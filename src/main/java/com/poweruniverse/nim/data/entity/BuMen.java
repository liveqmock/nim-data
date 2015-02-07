package com.poweruniverse.nim.data.entity;

import com.poweruniverse.nim.data.entity.base.BaseBuMen;

/*
* 实体类：部门
*/
public class BuMen  extends BaseBuMen {
	private static final long serialVersionUID = 1L;
	public static final String KeJiGLB_KJ_BH = "014301";
	// constructors
	public BuMen () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BuMen (java.lang.Integer id) {
		super(id);
	}

	public BuMen (java.lang.Integer id,String buMenMC,String buMenBH) {
		super(id);
		this.setBuMenBH(buMenBH);
		this.setBuMenMC(buMenMC);
	}

	protected void initialize () {}
	
	
}