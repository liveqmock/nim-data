package com.poweruniverse.nim.data.entity.sys;
import com.poweruniverse.nim.data.entity.sys.base.BaseYongHuZT;

/*
* 实体类：用户状态
*/
public class YongHuZT  extends BaseYongHuZT {
	private static final long serialVersionUID = 1L;
	public static final int WeiQiYong = 0;//未启用
	public static final int TingYong = 1;//停用
	public static final int ZhengChang = 2;//正常


	// constructors
	public YongHuZT () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public YongHuZT (java.lang.Integer id) {
		super(id);
	}

	public YongHuZT (java.lang.Integer id,String yongHuZTMC) {
		super(id);
		this.setYongHuZTMC(yongHuZTMC);
	}

	protected void initialize () {}
	
	
}