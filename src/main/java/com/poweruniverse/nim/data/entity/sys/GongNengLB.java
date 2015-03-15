package com.poweruniverse.nim.data.entity.sys;
import com.poweruniverse.nim.data.entity.sys.base.BaseGongNengLB;

/*
* 实体类：操作类别
*/
public class GongNengLB  extends BaseGongNengLB {
	private static final long serialVersionUID = 1L;

	// constructors
	public GongNengLB () {
	}

	/**
	 * Constructor for primary key
	 */
	public GongNengLB (java.lang.Integer id) {
		super(id);
	}

	public GongNengLB (java.lang.Integer id,String gongNengLBMC) {
		super(id);
		this.setGongNengLBMC(gongNengLBMC);
	}

	protected void initialize () {}
	
	public static int Json = 1;
	public static int Form = 2;
	public static int Status = 3;
	public static int UserDefine = 5;
	public static int Export = 6;
	public static int Report = 7;
	
	public static int ProcessFormView = 10;
	public static int FormView = 11;
	

}