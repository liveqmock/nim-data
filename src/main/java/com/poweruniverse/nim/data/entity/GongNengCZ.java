package com.poweruniverse.nim.data.entity;


import com.poweruniverse.nim.data.entity.base.BaseGongNengCZ;

/*
* 实体类：功能操作
*/
public class GongNengCZ  extends BaseGongNengCZ  {
	private static final long serialVersionUID = 1L;
 
	// constructors
	public GongNengCZ () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GongNengCZ (java.lang.Integer id) {
		super(id);
	}

	public GongNengCZ (java.lang.Integer id,String caoZuoMC,String caoZuoDH) {
		super(id);
		this.setCaoZuoDH(caoZuoDH);
		this.setCaoZuoMC(caoZuoMC);
		this.setDuiXiangXG(true);
		this.setKeYiSQ(true);
	}

	protected void initialize () {}
	
//	czmbData.set("caoZuoMBLJ", xiTongDH+"/"+gongNengLBDH+"/"+gongNengDH+"/");//操作模版路径

}