package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.ZiDuan;
import com.poweruniverse.nim.data.entity.ZiDuanLX;

/*
* 实体类：字段
*/
@Version("2015-01-20 18:30:34")
public abstract class BaseZiDuan  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseZiDuan () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseZiDuan (java.lang.Integer id) {
		this.setZiDuanDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：ziDuanDM
	private java.lang.Integer ziDuanDM = null;
	public java.lang.Integer getZiDuanDM(){return this.ziDuanDM ;}
	public void setZiDuanDM(java.lang.Integer ziDuanDM){this.ziDuanDM = ziDuanDM;}

			
	// 属性：字段代号 （ziDuanDH）
	private java.lang.String ziDuanDH = null;
	public java.lang.String getZiDuanDH(){return this.ziDuanDH ;}
	public void setZiDuanDH(java.lang.String ziDuanDH){this.ziDuanDH = ziDuanDH;}
	
			
	// 属性：字段标题 （ziDuanBT）
	private java.lang.String ziDuanBT = null;
	public java.lang.String getZiDuanBT(){return this.ziDuanBT ;}
	public void setZiDuanBT(java.lang.String ziDuanBT){this.ziDuanBT = ziDuanBT;}
	
			
	// 属性：关联父类字段名称 （guanLianFLZD）
	private java.lang.String guanLianFLZD = null;
	public java.lang.String getGuanLianFLZD(){return this.guanLianFLZD ;}
	public void setGuanLianFLZD(java.lang.String guanLianFLZD){this.guanLianFLZD = guanLianFLZD;}
	
	// 对象：实体类 （shiTiLei）
	private ShiTiLei shiTiLei;
	public ShiTiLei getShiTiLei(){return this.shiTiLei ;}
	public void setShiTiLei(ShiTiLei shiTiLei){this.shiTiLei = shiTiLei;}

	// 对象：字段类型 （ziDuanLX）
	private ZiDuanLX ziDuanLX;
	public ZiDuanLX getZiDuanLX(){return this.ziDuanLX ;}
	public void setZiDuanLX(ZiDuanLX ziDuanLX){this.ziDuanLX = ziDuanLX;}

			
	// 属性：数据库列名 （lieMing）
	private java.lang.String lieMing = null;
	public java.lang.String getLieMing(){return this.lieMing ;}
	public void setLieMing(java.lang.String lieMing){this.lieMing = lieMing;}
	
			
	// 属性：字段长度 （ziDuanCD）
	private java.lang.Integer ziDuanCD = new java.lang.Integer(0);
	public java.lang.Integer getZiDuanCD(){return this.ziDuanCD ;}
	public void setZiDuanCD(java.lang.Integer ziDuanCD){this.ziDuanCD = ziDuanCD;}
	
			
	// 属性：字段精度 （ziDuanJD）
	private java.lang.Integer ziDuanJD = new java.lang.Integer(0);
	public java.lang.Integer getZiDuanJD(){return this.ziDuanJD ;}
	public void setZiDuanJD(java.lang.Integer ziDuanJD){this.ziDuanJD = ziDuanJD;}
	
			
	// 属性：字段说明 （ziDuanSM）
	private java.lang.String ziDuanSM = null;
	public java.lang.String getZiDuanSM(){return this.ziDuanSM ;}
	public void setZiDuanSM(java.lang.String ziDuanSM){this.ziDuanSM = ziDuanSM;}
	
			
	// 属性：允许空值 （yunXuKZ）
	private java.lang.Boolean yunXuKZ = new java.lang.Boolean(false);
	public java.lang.Boolean getYunXuKZ(){return this.yunXuKZ ;}
	public void setYunXuKZ(java.lang.Boolean yunXuKZ){this.yunXuKZ = yunXuKZ;}
	
			
	// 属性：延迟加载 （yanChiJZ）
	private java.lang.Boolean yanChiJZ = new java.lang.Boolean(false);
	public java.lang.Boolean getYanChiJZ(){return this.yanChiJZ ;}
	public void setYanChiJZ(java.lang.Boolean yanChiJZ){this.yanChiJZ = yanChiJZ;}
	
	// 对象：关联实体类 （guanLianSTL）
	private ShiTiLei guanLianSTL;
	public ShiTiLei getGuanLianSTL(){return this.guanLianSTL ;}
	public void setGuanLianSTL(ShiTiLei guanLianSTL){this.guanLianSTL = guanLianSTL;}

			
	// 属性：是否唯一 （shiFouWY）
	private java.lang.Boolean shiFouWY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWY(){return this.shiFouWY ;}
	public void setShiFouWY(java.lang.Boolean shiFouWY){this.shiFouWY = shiFouWY;}
	
			
	// 属性：是否使用 （shiFouSY）
	private java.lang.Boolean shiFouSY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSY(){return this.shiFouSY ;}
	public void setShiFouSY(java.lang.Boolean shiFouSY){this.shiFouSY = shiFouSY;}
	
	// 对象：关联父类字段对象 （guanLianFLZDObj）
	private ZiDuan guanLianFLZDObj;
	public ZiDuan getGuanLianFLZDObj(){return this.guanLianFLZDObj ;}
	public void setGuanLianFLZDObj(ZiDuan guanLianFLZDObj){this.guanLianFLZDObj = guanLianFLZDObj;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof ZiDuan)) return false;
		else {
			ZiDuan entity = (ZiDuan) obj;
			if (null == this.getZiDuanDM() || null == entity.getZiDuanDM()) return false;
			else return (this.getZiDuanDM().equals(entity.getZiDuanDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getZiDuanDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getZiDuanDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.ziDuanBT+"";
	}

	public Integer pkValue() {
		return this.ziDuanDM;
	}

	public String pkName() {
		return "ziDuanDM";
	}

	public void pkNull() {
		this.ziDuanDM = null;;
	}
	
	public int compareTo(Object o) {
		ZiDuan obj = (ZiDuan)o;
		if(this.getZiDuanDM()==null){
			return 1;
		}
		return this.getZiDuanDM().compareTo(obj.getZiDuanDM());
	}
	
	public ZiDuan clone(){
		ZiDuan ziDuan = new ZiDuan();
		
		ziDuan.setZiDuanDH(ziDuanDH);
		ziDuan.setZiDuanBT(ziDuanBT);
		ziDuan.setGuanLianFLZD(guanLianFLZD);
		ziDuan.setShiTiLei(shiTiLei);
		ziDuan.setZiDuanLX(ziDuanLX);
		ziDuan.setLieMing(lieMing);
		ziDuan.setZiDuanCD(ziDuanCD);
		ziDuan.setZiDuanJD(ziDuanJD);
		ziDuan.setZiDuanSM(ziDuanSM);
		ziDuan.setYunXuKZ(yunXuKZ);
		ziDuan.setYanChiJZ(yanChiJZ);
		ziDuan.setGuanLianSTL(guanLianSTL);
		ziDuan.setShiFouWY(shiFouWY);
		ziDuan.setShiFouSY(shiFouSY);
		ziDuan.setGuanLianFLZDObj(guanLianFLZDObj);
		
		return ziDuan;
	}
	
	
	
	
	
	
}