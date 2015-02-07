package com.poweruniverse.nim.data.service.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.poweruniverse.nim.data.entity.GongNengGZLTJGS;
import com.poweruniverse.nim.data.entity.GongNengLCGS;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMXGS;


public class Permit{
	private List<Query> queries = null;
	public Permit(){
		
	}
	public Permit(String property,String operator,String value){
		queries = new ArrayList<Query>();
		queries.add(new PropertyQuery(property,null,operator,value));
	}
	
	
	public Permit setFilterByGNLCGSS(Iterator<GongNengLCGS> gss){
		queries = new ArrayList<Query>();
		GongNengLCGS gs = null;
		while(gss.hasNext()){
			gs = gss.next();
			if(gs.getZiDuanCZF().equals("sql")){
				queries.add(new SqlQuery(gs.getCaoZuoQXGS()));
			}else if(gs.getZiDuanMS().startsWith("{")){
				queries.add(new UserPropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}else{
				queries.add(new PropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}
		}
		return this;
	}
	
	public Permit setFilterByGZLTJGSS(Iterator<GongNengGZLTJGS> gss){
		queries = new ArrayList<Query>();
		GongNengGZLTJGS gs = null;
		while(gss.hasNext()){
			gs = gss.next();
			if(gs.getZiDuanCZF().equals("sql")){
				queries.add(new SqlQuery(gs.getCaoZuoQXGS()));
			}else if(gs.getZiDuanMS().startsWith("{")){
				queries.add(new UserPropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}else{
				queries.add(new PropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}
		}
		return this;
	}

	
	public Permit setFilterByGNCZGSS(Iterator<JueSeQXGNCZMXGS> gss){
		queries = new ArrayList<Query>();
		JueSeQXGNCZMXGS gs = null;
		while(gss.hasNext()){
			gs = gss.next();
			if(gs.getZiDuanCZF().equals("sql")){
				queries.add(new SqlQuery(gs.getCaoZuoQXGS()));
			}else if(gs.getZiDuanMS().startsWith("{")){
				queries.add(new UserPropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}else{
				queries.add(new PropertyQuery(gs.getZiDuanMS(),gs.getZiDuanCZF(),gs.getZiDuanQZ()));
			}
		}
		return this;
	}
	
	
	public List<Query> getQueries() {
		return queries;
	}
	@Override
	public String toString() {
		if(this.queries == null){
			return "null";
		}
		return this.queries.toString();
	}
	
	
}