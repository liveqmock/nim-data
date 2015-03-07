package com.poweruniverse.nim.data.entity.system;

import com.poweruniverse.nim.data.entity.system.base.BaseZiDuan;

/*
* 实体类：字段
*/
public class ZiDuan  extends BaseZiDuan   { 
	private static final long serialVersionUID = 1L;

	// constructors
	public ZiDuan () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ZiDuan (java.lang.Integer id) {
		super(id);
	}

	public ZiDuan (java.lang.Integer id,String zdmc,String zddh) {
		super(id);
		this.setZiDuanBT(zdmc);
		this.setZiDuanDH(zddh);
	}

	protected void initialize () {}
	
	
}