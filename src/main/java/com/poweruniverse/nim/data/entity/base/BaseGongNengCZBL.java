package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.GongNengCZBL;
import com.poweruniverse.nim.data.entity.ZiDuan;
/*
* 实体类：操作变量
*/
public abstract class BaseGongNengCZBL  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengCZBL () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengCZBL (java.lang.Integer id) {
		this.setCaoZuoBLDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：caoZuoBLDM
	private java.lang.Integer caoZuoBLDM = null;
	public java.lang.Integer getCaoZuoBLDM(){return this.caoZuoBLDM ;}
	public void setCaoZuoBLDM(java.lang.Integer caoZuoBLDM){this.caoZuoBLDM = caoZuoBLDM;}

	// 对象：功能操作 （gongNengCZ）
	private GongNengCZ gongNengCZ;
	public GongNengCZ getGongNengCZ(){return this.gongNengCZ ;}
	public void setGongNengCZ(GongNengCZ gongNengCZ){this.gongNengCZ = gongNengCZ;}

	// 对象：字段 （ziDuan）
	private ZiDuan ziDuan;
	public ZiDuan getZiDuan(){return this.ziDuan ;}
	public void setZiDuan(ZiDuan ziDuan){this.ziDuan = ziDuan;}

			
	// 属性：是否记录变化 （shiFouJJLBH）
	private java.lang.Boolean shiFouJJLBH = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouJJLBH(){return this.shiFouJJLBH ;}
	public void setShiFouJJLBH(java.lang.Boolean shiFouJJLBH){this.shiFouJJLBH = shiFouJJLBH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNengCZBL)) return false;
		else {
			GongNengCZBL entity = (GongNengCZBL) obj;
			if (null == this.getCaoZuoBLDM() || null == entity.getCaoZuoBLDM()) return false;
			else return (this.getCaoZuoBLDM().equals(entity.getCaoZuoBLDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCaoZuoBLDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCaoZuoBLDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoBLDM+"";
	}

	public Integer pkValue() {
		return this.caoZuoBLDM;
	}

	public String pkName() {
		return "caoZuoBLDM";
	}

	public void pkNull() {
		this.caoZuoBLDM = null;;
	}
	
	public int compareTo(Object o) {
		GongNengCZBL obj = (GongNengCZBL)o;
		if(this.getCaoZuoBLDM()==null){
			return 1;
		}
		return this.getCaoZuoBLDM().compareTo(obj.getCaoZuoBLDM());
	}
	
	public GongNengCZBL clone(){
		GongNengCZBL gongNengCZBL = new GongNengCZBL();
		
		gongNengCZBL.setGongNengCZ(gongNengCZ);
		gongNengCZBL.setZiDuan(ziDuan);
		gongNengCZBL.setShiFouJJLBH(shiFouJJLBH);
		
		return gongNengCZBL;
	}
	
	
	
	
	
	
}