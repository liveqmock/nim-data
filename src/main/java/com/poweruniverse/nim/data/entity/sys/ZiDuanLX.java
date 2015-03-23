package com.poweruniverse.nim.data.entity.sys;

import com.poweruniverse.nim.data.entity.sys.base.BaseZiDuanLX;

/*
* 实体类：字段类型
*/
public class ZiDuanLX  extends BaseZiDuanLX {
	private static final long serialVersionUID = 1L;
	public static final int ZiDuanLX_STRING = 1;
	public static final int ZiDuanLX_INT = 2;
	public static final int ZiDuanLX_DOUBLE = 10;
	public static final int ZiDuanLX_BOOLEAN = 5;
	public static final int ZiDuanLX_DATE = 3;
	public static final int ZiDuanLX_TEXT = 7;
	public static final int ZiDuanLX_MONTH = 4;
	public static final int ZiDuanLX_OBJECT = 9;
	public static final int ZiDuanLX_SET = 8;
	public static final int ZiDuanLX_File = 11;
	public static final int ZiDuanLX_Fileset = 12;
	public static final int ZiDuanLX_Dictionary = 13;
	public static final String ZiDuanLX_INT_DH = "int";

	// constructors
	public ZiDuanLX () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ZiDuanLX (java.lang.Integer id) {
		super(id);
	}

	public ZiDuanLX (java.lang.Integer id,String ziDuanLXDH) {
		super(id);
		this.setZiDuanLXDH(ziDuanLXDH);
	}

	protected void initialize () {}
	
	//关联对象字段
	public static boolean isObjectType(String type){
		return "object".equalsIgnoreCase(type) || "file".equalsIgnoreCase(type) ;
	}
	//集合字段
	public static boolean isSetType(String type){
		return "set".equalsIgnoreCase(type) || "fileset".equalsIgnoreCase(type) ;
	}
	
}