package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.LiuChengJSBL;
import com.poweruniverse.nim.data.entity.ZiDuanLX;
/*
* 实体类：流程检视变量
*/
public abstract class BaseLiuChengJSBL  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengJSBL () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengJSBL (java.lang.Integer id) {
		this.setLiuChengJSBLDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：流程检视变量代号 （liuChengJSBLDH）
	private java.lang.String liuChengJSBLDH = null;
	public java.lang.String getLiuChengJSBLDH(){return this.liuChengJSBLDH ;}
	public void setLiuChengJSBLDH(java.lang.String liuChengJSBLDH){this.liuChengJSBLDH = liuChengJSBLDH;}
	
	// 主键：liuChengJSBLDM
	private java.lang.Integer liuChengJSBLDM = null;
	public java.lang.Integer getLiuChengJSBLDM(){return this.liuChengJSBLDM ;}
	public void setLiuChengJSBLDM(java.lang.Integer liuChengJSBLDM){this.liuChengJSBLDM = liuChengJSBLDM;}

			
	// 属性：流程检视变量名称 （liuChengJSBLMC）
	private java.lang.String liuChengJSBLMC = null;
	public java.lang.String getLiuChengJSBLMC(){return this.liuChengJSBLMC ;}
	public void setLiuChengJSBLMC(java.lang.String liuChengJSBLMC){this.liuChengJSBLMC = liuChengJSBLMC;}
	
	// 对象：字段类型 （ziDuanLX）
	private ZiDuanLX ziDuanLX;
	public ZiDuanLX getZiDuanLX(){return this.ziDuanLX ;}
	public void setZiDuanLX(ZiDuanLX ziDuanLX){this.ziDuanLX = ziDuanLX;}

			
	// 属性：流程检视变量值 （liuChengJSBLZ）
	private java.lang.String liuChengJSBLZ = null;
	public java.lang.String getLiuChengJSBLZ(){return this.liuChengJSBLZ ;}
	public void setLiuChengJSBLZ(java.lang.String liuChengJSBLZ){this.liuChengJSBLZ = liuChengJSBLZ;}
	
	// 对象：流程检视 （liuChengJS）
	private LiuChengJS liuChengJS;
	public LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

			
	// 属性：原值 （yuanZ）
	private java.lang.String yuanZ = null;
	public java.lang.String getYuanZ(){return this.yuanZ ;}
	public void setYuanZ(java.lang.String yuanZ){this.yuanZ = yuanZ;}
	
			
	// 属性：原值显示 （yuanZXS）
	private java.lang.String yuanZXS = null;
	public java.lang.String getYuanZXS(){return this.yuanZXS ;}
	public void setYuanZXS(java.lang.String yuanZXS){this.yuanZXS = yuanZXS;}
	
			
	// 属性：流程检视变量值显示 （liuChengJSBLZXS）
	private java.lang.String liuChengJSBLZXS = null;
	public java.lang.String getLiuChengJSBLZXS(){return this.liuChengJSBLZXS ;}
	public void setLiuChengJSBLZXS(java.lang.String liuChengJSBLZXS){this.liuChengJSBLZXS = liuChengJSBLZXS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof LiuChengJSBL)) return false;
		else {
			LiuChengJSBL entity = (LiuChengJSBL) obj;
			if (null == this.getLiuChengJSBLDM() || null == entity.getLiuChengJSBLDM()) return false;
			else return (this.getLiuChengJSBLDM().equals(entity.getLiuChengJSBLDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengJSBLDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengJSBLDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengJSBLMC+"";
	}

	public Integer pkValue() {
		return this.liuChengJSBLDM;
	}

	public String pkName() {
		return "liuChengJSBLDM";
	}

	public void pkNull() {
		this.liuChengJSBLDM = null;;
	}
	
	public int compareTo(Object o) {
		LiuChengJSBL obj = (LiuChengJSBL)o;
		if(this.getLiuChengJSBLDM()==null){
			return 1;
		}
		return this.getLiuChengJSBLDM().compareTo(obj.getLiuChengJSBLDM());
	}
	
	public LiuChengJSBL clone(){
		LiuChengJSBL liuChengJSBL = new LiuChengJSBL();
		
		liuChengJSBL.setLiuChengJSBLDH(liuChengJSBLDH);
		liuChengJSBL.setLiuChengJSBLMC(liuChengJSBLMC);
		liuChengJSBL.setZiDuanLX(ziDuanLX);
		liuChengJSBL.setLiuChengJSBLZ(liuChengJSBLZ);
		liuChengJSBL.setLiuChengJS(liuChengJS);
		liuChengJSBL.setYuanZ(yuanZ);
		liuChengJSBL.setYuanZXS(yuanZXS);
		liuChengJSBL.setLiuChengJSBLZXS(liuChengJSBLZXS);
		
		return liuChengJSBL;
	}
	
	
	
	
	
	
}