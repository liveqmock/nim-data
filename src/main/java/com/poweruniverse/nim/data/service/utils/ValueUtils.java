package com.poweruniverse.nim.data.service.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;

import com.poweruniverse.nim.data.entity.system.base.EntityI;

public class ValueUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sbf = new SimpleDateFormat("MM-dd");
	
	/**
	 * 根据字段描述 从数据对象中取得相应的值
	 * 与PriopertyUtils 相比 主要区别在于实现了对集合的处理
	 * @param srcObj
	 * @param fieldSyntax
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getValueByProperties(Object obj,String fieldSyntax){
		if(fieldSyntax==null || fieldSyntax.length()==0){
			return obj;
		}
		if(obj==null){
			return null;
		}
		Object value = obj;
		String fieldString = fieldSyntax;
		try {
			while(fieldString.length()>0){
				String cFieldName  = fieldString;
				if(fieldString.indexOf(".")>=0){
					cFieldName = fieldString.substring(0,fieldString.indexOf("."));
					fieldString = fieldString.substring(fieldString.indexOf(".")+1);
				}else{
					cFieldName = fieldString;
					fieldString = "";
				}
				if(value instanceof Set){
					Set<?> valueSet = (Set<?>)value;
					value = new HashSet<Object>();
					for(Object v : valueSet){
						Object subValue = getValueByProperties(v,cFieldName);
						if(subValue instanceof Set){
							((Set<Object>) value).addAll((Set<Object>)subValue);
						}else{
							((Set<Object>) value).add(subValue);  
						}
					}
				}else{
					value = PropertyUtils.getProperty(value, cFieldName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static Object getValueOfCorrectType(String valueString,String type,String operator) throws Exception {
		//如果是between类型的
		if("between".equalsIgnoreCase(operator)){
			Object valueObjs[] = new Object[2];
			if(valueString!=null && valueString.indexOf(",")>=0){
				String values[] = valueString.split(",");
				if(values.length>0 && values[0]!=null && values[0].length()>0){
					valueObjs[0] = getValueOfCorrectType(values[0],type,"=");
				}
				if(values.length>1 && values[1]!=null && values[1].length()>0){
					valueObjs[1] = getValueOfCorrectType(values[1],type,"=");
				}
			}else{
				valueObjs[0] = getValueOfCorrectType(valueString,type,"=");
			}
			return valueObjs;
		}
		//将字段取值转换为适当的对象
		Object obj = null;
		if("null".equals(valueString) || "\"null\"".equals(valueString) || ("".equals(valueString) && !(type.equals("string") || type.equals("text")  || type.equals("varchar") || type.equals("varchar2")))){
			valueString = null;
		}else if(type.equals("int") || type.equals("integer")){
			if(valueString.startsWith("[")){
				JSONArray values = JSONArray.fromObject(valueString);
				Set<Integer> ids = new HashSet<Integer>();
				for(int i=0;i<values.size();i++){
					ids.add(values.getInt(i));
				}
				obj = ids;
			}else if("true".equalsIgnoreCase(valueString)){
				obj = Integer.valueOf(1);
			}else if("false".equalsIgnoreCase(valueString)){
				obj = Integer.valueOf(0);
			}else{
				obj = Integer.valueOf(valueString);
			}
		}else if(type.equals("persent") || type.equals("yuan") || type.equals("yuanrmb")|| type.equals("money") || 
				type.equals("tenthousandmoney")|| type.equals("tenthousand") || type.equals("tenthousandrmb") || 
				type.equals("double") || type.equals("float") || type.equals("number")){
			obj = Double.valueOf(valueString);
		}else if(type.equals("string") || type.equals("text")  || type.equals("varchar") || type.equals("varchar2")){
			if(valueString.startsWith("[")){
				JSONArray values = JSONArray.fromObject(valueString);
				Set<String> strings = new HashSet<String>();
				for(int i=0;i<values.size();i++){
					strings.add(values.getString(i));
				}
				obj = strings;
			}else{
				obj = valueString;
			}
			
		}else if(type.equals("boolean")){
			obj = Boolean.valueOf(valueString);
		}else if(type.equals("date")){
			try {
				if("<".equalsIgnoreCase(operator) || "<=".equalsIgnoreCase(operator) || "lt".equalsIgnoreCase(operator) ||"le".equalsIgnoreCase(operator) ){
					//小于一个日期 应该是小于当天的 23:59:59 (这里有一秒的bug)
					Date dataobj = sdf.parse(valueString);
					Calendar cal = Calendar.getInstance();
					cal.setTime(dataobj);
					cal.set(Calendar.HOUR_OF_DAY,23);
					cal.set(Calendar.MINUTE,59);
					cal.set(Calendar.SECOND,59);
					
					obj = cal.getTime();
				}else{
					obj = sdf.parse(valueString);
				}
				
			} catch (Exception e) {
			}
		}else if(type.equals("month")){
			obj = smf.parse(valueString);
		}else if(type.equals("time")){
			obj = stf.parse(valueString);
		}else if(type.equals("birthday")){
			obj = sbf.parse(valueString);
		}else if(type.equals("datetime")){
			obj = DateFormat.getDateTimeInstance().parse(valueString);
		}else if(type.equals("object")){
			obj = Integer.valueOf(valueString);
		}else if(type.equals("set") || type.equals("fileset") || type.equals("officefileset")){
		}else{
			throw new Exception("getValueOfCorrectType():未定义处理代码的数据类型："+type+","+valueString);
		}
		return obj;
	}
	
	/**
	 * 检查value1 和value2是否满足operator确定的比较条件
	 * @param value1
	 * @param value2
	 * @param operator
	 * @return
	 */
	public static boolean checkRegulation(Object value1,Object value2,String operator){
		boolean checkResult = false;
		//value1 等于null 的情况下 单独处理
		if(value1==null  ){
			if(value2==null  ){
				if ("=".equals(operator) || "eq".equals(operator) || "like".equals(operator)){
					return true;
				}
			}else{
				if ("!=".equals(operator) || "ne".equals(operator)){
					return true;
				}
			}
			return false;
		}
		//判断value1是否集合
		//判断值是否为集合类型
		if(value1 instanceof Set){
			@SuppressWarnings("unchecked")
			Set<Object> setValue = (Set<Object>)value1;
			//肯定的条件：集合中的某一元素满足此条件即可
			//否定的条件： 要求集合中的各元素均不满足此条件 
			checkResult = true;
			for(Object v:setValue){
				if(checkRegulation(v,value2,operator)){
					if("!=".equals(operator) || "ne".equals(operator) ){
						checkResult = false;
					}
					break;
				}
			}
		}else{
			//取得值的正确类型
			if ("like".equals(operator)){
				if(value1 instanceof String){
					//只有文本字段 允许like操作符
					checkResult = (value2==null?false:((String) value1).startsWith((String) value2));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = value1.equals(value2);
				}
			}else if ("nlike".equals(operator)){
				if(value1 instanceof String){
					//只有文本字段 允许nlike操作符
					checkResult = !(value2==null?false:((String) value1).startsWith((String) value2));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = !value1.equals(value2);
				}
			}else if ("=".equals(operator) || "eq".equals(operator)){
				checkResult = value1.equals(value2);
			}else if ("<>".equals(operator) || "ne".equals(operator)){
				checkResult = !value1.equals(value2);
			}else if (">=".equals(operator) || "ge".equals(operator)){
				if(value1 instanceof Integer){
					//只有数值字段 允许>=操作符
					checkResult = (value2==null?false:(((Integer) value1).intValue() >= ((Integer) value2).intValue()));
				}else if(value1 instanceof Double){
					//只有数值字段 允许>=操作符
					checkResult = (value2==null?false:(((Double) value1).doubleValue() >= ((Double) value2).doubleValue()));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = value1.equals(value2);
				}
			}else if (">".equals(operator) || "gt".equals(operator)){
				if(value1 instanceof Integer){
					//只有数值字段 允许>操作符
					checkResult = (value2==null?false:(((Integer) value1).intValue() > ((Integer) value2).intValue()));
				}else if(value1 instanceof Double){
					//只有数值字段 允许>操作符
					checkResult = (value2==null?false:(((Double) value1).doubleValue() > ((Double) value2).doubleValue()));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = value1.equals(value2);
				}
			}else if ("<=".equals(operator) || "le".equals(operator)){
				if(value1 instanceof Integer){
					//只有数值字段 允许<=操作符
					checkResult = (value2==null?false:(((Integer) value1).intValue() <= ((Integer) value2).intValue()));
				}else if(value1 instanceof Double){
					//只有数值字段 允许<=操作符
					checkResult = (value2==null?false:(((Double) value1).doubleValue() <= ((Double) value2).doubleValue()));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = value1.equals(value2);
				}
			}else if ("<".equals(operator) || "lt".equals(operator)){
				if(value1 instanceof Integer){
					//只有数值字段 允许<操作符
					checkResult = (value2==null?false:(((Integer) value1).intValue() < ((Integer) value2).intValue()));
				}else if(value1 instanceof Double){
					//只有数值字段 允许<操作符
					checkResult = (value2==null?false:(((Double) value1).doubleValue() < ((Double) value2).doubleValue()));
				}else{
					//其它类型的字段 替换为 “等于”
					checkResult = value1.equals(value2);
				}
			}else if ("between".equals(operator)){
				Object[] params = (Object[])value2;
				//between类型的公式 且字段非日期类型
				if(params[0]!=null && params[1]==null){
					//仅有后面的值
					if(value1 instanceof Integer){
						//只有数值字段 允许>=操作符
						checkResult = (((Integer) value1).intValue() >= ((Integer) params[0]).intValue());
					}else if(value1 instanceof Double){
						//只有数值字段 允许>=操作符
						checkResult = (((Double) value1).doubleValue() >= ((Double) params[0]).doubleValue());
					}else{
						//其它类型的字段 替换为 “等于”
						checkResult = value1.equals(params[0]);
					}
				}else if(params[0]==null && params[1]!=null){
					//仅有前面的值
					if(value1 instanceof Integer){
						//只有数值字段 允许<=操作符
						checkResult = (((Integer) value1).intValue() <= ((Integer) params[1]).intValue());
					}else if(value1 instanceof Double){
						//只有数值字段 允许<=操作符
						checkResult = (((Double) value1).doubleValue() <= ((Double) params[1]).doubleValue());
					}else{
						//其它类型的字段 替换为 “等于”
						checkResult = value1.equals(params[1]);
					}
				}else{
					//两个值都有
					if(value1 instanceof Integer){
						//只有数值字段 允许>=操作符
						checkResult = (((Integer) value1).intValue() >= ((Integer) params[0]).intValue() && ((Integer) value1).intValue() <= ((Integer) params[1]).intValue());
					}else if(value1 instanceof Double){
						//只有数值字段 允许>=操作符
						checkResult = (((Double) value1).doubleValue() >= ((Double) params[0]).doubleValue() && ((Double) value1).doubleValue() <= ((Double) params[1]).doubleValue());
					}else{
						//其它类型的字段 替换为 “等于”
						checkResult = value1.equals(value2);
					}
				}
			}
		}
		return checkResult;
	}

	public static String replaceRegulation(String syntaxString,Object obj){
		while(syntaxString.indexOf("{")>=0){
			String propertyString = syntaxString.substring(syntaxString.indexOf("{")+1,syntaxString.indexOf("}"));
			Object synValue = getValueByProperties(obj,propertyString);
			String syntaxLeft = syntaxString.substring(0,syntaxString.indexOf("{"));
			String syntaxRight = syntaxString.substring(syntaxString.indexOf("}")+1);
			if(synValue ==null){
				syntaxString = syntaxLeft+syntaxRight;
			}else if(synValue instanceof Set){
				JSONArray valueArray = new JSONArray();
				for(Object v:((Set<?>) synValue).toArray()){
					valueArray.add(v);
				}
				syntaxString = syntaxLeft+valueArray+syntaxRight;
			}else if (synValue instanceof EntityI){
				syntaxString = syntaxLeft+((EntityI)synValue).pkValue()+syntaxRight;
			}else{
				syntaxString = syntaxLeft+synValue+syntaxRight;
			}
		}
		return syntaxString;
	}
	
	public static Date getCleanDate(Date date){
		Date newDate = null;
		if(date!=null){
			try {
				newDate = sdf.parse(sdf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return newDate;
	}
	
	public static String formatDate(Date date){
		String newDateString = null;
		if(date!=null){
			newDateString = sdf.format(date);
		}
		return newDateString;
	}
	public static Date parseDate(String dateString){
		Date newDate = null;
		if(dateString!=null){
			try {
				newDate = sdf.parse(dateString);
			} catch (ParseException e) {
			}
		}
		return newDate;
	}
}
