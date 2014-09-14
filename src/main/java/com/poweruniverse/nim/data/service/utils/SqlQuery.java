package com.poweruniverse.nim.data.service.utils;

public class SqlQuery extends Query{
	private String sql;

	public SqlQuery(String sql){
		this.sql = sql;
		this.sqlQuery = true;
	}
	
	public String getSql() {
		return sql;
	}

	@Override
	public String toString() {
		return "【"+sql+"】" ;
	}
}
