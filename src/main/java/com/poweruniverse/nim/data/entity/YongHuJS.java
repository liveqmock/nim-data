package com.poweruniverse.nim.data.entity;

import com.poweruniverse.nim.data.entity.base.BaseYongHuJS;

/*
* 实体类：用户角色
*/
public class YongHuJS extends BaseYongHuJS {
	private static final long serialVersionUID = 1L;

	// constructors
	public YongHuJS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public YongHuJS (java.lang.Integer id) {
		this.setYongHuJSDM(id);
		initialize();
	}

	public YongHuJS (java.lang.Integer id,JueSe jueSe) {
		this.setYongHuJSDM(id);
		this.setJueSe(jueSe);
	}

	protected void initialize () {}
	
	public String toString() {
		return this.getJueSe()==null?(""+this.getYongHuJSDM()):this.getJueSe().toString();
	}
}