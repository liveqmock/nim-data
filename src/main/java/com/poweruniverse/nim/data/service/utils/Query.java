package com.poweruniverse.nim.data.service.utils;

public class Query{ 
	protected boolean sqlQuery = false;
	protected boolean userQuery = false;

	public boolean isSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(boolean sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public boolean isUserQuery() {
		return userQuery;
	}

	public void setUserQuery(boolean userQuery) {
		this.userQuery = userQuery;
	}

}
