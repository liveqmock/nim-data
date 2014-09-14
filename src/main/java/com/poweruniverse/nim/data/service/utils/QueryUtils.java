package com.poweruniverse.nim.data.service.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.interfaces.entity.ShiTiLeiI;
import com.poweruniverse.nim.interfaces.entity.ZiDuanI;

public class QueryUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sbf = new SimpleDateFormat("MM-dd");
	
	//将一个字段的排序定义 转变为hibernate排序语法
	public static Order getSortCriterion(String propertyString,boolean isAsc,HashMap<String, String> existsAlias){
		Order order = null;
		String[] ziDuanArray = null;
		String finalProperty;
		String aliaName;
		String aliaName2;
		//最终使用的字段名称 
		finalProperty = propertyString;
		//如果是多重字段 需要创建别名 并重新设置finalProperty
		if(propertyString.indexOf(".")>=0){
			//如果是.id可以不考虑
			if(propertyString.indexOf(".id")>=0){
				propertyString = propertyString.substring(0,propertyString.indexOf(".id"));
			}
			//
			if(propertyString.indexOf(".")>=0){
				ziDuanArray = propertyString.split("\\.");
				propertyString = null;
				aliaName = null;
				aliaName2 = null;
				for (int i=0;i<ziDuanArray.length -1;i++){
					if(i==0){
						propertyString = ziDuanArray[0];
						aliaName = ziDuanArray[0];
					}else{
						propertyString += "."+ziDuanArray[i];
						aliaName += "_"+ziDuanArray[i];
					}
					if(!existsAlias.containsKey(propertyString) ){
						aliaName2 = aliaName+"_alia";
//								System.out.println("createAlias...key:"+propertyString+",alia:"+aliaName2);
//								criteria.createAlias(propertyString,aliaName2);
						existsAlias.put(propertyString,aliaName2);
					}else{
						aliaName2 = existsAlias.get(propertyString);
					}
				}
				finalProperty = aliaName2+"."+ziDuanArray[ziDuanArray.length -1];
			}
		}
		if(isAsc){
			order = Order.asc(finalProperty);
		}else{
			order = Order.desc(finalProperty);
		}
		return order;
	}
		

	
	/**
	* 根据过滤条件字符串 创建查询条件集合
	* 如果filterJsonString为null或空串或空集 返回null
	* 如果解析filterJsonString过程中失败 返回1<>1的查询条件
	* @param filterJsonString 
	* @return null  
	*/
	public static List<Query> createQueriesByJson(JSONArray filters) throws Exception{
		List<Query> queries = null;
		queries = new ArrayList<Query>();
		for(int i=0;i<filters.size();i++){
			JSONObject filter = filters.getJSONObject(i);
			String filtersOperator = filter.has("operator")?filter.getString("operator"):"=";
			if(filtersOperator.equals("sql")){
				queries.add(new SqlQuery(
						filter.getString("sql")
				));
			}else{
				queries.add(new PropertyQuery(
					filter.getString("property"),
					filter.has("type")?filter.getString("type"):null,
					filter.has("assist")?filter.getString("assist"):null,
					filtersOperator,
					filter.getString("value")
				));
			}
		}
		return queries;
	}

	/**
	* 根据条件集合 生成hibernate查询语法中的过滤条件
	* queries =null ：返回null
	* queries.size() = 0 : 返回 1<>1
	* @param stl
	* @param existsAlias
	* @param queries
	* @return
	* @throws Exception
	*/
	public static Criterion createCriterionByQueries(ShiTiLeiI stl,List<Query> queries) throws Exception{
		Criterion criterion = null;
		if(queries!=null){
			if(queries.size()>0){
				Criterion criterionTmp = null;
				Iterator<Query> qs = queries.iterator();
				while(qs.hasNext()){
					Query query = qs.next();
					criterionTmp = createCriterionByQuery(stl,query);
					//PropertyQuery类型的查询 可能存在assist
					if(query instanceof PropertyQuery){
						PropertyQuery pq = (PropertyQuery)query;
						if(pq.getAssist()!=null && pq.getAssist().length()>0){
							JSONArray assists = JSONArray.fromObject(pq.getAssist());
							Criterion criterionTmp2 = null;
							for(int j=0;j<assists.size();j++){
								pq.setProperty(assists.getString(j));
								criterionTmp2 = createCriterionByQuery(stl,query);
								if(criterionTmp2 != null){
									if(criterionTmp==null){
										criterionTmp = criterionTmp2;
									}else{
										criterionTmp = Restrictions.or(criterionTmp, criterionTmp2);
									}
								}
							}
						}
					}
					if(criterionTmp!=null && criterion==null){
							criterion = criterionTmp;
					}else if(criterionTmp!=null && criterion!=null){
						criterion = Restrictions.and(criterion, criterionTmp);
					}
				}
			}else{
				//空集合 返回1<>1条件
				criterion = Restrictions.sqlRestriction("1<>1");
			}
		}
		return criterion;
	}
	
	private static Criterion createCriterionByQuery(ShiTiLeiI stl,Query query) throws Exception{
		Criterion criterion = null;
		try {
			if(query.isSqlQuery()){
				SqlQuery sq = (SqlQuery)query;
				criterion = createCriterionBySqlQuery(new SqlQuery(sq.getSql()));
			}else{
				PropertyQuery pq = (PropertyQuery)query;
				if(stl.hasZiDuan(pq.getProperty())){
					if(pq.getValue().equalsIgnoreCase("null") && ( pq.getOperator().equals("<>") ||  pq.getOperator().equals("ne") ) ){
						criterion = Restrictions.isNotNull(pq.getProperty());
					}else if(pq.getValue().equalsIgnoreCase("null") && ( pq.getOperator().equals("=") ||  pq.getOperator().equals("eq") ) ){
						criterion = Restrictions.isNull(pq.getProperty());
					}else{
						criterion = createCriterionByPropertyQuery2(stl,pq);
					}
				}
				
			}
		} catch (Exception e) {
			criterion = Restrictions.sqlRestriction("1<>1");
			e.printStackTrace();
		}
		return criterion;
	}

	private static Criterion createCriterionBySqlQuery(SqlQuery query) throws Exception{
		return Restrictions.sqlRestriction(query.getSql());
	}
	
	private static Criterion createCriterionByPropertyQuery2(ShiTiLeiI stl,PropertyQuery query) throws Exception{
		Criterion criterion = null;
		String queryProperty =query.getProperty();
		if(queryProperty!=null){
			if(queryProperty.indexOf(".")>=0){
				String[] ziDuanArray = queryProperty.split("\\.");
				if(ziDuanArray.length==2 && ("id".equals(ziDuanArray[1]) || stl.getZiDuan(ziDuanArray[0]).getShiTiLei().getZhuJianLie().equals(ziDuanArray[1]))){
					//两个部分且第二个部分为主键列
					criterion = getCriterion(ziDuanArray[0]+".id","int",query.getOperator(),ValueUtils.getValueOfCorrectType(query.getValue(),"int",query.getOperator()));
				}else{
					String firstPropertyString = queryProperty.substring(0,queryProperty.indexOf("."));
					ZiDuanI firstPropertyZiDuan = stl.getZiDuan(firstPropertyString);
					ShiTiLeiI finalStl = firstPropertyZiDuan.getGuanLianSTL();
					//判断是否 是(集合<>vaue)形式的条件
					String nestedQueryOperator = query.getOperator();
					boolean isNotInNested=false;
					if( "<>".equals(nestedQueryOperator) || "ne".equals(nestedQueryOperator)){
						nestedQueryOperator= "=";
						isNotInNested=true;
					}else if( "nlike".equals(nestedQueryOperator)){
						nestedQueryOperator= "like";
						isNotInNested=true;
					}
					//
					DetachedCriteria subselect = getNestedQueryCriterion2(finalStl,new PropertyQuery(queryProperty.substring(queryProperty.indexOf(".")+1),nestedQueryOperator,query.getValue()) );
					//
					if("set".equals(firstPropertyZiDuan.getZiDuanLX().getZiDuanLXDH())){
						subselect.setProjection(Projections.property(firstPropertyZiDuan.getGuanLianFLZD()+".id"));
						criterion = isNotInNested?Property.forName(stl.getZhuJianLie()).notIn(subselect):Property.forName(stl.getZhuJianLie()).in(subselect);
					}else{
						subselect.setProjection(Projections.id());
						criterion =  isNotInNested?Property.forName(firstPropertyString+".id").notIn(subselect):Property.forName(firstPropertyString+".id").in(subselect);
					}
					return criterion;
				
				}
			}else{
				//没有表关联的情况下 直接返回
				String type = query.getType();
				if(type==null){
					ZiDuanI zd = stl.getZiDuan(queryProperty);
					type = zd.getZiDuanLX().getZiDuanLXDH();
				}
				if("set".equals(type)){
					try {
						Integer.parseInt(query.getValue());
						queryProperty+=".id";
						type = "int";
					}catch (NumberFormatException e) {
						ShiTiLeiI glstl = stl.getZiDuan(queryProperty).getGuanLianSTL();
						ZiDuanI glstlzd = glstl.getZiDuan(glstl.getXianShiLie());
						queryProperty+="."+glstl.getXianShiLie();
						type = glstlzd.getZiDuanLX().getZiDuanLXDH();
					}
					PropertyQuery newQuery = new PropertyQuery(queryProperty,type,query.getOperator(),query.getValue());
					return createCriterionByPropertyQuery2(stl,newQuery);
				}else{
					String valueString = query.getValue();
					String operatorString = query.getOperator();
					
					try {
						if((valueString==null || "null".equals(valueString) || "\"null\"".equals(valueString)) && ("=".equals(operatorString) || "eq".equals(operatorString) || "like".equals(operatorString) )){//为空
							criterion = Restrictions.isNull(queryProperty);
						}else if((valueString==null || "null".equals(valueString) || "\"null\"".equals(valueString)) && ( "<>".equals(operatorString) || "ne".equals(operatorString) || "nlike".equals(operatorString)) ){//非空
							criterion = Restrictions.isNotNull(queryProperty);
						}else{
							criterion = getCriterion(queryProperty,type,operatorString,ValueUtils.getValueOfCorrectType(valueString,type,operatorString));
						}
					} catch (Exception e) {
						System.out.println("创建查询语法出错,Property:"+queryProperty+",Operator:"+operatorString+",value:"+valueString+",type:"+type);
						e.printStackTrace();
					}
				}
			}
		}
		return criterion;
	}
	
	//将带有集合属性的过滤条件 转换为公式
	private static DetachedCriteria getNestedQueryCriterion2(ShiTiLeiI stl,PropertyQuery query) throws Exception{
		String propertyString = query.getProperty();
		DetachedCriteria subselect = DetachedCriteria.forClass(Class.forName(stl.getShiTiLeiClassName()));
		if(propertyString.indexOf(".")>=0){
			//截取属性字段的第一部分 用于获取当前字段的相关信息
			String property = propertyString.substring(0, propertyString.indexOf("."));
			ZiDuanI zd = stl.getZiDuan(property);
			//截取属性字段的剩余部分 创建新的查询 递归转换
			PropertyQuery pq = new PropertyQuery(propertyString.substring(propertyString.indexOf(".")+1),null,query.getOperator(),query.getValue()); 
			DetachedCriteria subselect2 = getNestedQueryCriterion2(zd.getGuanLianSTL(),pq);
			//根据当前字段类型 确定子查询需要返回的列
			if("set".equals(zd.getZiDuanLX().getZiDuanLXDH())){
				subselect2.setProjection(Projections.property(zd.getGuanLianFLZD()+".id"));
				//子查询应该与主表的那个字段关联
				subselect = subselect.add(Property.forName(stl.getZhuJianLie()).in(subselect2));
			}else{
				subselect2.setProjection(Projections.id());
				//子查询应该与主表的那个字段关联
				subselect = subselect.add(Property.forName(property+".id").in(subselect2));
			}
		}else{
			//因为propertyString不包含"." 此处不用提供和处理alias 因为不会使用到
			subselect.add(createCriterionByPropertyQuery2(stl,query));
		}
		return subselect;
	}


	/**
	* 根据参数 创建hibernate查询条件
	* @param property  字段(如果需要alia，已经是转换后的最终形式 )
	* @param type		类型
	* @param ziDuanCZF	操作符
	* @param value		值（已经是正确的类型）
	* @return
	* @throws Exception
	*/
	private static Criterion getCriterion(String property,String type,String ziDuanCZF,Object value) throws Exception{
		Criterion criterion = null;
		if(value!=null){//value为null的情况下 忽略此过滤条件
			if ("between".equals(ziDuanCZF)){
				Object[] params = (Object[])value;
				if(type.equals("date") || type.equals("time") || type.equals("month") || type.equals("birthday")){
					//between类型的公式 且字段为日期类型
					//根据字段类型 确定日志格式字符串 及格式化对象
					String dateFormat = "";
					SimpleDateFormat df = null;
					if(type.equals("date")){
						dateFormat = "yyyy-MM-dd";
						df= sdf;
					}else if(type.equals("month")){
						dateFormat = "yyyy-MM";
						df= smf;
					}else if(type.equals("time")){
						dateFormat = "hh24:mm";
						df= stf;
					}else if(type.equals("birthday")){
						dateFormat = "MM-dd";
						df= sbf;
					}else{
						throw new Exception("QueryUtils.getQueryCriterion():未定义处理方式的日期类型："+type);
					}
					//根据上面确定的日期格式 拼sql
					if(params[0]!=null && params[1]==null){
						//仅有后面的值
						criterion = Restrictions.sqlRestriction("to_char("+property+",'"+dateFormat+"') > '"+df.format(params[0])+"'");
					}else if(params[0]==null && params[1]!=null){
						//仅有前面的值
						criterion = Restrictions.sqlRestriction("to_char("+property+",'"+dateFormat+"') < '"+df.format(params[1])+"'");
					}else{
						//两个值都有
						criterion = Restrictions.sqlRestriction("to_char("+property+",'"+dateFormat+"') between '"+df.format(params[0])+"' and '"+df.format(params[1])+"'");
					}
				}else{
					//between类型的公式 且字段非日期类型
					if(params[0]!=null && params[1]==null){
						//仅有后面的值
						criterion = Restrictions.ge(property,params[0]);
					}else if(params[0]==null && params[1]!=null){
						//仅有前面的值
						criterion = Restrictions.le(property,params[1]);
					}else{
						//两个值都有
						criterion = Restrictions.between(property,params[0],params[1]);
					}
				}
				
			}else if(value instanceof Set){
				Set<?> valueList = (Set<?>)value;
				if(valueList.size()>0){
					criterion = null;
					for(Object v:(Set<?>)value){
						Criterion criterion2 = getCriterion(property,type,ziDuanCZF,v);//这里的v应该是正确的类型
						if(criterion2!=null){
							if(criterion==null){
								criterion= criterion2;
							}else{
								if( "<>".equals(ziDuanCZF) || "ne".equals(ziDuanCZF) || "nlike".equals(ziDuanCZF)){
									//否定的条件： 要求集合中的各元素均不满足此条件 (and)
									criterion = Restrictions.and(criterion, criterion2);
								}else{
									//肯定的条件：集合中的某一元素满足此条件即可 (or)
									criterion = Restrictions.or(criterion, criterion2);
								}
							}
						}
					}
				}
			}else{
				if ("like".equals(ziDuanCZF)){
					if(value instanceof String){
						//只有文本字段 允许like操作符
						criterion = Restrictions.ilike(property,(String)value,MatchMode.ANYWHERE);
					}else{
						//其它类型的字段 替换为 “等于”
						criterion = Restrictions.eq(property,value);
					}
				}else if ("nlike".equals(ziDuanCZF)){
					if(value instanceof String){
						//只有文本字段 允许not like操作符
						criterion = Restrictions.not(Restrictions.ilike(property,(String)value,MatchMode.ANYWHERE));
					}else{
						//其它类型的字段 替换为 “不等于”
						criterion = Restrictions.ne(property,value);
					}
				}else if ("start".equals(ziDuanCZF)){
					if(value instanceof String){
						//只有文本字段 允许like操作符
						criterion = Restrictions.ilike(property,(String)value,MatchMode.START);
					}else{
						//其它类型的字段 替换为 “等于”
						criterion = Restrictions.eq(property,value);
					}
				}else if ("=".equals(ziDuanCZF) || "eq".equals(ziDuanCZF)){
					criterion = Restrictions.eq(property,value);
				}else if ("<>".equals(ziDuanCZF) || "ne".equals(ziDuanCZF)){
					criterion = Restrictions.ne(property,value);
				}else if (">=".equals(ziDuanCZF) || "ge".equals(ziDuanCZF)){
					criterion = Restrictions.ge(property,value);
				}else if (">".equals(ziDuanCZF) || "gt".equals(ziDuanCZF)){
					criterion = Restrictions.gt(property,value);
				}else if ("<=".equals(ziDuanCZF) || "le".equals(ziDuanCZF)){
					criterion = Restrictions.le(property,value);
				}else if ("<".equals(ziDuanCZF) || "lt".equals(ziDuanCZF)){
					criterion = Restrictions.lt(property,value);
				}else{
					throw new Exception("QueryUtils.getCriterion():未定义的处理方式："+ziDuanCZF);
				}
			}
		}
		return criterion;
	}
	
}
