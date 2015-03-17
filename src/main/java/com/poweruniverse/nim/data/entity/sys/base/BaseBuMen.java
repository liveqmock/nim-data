package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：部门
*/
@Version("2015-03-16 11:15:59")
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
	private com.poweruniverse.nim.data.entity.sys.BuMen shangJiBM;
	public com.poweruniverse.nim.data.entity.sys.BuMen getShangJiBM(){return this.shangJiBM ;}
	public void setShangJiBM(com.poweruniverse.nim.data.entity.sys.BuMen shangJiBM){this.shangJiBM = shangJiBM;}

			
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
	
			
	// 属性：汇总编号 （huiZongBH）
	private java.lang.String huiZongBH = null;
	public java.lang.String getHuiZongBH(){return this.huiZongBH ;}
	public void setHuiZongBH(java.lang.String huiZongBH){this.huiZongBH = huiZongBH;}
	
			
	// 属性：是否业务部门 （shiFouYWBM）
	private java.lang.Boolean shiFouYWBM = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouYWBM(){return this.shiFouYWBM ;}
	public void setShiFouYWBM(java.lang.Boolean shiFouYWBM){this.shiFouYWBM = shiFouYWBM;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.BuMen)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.BuMen entity = (com.poweruniverse.nim.data.entity.sys.BuMen) obj;
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
		com.poweruniverse.nim.data.entity.sys.BuMen obj = (com.poweruniverse.nim.data.entity.sys.BuMen)o;
		if(this.getBuMenDM()==null){
			return 1;
		}
		return this.getBuMenDM().compareTo(obj.getBuMenDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.BuMen clone(){
		com.poweruniverse.nim.data.entity.sys.BuMen buMen = new com.poweruniverse.nim.data.entity.sys.BuMen();
		
		buMen.setBuMenMC(buMenMC);
		buMen.setBuMenBH(buMenBH);
		buMen.setShangJiBM(shangJiBM);
		buMen.setBuMenJB(buMenJB);
		buMen.setBuMenQM(buMenQM);
		buMen.setBuMenJJ(buMenJJ);
		buMen.setHuiZongBH(huiZongBH);
		buMen.setShiFouYWBM(shiFouYWBM);
		
		return buMen;
	}
	
	
	
	
	
	
}