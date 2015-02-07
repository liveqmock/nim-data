package com.poweruniverse.nim.data.service.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.GongNengLC;
import com.poweruniverse.nim.data.entity.GongNengLCMX;
import com.poweruniverse.nim.data.entity.JueSe;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZ;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMX;
import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuJS;
import com.poweruniverse.nim.data.entity.ZiDuan;
import com.poweruniverse.nim.data.entity.ZiDuanLX;

public class QueryUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sbf = new SimpleDateFormat("MM-dd");
	
	//根据用户功能操作权限中 所授予的可以进行操作的允许范围 创建查询条件
	public static Criterion createCriterionByPermits(ShiTiLei stl,List<Permit> permits,YongHu yh) throws Exception{
		Criterion criterion = null;
		if(permits!=null){
			if(permits.size()==0){
				criterion = Restrictions.sqlRestriction("1<>1");
			}else{
				Criterion criterionTmp = null;
				Iterator<Permit> ps = permits.iterator();
				while(ps.hasNext()){
					criterionTmp = createCriterionByQueries(stl,ps.next().getQueries(),yh);
					if(criterionTmp!=null && criterion==null){
						criterion = criterionTmp;
					}else if(criterionTmp!=null && criterion!=null){
						criterion = Restrictions.or(criterion, criterionTmp);
//					}else if(criterionTmp==null ){
//						//本次若干条件中 只要有一条全部的条件 就不再考虑其他条件了
//						criterion=null;
//						break;
					}
				}
			}
		}
		return criterion;
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
	public static Criterion createCriterionByQueries(ShiTiLei stl,List<Query> queries,YongHu yh) throws Exception{
		Criterion criterion = null;
		if(queries!=null){
			if(queries.size()>0){
				Criterion criterionTmp = null;
				Iterator<Query> qs = queries.iterator();
				while(qs.hasNext()){
					Query query = qs.next();
					criterionTmp = createCriterionByQuery(stl,query,yh);
					//PropertyQuery类型的查询 可能存在assist
					if(query instanceof PropertyQuery){
						PropertyQuery pq = (PropertyQuery)query;
						if(pq.getAssist()!=null && pq.getAssist().length()>0){
							JSONArray assists = JSONArray.fromObject(pq.getAssist());
							Criterion criterionTmp2 = null;
							for(int j=0;j<assists.size();j++){
								pq.setProperty(assists.getString(j));
								criterionTmp2 = createCriterionByQuery(stl,query,yh);
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
	
	/**
	* 根据过滤条件字符串 创建查询条件集合
	* 如果filterJsonString为null或空串或空集 返回null
	* 如果解析filterJsonString过程中失败 返回1<>1的查询条件
	* @param filterJsonString 
	* @return null  
	*/
	public static List<Query> createQueriesByJson(String filterJsonString){
		List<Query> queries = null;
		try {
			if(filterJsonString!=null && filterJsonString.length()>0){
				JSONArray filters = JSONArray.fromObject(filterJsonString);
				if(filters.size()>0){
					queries = new ArrayList<Query>();
					for(int i=0;i<filters.size();i++){
						JSONObject filter = filters.getJSONObject(i);
						String filtersOperator = filter.has("operator")?filter.getString("operator"):"=";
						if(filtersOperator.equals("sql")){
							queries.add(new SqlQuery(
									filter.getString("sql")
							));
						}else if(filter.getString("property").startsWith("{")){
							queries.add(new UserPropertyQuery(
								filter.getString("property"),
								filtersOperator,
								filter.getString("value")
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
				}
			}
		} catch (Exception e) {
			queries = new ArrayList<Query>();
			queries.add(new SqlQuery("1<>1"));
			e.printStackTrace();
		}
		return queries;
	}


	private static Criterion createCriterionByQuery(ShiTiLei stl,Query query,YongHu yh) throws Exception{
		Criterion criterion = null;
		try {
			if(query.isSqlQuery()){
				SqlQuery sq = (SqlQuery)query;
				String sqlString = ValueUtils.replaceRegulation(sq.getSql(), yh);
				criterion = createCriterionBySqlQuery(new SqlQuery(sqlString));
			}else if(query.isUserQuery()){
				UserPropertyQuery upq = (UserPropertyQuery)query;
//				upq.setProperty(ValueUtils.replaceRegulation(upq.getProperty(), yh));
				 
				criterion = createCriterionByUserQuery(upq,yh);
			}else{
				PropertyQuery pq = (PropertyQuery)query;
				if(stl.hasZiDuan(pq.getProperty())){
					if(("null".equalsIgnoreCase(pq.getValue()) || "\"null\"".equalsIgnoreCase(pq.getValue()) ) && ( pq.getOperator().equals("<>") ||  pq.getOperator().equals("ne") ) ){
						criterion = Restrictions.isNotNull(pq.getProperty());
					}else if(("null".equalsIgnoreCase(pq.getValue()) || "\"null\"".equalsIgnoreCase(pq.getValue()) )  && ( pq.getOperator().equals("=") ||  pq.getOperator().equals("eq") ) ){
						criterion = Restrictions.isNull(pq.getProperty());
					}else{
						pq.setValue(ValueUtils.replaceRegulation(pq.getValue(), yh));
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
	
	/**
	 * 
	 * @param query
	 * @param yh
	 * @return
	 * @throws Exception
	 */
	private static Criterion createCriterionByUserQuery(UserPropertyQuery query,YongHu yh) throws Exception{
		Criterion criterion = null;
		//属性字段
		String property = query.getProperty();
		String synString = property.substring(property.indexOf("{")+1,property.indexOf("}"));
		//操作符
		String operator = query.getOperator();
		//属性字段的类型
		String type = query.getType();
		if(type==null){
			ShiTiLei yhSTL = (ShiTiLei)SystemSessionFactory.getSession().load(ShiTiLei.class,18);
			type=yhSTL.getZiDuanLX(synString).getZiDuanLXDH();
		}
		//值的正确类型
		Object value = ValueUtils.getValueOfCorrectType(query.getValue(), type, operator);
		//属性字段对应的值
//		upq.setProperty();
		Object synValue = ValueUtils.getValueOfCorrectType(ValueUtils.replaceRegulation(query.getProperty(), yh), type, null);
		//
		if(!ValueUtils.checkRegulation(synValue,value,operator)){
			criterion = Restrictions.sqlRestriction("1<>1");
		}else{
			criterion = Restrictions.sqlRestriction("1=1");
		}
		return criterion;
	}
	
	private static Criterion createCriterionByPropertyQuery2(ShiTiLei stl,PropertyQuery query) throws Exception{
		Criterion criterion = null;
		String queryProperty =query.getProperty();
		if(queryProperty!=null){
			if(queryProperty.indexOf(".")>=0){
				String[] ziDuanArray = queryProperty.split("\\.");
				if(ziDuanArray.length==2 && ("id".equals(ziDuanArray[1]) || stl.getZiDuan(ziDuanArray[0]).getShiTiLei().getZhuJianLie().equals(ziDuanArray[1]))){
					//两个部分且第二个部分为主键列
					
					criterion = getCriterion(ziDuanArray[0]+".id",ZiDuanLX.ZiDuanLX_INT_DH,query.getOperator(),ValueUtils.getValueOfCorrectType(query.getValue(),ZiDuanLX.ZiDuanLX_INT_DH,query.getOperator()));
					if("<>".equals(query.getOperator()) || "ne".equals(query.getOperator())){
						//对象不等于的情况下 要判断为空的情况
						criterion = Restrictions.or(criterion, Restrictions.isNull(ziDuanArray[0]));
					}
				}else{
					String firstPropertyString = queryProperty.substring(0,queryProperty.indexOf("."));
					ZiDuan firstPropertyZiDuan = stl.getZiDuan(firstPropertyString);
					ShiTiLei finalStl = firstPropertyZiDuan.getGuanLianSTL();
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
					if(ZiDuanLX.isSetType(firstPropertyZiDuan.getZiDuanLX().getZiDuanLXDH())){
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
					type=stl.getZiDuanLX(queryProperty).getZiDuanLXDH();
				}
				if(ZiDuanLX.isObjectType(type)){
					//如果参数值是有效的整数 （对象类型字段 与整数比较 -> obj.id = 1）
					boolean b = Pattern.matches("^\\d+$", query.getValue());
					if(b){
						queryProperty+=".id";
						type = "int";
					}else if(query.getValue()==null || "null".equals(query.getValue()) || "\"null\"".equals(query.getValue())){
						queryProperty+=".id";
						type = "int";
					}else{
						//对象类型与字符串比较
						ShiTiLei glstl = stl.getZiDuan(queryProperty).getGuanLianSTL();
						ZiDuan glstlzd = glstl.getZiDuan(glstl.getXianShiLie());
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
	private static DetachedCriteria getNestedQueryCriterion2(ShiTiLei stl,PropertyQuery query) throws Exception{
		String propertyString = query.getProperty();
		DetachedCriteria subselect = DetachedCriteria.forClass(Class.forName(stl.getShiTiLeiClassName()));
		if(propertyString.indexOf(".")>=0){
			//截取属性字段的第一部分 用于获取当前字段的相关信息
			String property = propertyString.substring(0, propertyString.indexOf("."));
			ZiDuan zd = stl.getZiDuan(property);
			//截取属性字段的剩余部分 创建新的查询 递归转换
			PropertyQuery pq = new PropertyQuery(propertyString.substring(propertyString.indexOf(".")+1),null,query.getOperator(),query.getValue()); 
			DetachedCriteria subselect2 = getNestedQueryCriterion2(zd.getGuanLianSTL(),pq);
			//根据当前字段类型 确定子查询需要返回的列
			if(ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
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


//	private static Criterion createCriterionByPropertyQuery(ShiTiLei stl,PropertyQuery query) throws Exception{
//		Criterion criterion = null;
//		//普通属性公式
//		String[] ziDuanArray = null;
//		String propertyString;
//		boolean isPKZiduan = false;
//		boolean isSetField = false;
//		
//		String finalProperty =query.getProperty();
//		String operator = query.getOperator();
//		
//		String type = query.getType();
//		if(type==null){
//			type=stl.getZiDuanLX(finalProperty).getZiDuanLXDH();
//		}
//		
//		
//		if(ZiDuanLX.isObjectType(type) && "like".equals(operator)){
//			//like 一个对象 意味着 like它的显示列
//			ShiTiLei subStl = stl.getZiDuan(finalProperty).getGuanLianSTL();
//			finalProperty +="."+subStl.getXianShiLie();
//			type = subStl.getZiDuan(subStl.getXianShiLie()).getZiDuanLX().getZiDuanLXDH();
//		}
//		//检查公式相关字段 是否为多重字段 并为多重字段创建别名
//		if(finalProperty!=null && finalProperty.indexOf(".")>=0){
//			propertyString = finalProperty;
//			//如果是.id可以不考虑
//			if(propertyString.indexOf(".id")>=0){
//				propertyString = propertyString.substring(0,propertyString.indexOf(".id"));
//				isPKZiduan = true;
//			}else{
//				ShiTiLei finalStl = stl;
//				ziDuanArray = propertyString.split("\\.");
//				for (int i=0;i<ziDuanArray.length -1;i++){
//					ZiDuan tempZD = finalStl.getZiDuan(ziDuanArray[i]);
//					finalStl = tempZD.getGuanLianSTL();
//					if(ZiDuanLX.isSetType(tempZD.getZiDuanLX().getZiDuanLXDH())){
//						isSetField = true;
//					}
//				}
//				if(finalStl.getZhuJianLie().equals(ziDuanArray[ziDuanArray.length -1])){
//					propertyString = propertyString.substring(0,propertyString.indexOf("."+ziDuanArray[ziDuanArray.length -1]));
//					isPKZiduan = true;
//				}
//			}
//			//如果是集合类型
//			if(isSetField){
//				String firstPropertyString = propertyString.substring(0,propertyString.indexOf("."));
//				ZiDuan firstPropertyZiDuan = stl.getZiDuan(firstPropertyString);
//				ShiTiLei finalStl = firstPropertyZiDuan.getGuanLianSTL();
//				//判断是否 是(集合<>vaue)形式的条件
//				String nestedQueryOperator = operator;
//				boolean isNotInNested=false;
//				if( "<>".equals(nestedQueryOperator) || "ne".equals(nestedQueryOperator)){
//					nestedQueryOperator= "=";
//					isNotInNested=true;
//				}
//				//
//				DetachedCriteria subselect = getNestedQueryCriterion(finalStl,new PropertyQuery(propertyString.substring(propertyString.indexOf(".")+1),nestedQueryOperator,query.getValue()) );
//				//
//				if(ZiDuanLX.isSetType(firstPropertyZiDuan.getZiDuanLX().getZiDuanLXDH())){
//					subselect.setProjection(Projections.property(firstPropertyZiDuan.getGuanLianFLZD()+".id"));
//					criterion = isNotInNested?Property.forName(stl.getZhuJianLie()).notIn(subselect):Property.forName(stl.getZhuJianLie()).in(subselect);
//				}else{
//					subselect.setProjection(Projections.id());
//					criterion =  isNotInNested?Property.forName(firstPropertyString+".id").notIn(subselect):Property.forName(firstPropertyString+".id").in(subselect);
//				}
//				return criterion;
//			}
//			//
//		}
//		//取得字段类型
//		if(ZiDuanLX.isObjectType(type)){
//			finalProperty+=".id";
//			type = ZiDuanLX.ZiDuanLX_INT_DH;
//		}
//		Object value = null;
//		//将[1,2,3,4]形式的数组 转变为set(operator为between的情况例外)
//		if(query.getValue().startsWith("[") && !operator.equals("between")){
//			if(type.equals("string")){
//				JSONArray values = JSONArray.fromObject(query.getValue());
//				Set<String> ids = new HashSet<String>();
//				for(int i=0;i<values.size();i++){
//					ids.add(values.getString(i));
//				}
//				value = ids;
//			}else if(type.equals("int")){
//				JSONArray values = JSONArray.fromObject(query.getValue());
//				Set<Integer> ids = new HashSet<Integer>();
//				for(int i=0;i<values.size();i++){
//					ids.add(values.getInt(i));
//				}
//				value = ids;
//			}else{
//				throw new Exception("未处理的数据类型："+type);
//			}
//		}else{
//			value = ValueUtils.getValueOfCorrectType(query.getValue(), type, operator);
//		}
//		//生成过滤条件
//		if(value instanceof Set){
//			Set<?> valueList = (Set<?>)value;
//			if(valueList.size()>0){
//				criterion = null;
//				for(Object v:(Set<?>)value){
//					Criterion criterion2 = getCriterion(finalProperty,type,operator,v);//这里的v应该是正确的类型
//					if(criterion2!=null){
//						if(criterion==null){
//							criterion= criterion2;
//						}else{
//							if( "<>".equals(operator) || "ne".equals(operator)){
//								//否定的条件： 要求集合中的各元素均不满足此条件 (and)
//								criterion = Restrictions.and(criterion, criterion2);
//							}else{
//								//肯定的条件：集合中的某一元素满足此条件即可 (or)
//								criterion = Restrictions.or(criterion, criterion2);
//							}
//						}
//					}
//				}
//			}
//		}else{
//			//取得值的正确类型
//			try {
//				if((value==null || "null".equals(value)) && ("=".equals(operator) || "eq".equals(operator) || "like".equals(operator) )){//为空
//					criterion = Restrictions.isNull(finalProperty);
//				}else if((value==null || "null".equals(value)) && ( "<>".equals(operator) || "ne".equals(operator)) ){//非空
//					criterion = Restrictions.isNotNull(finalProperty);
//				}else if(value instanceof EntityI && type.equals("int")){//对象类型
//					EntityI entity = (EntityI)value;
//					criterion = getCriterion(finalProperty,type,operator,entity.pkValue());
//				}else{
//					criterion = getCriterion(finalProperty,type,operator,ValueUtils.getValueOfCorrectType(""+value,type,operator));
//				}
//			} catch (Exception e) {
//				System.out.println("创建查询语法出错,Property:"+finalProperty+",Operator:"+operator+",value:"+value+",type:"+type);
//				e.printStackTrace();
//			}
//		}
//		return criterion;
//	}
	
	
	//将带有集合属性的过滤条件 转换为公式
//	private static DetachedCriteria getNestedQueryCriterion(ShiTiLei stl,PropertyQuery query) throws Exception{
//		String propertyString = query.getProperty();
//		DetachedCriteria subselect = DetachedCriteria.forClass(Class.forName(stl.getShiTiLeiClassName()));
//		if(propertyString.indexOf(".")>=0){
//			//截取属性字段的第一部分 用于获取当前字段的相关信息
//			String property = propertyString.substring(0, propertyString.indexOf("."));
//			ZiDuan zd = stl.getZiDuan(property);
//			//截取属性字段的剩余部分 创建新的查询 递归转换
//			PropertyQuery pq = new PropertyQuery(propertyString.substring(propertyString.indexOf(".")+1),null,query.getOperator(),query.getValue()); 
//			DetachedCriteria subselect2 = getNestedQueryCriterion(zd.getGuanLianSTL(),pq);
//			//根据当前字段类型 确定子查询需要返回的列
//			if(ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
//				subselect2.setProjection(Projections.property(zd.getGuanLianFLZD()+".id"));
//				//子查询应该与主表的那个字段关联
//				subselect = subselect.add(Property.forName(stl.getZhuJianLie()).in(subselect2));
//			}else{
//				subselect2.setProjection(Projections.id());
//				//子查询应该与主表的那个字段关联
//				subselect = subselect.add(Property.forName(property+".id").in(subselect2));
//			}
//		}else{
//			//因为propertyString不包含"." 此处不用提供和处理alias 因为不会使用到
//			subselect.add(createCriterionByPropertyQuery(stl,query));
//		}
//		return subselect;
//	}

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
		
	
	/**
	 * 根据用户授权 取得条件集合 
	 * 返回null 表示全部允许 
	 * 返回空集合 表示不允许
	 * @param yh
	 * @param gncz
	 * @return
	 */
	public static List<Permit> getPermitsByYHAuth(YongHu yh,GongNengCZ gncz,boolean addAssigneeAuth){
		Session sess = SystemSessionFactory.getSession();
		if(!sess.contains(yh)){
			yh =(YongHu)sess.load(YongHu.class, yh.getYongHuDM());
		}
		
		List<Integer> jss = new ArrayList<Integer>();
		for(YongHuJS yhjs:yh.getJss()){
			jss.add(yhjs.getJueSe().getJueSeDM());
		}
		
		return getPermitsByJSsAuth(jss,yh,gncz,addAssigneeAuth);
	}
		
	/**
	 * 根据角色授权 取得条件集合 
	 * 返回 null 表示全部允许
	 * 返回空集合 表示不允许
	 * @param jss
	 * @param yh
	 * @param gncz
	 * @return
	 */
	public static ArrayList<Permit> getPermitsByJSsAuth(List<Integer> jss,YongHu yh,GongNengCZ gncz,boolean addAssigneeAuth){
		ArrayList<Permit> ps = null;
		//根据权限 取得查询条件
		if(!gncz.getKeYiSQ() || (yh!=null && "ADMIN".equals(yh.getDengLuDH()))){
			//功能操作无需授权 将permits置null 表示全部允许
		}else{
			//功能操作需要授权 将permits置空集 表示不允许
			ps = new ArrayList<Permit>();
			if(jss!=null && jss.size()>0){
				//功能操作需要授权 初始设置basefilters为空集合 表示全部不允许
				//然后再根据权限加入允许的范围
					//取当前用户对此功能操作的权限
				Session sess = SystemSessionFactory.getSession();
				@SuppressWarnings("unchecked")
				List<JueSeQXGNCZ> yhqxgnczs = (List<JueSeQXGNCZ>)sess.createCriteria(JueSeQXGNCZ.class)
						.add(Restrictions.eq("gongNengCZ.id", gncz.getGongNengCZDM()))
						.add(Restrictions.in("jueSe.id", jss))
						.list();
				if(yhqxgnczs!=null && yhqxgnczs.size()>0){
					for(JueSeQXGNCZ yhqxgncz:yhqxgnczs){
						//用户在本功能操作上 得到的授权
						Iterator<JueSeQXGNCZMX> gnczmxs = yhqxgncz.getMxs().iterator();
						JueSeQXGNCZMX gnczmx = null;
						while(gnczmxs.hasNext()){
							gnczmx = gnczmxs.next();
							if(gnczmx.getGss() != null){
								ps.add(new Permit().setFilterByGNCZGSS(gnczmx.getGss().iterator()));
							}
						}
					}
					//业务表相关的功能 额外增加任务执行人的权限
					if(addAssigneeAuth && gncz.getGongNeng().getShiTiLei().getShiFouYWB().booleanValue() && yh!=null ){
						ps.add(new Permit("assignee.id","=",yh.getYongHuDM().toString()));
					}
				}
			}
		}
		return ps;
	}
	
	/**
	 * 根据角色授权 取得条件集合 
	 * 返回 null 表示全部允许
	 * 返回空集合 表示不允许
	 * @param jss
	 * @param yh
	 * @param gncz
	 * @return
	 */
	public static ArrayList<Permit> getPermitsByJSAuth(JueSe js,GongNengCZ gncz){
		ArrayList<Permit> ps = null;
		//根据权限 取得查询条件
		if(!gncz.getKeYiSQ()){
			//功能操作无需授权 将permits置null 表示全部允许
		}else{
			//功能操作需要授权 将permits置空集 表示不允许
			ps = new ArrayList<Permit>();

			//功能操作需要授权 初始设置basefilters为空集合 表示全部不允许
			//然后再根据权限加入允许的范围
				//取当前用户对此功能操作的权限
			Session sess = SystemSessionFactory.getSession();
			@SuppressWarnings("unchecked")
			List<JueSeQXGNCZ> yhqxgnczs = (List<JueSeQXGNCZ>)sess.createCriteria(JueSeQXGNCZ.class)
					.add(Restrictions.eq("gongNengCZ.id", gncz.getGongNengCZDM()))
					.add(Restrictions.eq("jueSe.id", js.getJueSeDM()))
					.list();
			if(yhqxgnczs!=null && yhqxgnczs.size()>0){
				for(JueSeQXGNCZ yhqxgncz:yhqxgnczs){
					//用户在本功能操作上 得到的授权
					Iterator<JueSeQXGNCZMX> gnczmxs = yhqxgncz.getMxs().iterator();
					JueSeQXGNCZMX gnczmx = null;
					while(gnczmxs.hasNext()){
						gnczmx = gnczmxs.next();
						if(gnczmx.getGss() != null){
							ps.add(new Permit().setFilterByGNCZGSS(gnczmx.getGss().iterator()));
						}
					}
				}
			}
		
		}
		return ps;
	}		
	/**
	 * 根据流程条件 取得条件集合 
	 * @param yh
	 * @param gnlc
	 * @return
	 */
	public static ArrayList<Permit> getPermitsFromGongNengLC(GongNengLC gnlc){
		//将permits置空集 表示不允许
		ArrayList<Permit> ps = new ArrayList<Permit>();
		Iterator<GongNengLCMX> gnlcmxs = gnlc.getMxs().iterator();
		GongNengLCMX gnlcmx = null;
		while(gnlcmxs.hasNext()){
			gnlcmx = gnlcmxs.next();
			if(gnlcmx.getGss() != null){
				ps.add(new Permit().setFilterByGNLCGSS(gnlcmx.getGss().iterator()));
			}
		}
		return ps;
	}
	
	
}
