package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.BuMen;

/*
* 实体类：部门
*/
@Version("2015-01-20 18:30:35")
public abstract class BaseBuMen  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseBuMen () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBuMen (java.lang.Integer id) {
		this.setBuMenDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：buMenDM
	private java.lang.Integer buMenDM = null;
	public java.lang.Integer getBuMenDM(){return this.buMenDM ;}
	public void setBuMenDM(java.lang.Integer buMenDM){this.buMenDM = buMenDM;}

			
	// 属性：部门名称 （buMenMC）
	private java.lang.String buMenMC = null;
	public java.lang.String getBuMenMC(){return this.buMenMC ;}
	public void setBuMenMC(java.lang.String buMenMC){this.buMenMC = buMenMC;}
	
			
	// 属性：部门编号 （buMenBH）
	private java.lang.String buMenBH = null;
	public java.lang.String getBuMenBH(){return this.buMenBH ;}
	public void setBuMenBH(java.lang.String buMenBH){this.buMenBH = buMenBH;}
	
	// 对象：上级部门 （shangJiBM）
	private BuMen shangJiBM;
	public BuMen getShangJiBM(){return this.shangJiBM ;}
	public void setShangJiBM(BuMen shangJiBM){this.shangJiBM = shangJiBM;}

			
	// 属性：部门级别 （buMenJB）
	private java.lang.Integer buMenJB = new java.lang.Integer(0);
	public java.lang.Integer getBuMenJB(){return this.buMenJB ;}
	public void setBuMenJB(java.lang.Integer buMenJB){this.buMenJB = buMenJB;}
	
			
	// 属性：部门全名 （buMenQM）
	private java.lang.String buMenQM = null;
	public java.lang.String getBuMenQM(){return this.buMenQM ;}
	public void setBuMenQM(java.lang.String buMenQM){this.buMenQM = buMenQM;}
	
			
	// 属性：部门简介 （buMenJJ）
	private java.lang.String buMenJJ = null;
	public java.lang.String getBuMenJJ(){return this.buMenJJ ;}
	public void setBuMenJJ(java.lang.String buMenJJ){this.buMenJJ = buMenJJ;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof BuMen)) return false;
		else {
			BuMen entity = (BuMen) obj;
			if (null == this.getBuMenDM() || null == entity.getBuMenDM()) return false;
			else return (this.getBuMenDM().equals(entity.getBuMenDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getBuMenDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getBuMenDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.buMenMC+"";
	}

	public Integer pkValue() {
		return this.buMenDM;
	}

	public String pkName() {
		return "buMenDM";
	}

	public void pkNull() {
		this.buMenDM = null;;
	}
	
	public int compareTo(Object o) {
		BuMen obj = (BuMen)o;
		if(this.getBuMenDM()==null){
			return 1;
		}
		return this.getBuMenDM().compareTo(obj.getBuMenDM());
	}
	
	public BuMen clone(){
		BuMen buMen = new BuMen();
		
		buMen.setBuMenMC(buMenMC);
		buMen.setBuMenBH(buMenBH);
		buMen.setShangJiBM(shangJiBM);
		buMen.setBuMenJB(buMenJB);
		buMen.setBuMenQM(buMenQM);
		buMen.setBuMenJJ(buMenJJ);
		
		return buMen;
	}
	
	
	
	
	
	
}