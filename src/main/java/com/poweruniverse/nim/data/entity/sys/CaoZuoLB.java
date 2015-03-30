package com.poweruniverse.nim.data.entity.sys;
import com.poweruniverse.nim.data.entity.sys.base.BaseCaoZuoLB;

/*
* 实体类：操作类别
*/
public class CaoZuoLB  extends BaseCaoZuoLB {
	private static final long serialVersionUID = 1L;

	// constructors
	public CaoZuoLB () {
	}

	/**
	 * Constructor for primary key
	 */
	public CaoZuoLB (java.lang.Integer id) {
		super(id);
	}

	public CaoZuoLB (java.lang.Integer id,String caoZuoLBMC) {
		super(id);
		this.setCaoZuoLBMC(caoZuoLBMC);
	}

	protected void initialize () {}
	
	public static int Json = 1;
	public static int Page = 2;
	public static int Status = 3;
	public static int Upload = 5;
	public static int Export = 6;

}