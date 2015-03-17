package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：流程检视变量
*/
@Version("2015-03-16 11:16:00")
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
	
	// 对象：流程检视 （liuChengJS）
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

			
	// 属性：流程检视变量代号 （liuChengJSBLDH）
	private java.lang.String liuChengJSBLDH = null;
	public java.lang.String getLiuChengJSBLDH(){return this.liuChengJSBLDH ;}
	public void setLiuChengJSBLDH(java.lang.String liuChengJSBLDH){this.liuChengJSBLDH = liuChengJSBLDH;}
	
			
	// 属性：流程检视变量名称 （liuChengJSBLMC）
	private java.lang.String liuChengJSBLMC = null;
	public java.lang.String getLiuChengJSBLMC(){return this.liuChengJSBLMC ;}
	public void setLiuChengJSBLMC(java.lang.String liuChengJSBLMC){this.liuChengJSBLMC = liuChengJSBLMC;}
	
	// 主键：liuChengJSBLDM
	private java.lang.Integer liuChengJSBLDM = null;
	public java.lang.Integer getLiuChengJSBLDM(){return this.liuChengJSBLDM ;}
	public void setLiuChengJSBLDM(java.lang.Integer liuChengJSBLDM){this.liuChengJSBLDM = liuChengJSBLDM;}

			
	// 属性：流程检视变量值 （liuChengJSBLZ）
	private java.lang.String liuChengJSBLZ = null;
	public java.lang.String getLiuChengJSBLZ(){return this.liuChengJSBLZ ;}
	public void setLiuChengJSBLZ(java.lang.String liuChengJSBLZ){this.liuChengJSBLZ = liuChengJSBLZ;}
	
			
	// 属性：流程检视变量值显示 （liuChengJSBLZXS）
	private java.lang.String liuChengJSBLZXS = null;
	public java.lang.String getLiuChengJSBLZXS(){return this.liuChengJSBLZXS ;}
	public void setLiuChengJSBLZXS(java.lang.String liuChengJSBLZXS){this.liuChengJSBLZXS = liuChengJSBLZXS;}
	
	// 对象：字段类型 （ziDuanLX）
	private com.poweruniverse.nim.data.entity.sys.ZiDuanLX ziDuanLX;
	public com.poweruniverse.nim.data.entity.sys.ZiDuanLX getZiDuanLX(){return this.ziDuanLX ;}
	public void setZiDuanLX(com.poweruniverse.nim.data.entity.sys.ZiDuanLX ziDuanLX){this.ziDuanLX = ziDuanLX;}

			
	// 属性：原值 （yuanZ）
	private java.lang.String yuanZ = null;
	public java.lang.String getYuanZ(){return this.yuanZ ;}
	public void setYuanZ(java.lang.String yuanZ){this.yuanZ = yuanZ;}
	
			
	// 属性：原值显示 （yuanZXS）
	private java.lang.String yuanZXS = null;
	public java.lang.String getYuanZXS(){return this.yuanZXS ;}
	public void setYuanZXS(java.lang.String yuanZXS){this.yuanZXS = yuanZXS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.LiuChengJSBL)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.LiuChengJSBL entity = (com.poweruniverse.nim.data.entity.sys.LiuChengJSBL) obj;
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
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL obj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSBL)o;
		if(this.getLiuChengJSBLDM()==null){
			return 1;
		}
		return this.getLiuChengJSBLDM().compareTo(obj.getLiuChengJSBLDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSBL clone(){
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL liuChengJSBL = new com.poweruniverse.nim.data.entity.sys.LiuChengJSBL();
		
		liuChengJSBL.setLiuChengJS(liuChengJS);
		liuChengJSBL.setLiuChengJSBLDH(liuChengJSBLDH);
		liuChengJSBL.setLiuChengJSBLMC(liuChengJSBLMC);
		liuChengJSBL.setLiuChengJSBLZ(liuChengJSBLZ);
		liuChengJSBL.setLiuChengJSBLZXS(liuChengJSBLZXS);
		liuChengJSBL.setZiDuanLX(ziDuanLX);
		liuChengJSBL.setYuanZ(yuanZ);
		liuChengJSBL.setYuanZXS(yuanZXS);
		
		return liuChengJSBL;
	}
	
	
	
	
	
	
}