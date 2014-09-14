package com.poweruniverse.nim.data.service.utils;




public class PropertyQuery extends Query{
	private String property=null;
	private String operator=null;
	private String assist=null;
	private String value=null;
	private String type=null;
	
//	public PropertyQuery(String property,String operator,Object value){
//		this.property = property;
//		this.operator = operator==null?"=":operator;
//		this.value = value;
//	}
	
	public PropertyQuery(String property,String type,String assist,String operator,String value){
		this.property = property;
		this.type = type;
		this.assist = assist;
		this.operator = operator==null?"=":operator;
		this.value = value;
	}
	
	public PropertyQuery(String property,String assist,String operator,String value){
		this.property = property;
		this.assist = assist;
		this.operator = operator==null?"=":operator;
		this.value = value;
	}
	
	public PropertyQuery(String property,String operator,String value){
		this.property = property;
		this.operator = operator==null?"=":operator;
		this.value = value;
	}
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getAssist() {
		return assist;
	}


	public void setAssist(String assist) {
		this.assist = assist;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return property + " "+operator+" "+value ;
	}
}

