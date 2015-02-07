package com.poweruniverse.nim.data.service.utils;




public class UserPropertyQuery extends Query{
	private String property=null;
	private String operator=null;
	private String value=null;
	private String type=null;
	
	
	public UserPropertyQuery(String property,String operator,String value){
		this.property = property;
		this.operator = operator==null?"=":operator;
		this.value = value;
		this.setUserQuery(true);
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

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserPropertyQuery:"+property + " "+operator+" "+value ;
	}
}

