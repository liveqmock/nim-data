package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：字段
*/
@Version("2015-03-16 11:33:58")
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
	
	// 对象：关联父类字段 （guanLianFLZD）
	private com.poweruniverse.nim.data.entity.sys.ZiDuan guanLianFLZD;
	public com.poweruniverse.nim.data.entity.sys.ZiDuan getGuanLianFLZD(){return this.guanLianFLZD ;}
	public void setGuanLianFLZD(com.poweruniverse.nim.data.entity.sys.ZiDuan guanLianFLZD){this.guanLianFLZD = guanLianFLZD;}

	// 对象：实体类 （shiTiLei）
	private com.poweruniverse.nim.data.entity.sys.ShiTiLei shiTiLei;
	public com.poweruniverse.nim.data.entity.sys.ShiTiLei getShiTiLei(){return this.shiTiLei ;}
	public void setShiTiLei(com.poweruniverse.nim.data.entity.sys.ShiTiLei shiTiLei){this.shiTiLei = shiTiLei;}

	// 对象：字段类型 （ziDuanLX）
	private com.poweruniverse.nim.data.entity.sys.ZiDuanLX ziDuanLX;
	public com.poweruniverse.nim.data.entity.sys.ZiDuanLX getZiDuanLX(){return this.ziDuanLX ;}
	public void setZiDuanLX(com.poweruniverse.nim.data.entity.sys.ZiDuanLX ziDuanLX){this.ziDuanLX = ziDuanLX;}

			
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
	private com.poweruniverse.nim.data.entity.sys.ShiTiLei guanLianSTL;
	public com.poweruniverse.nim.data.entity.sys.ShiTiLei getGuanLianSTL(){return this.guanLianSTL ;}
	public void setGuanLianSTL(com.poweruniverse.nim.data.entity.sys.ShiTiLei guanLianSTL){this.guanLianSTL = guanLianSTL;}

			
	// 属性：是否唯一 （shiFouWY）
	private java.lang.Boolean shiFouWY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWY(){return this.shiFouWY ;}
	public void setShiFouWY(java.lang.Boolean shiFouWY){this.shiFouWY = shiFouWY;}
	
			
	// 属性：是否使用 （shiFouSY）
	private java.lang.Boolean shiFouSY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSY(){return this.shiFouSY ;}
	public void setShiFouSY(java.lang.Boolean shiFouSY){this.shiFouSY = shiFouSY;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.ZiDuan)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.ZiDuan entity = (com.poweruniverse.nim.data.entity.sys.ZiDuan) obj;
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
		com.poweruniverse.nim.data.entity.sys.ZiDuan obj = (com.poweruniverse.nim.data.entity.sys.ZiDuan)o;
		if(this.getZiDuanDM()==null){
			return 1;
		}
		return this.getZiDuanDM().compareTo(obj.getZiDuanDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.ZiDuan clone(){
		com.poweruniverse.nim.data.entity.sys.ZiDuan ziDuan = new com.poweruniverse.nim.data.entity.sys.ZiDuan();
		
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
		
		return ziDuan;
	}
	
	
	
	
	
	
}