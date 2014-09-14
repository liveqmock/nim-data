package com.poweruniverse.nim.data.service.utils;

import java.util.ArrayList;
import java.util.List;


public class Permit{
	private List<Query> queries = null;
	public Permit(){
		
	}
	public Permit(String property,String operator,String value){
		queries = new ArrayList<Query>();
		queries.add(new PropertyQuery(property,null,operator,value));
	}
	

	public List<Query> getQueries() {
		return queries;
	}
	
}