package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：操作类别
*/
@Version("2015-04-04 02:05:43")
public abstract class BaseCaoZuoLB  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseCaoZuoLB () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCaoZuoLB (java.lang.Integer id) {
		this.setCaoZuoLBDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：caoZuoLBDM
	private java.lang.Integer caoZuoLBDM = null;
	public java.lang.Integer getCaoZuoLBDM(){return this.caoZuoLBDM ;}
	public void setCaoZuoLBDM(java.lang.Integer caoZuoLBDM){this.caoZuoLBDM = caoZuoLBDM;}

			
	// 属性：操作类别名称 （caoZuoLBMC）
	private java.lang.String caoZuoLBMC = null;
	public java.lang.String getCaoZuoLBMC(){return this.caoZuoLBMC ;}
	public void setCaoZuoLBMC(java.lang.String caoZuoLBMC){this.caoZuoLBMC = caoZuoLBMC;}
	
			
	// 属性：显示链接 （xianShiLJ）
	private java.lang.Boolean xianShiLJ = new java.lang.Boolean(false);
	public java.lang.Boolean getXianShiLJ(){return this.xianShiLJ ;}
	public void setXianShiLJ(java.lang.Boolean xianShiLJ){this.xianShiLJ = xianShiLJ;}
	
			
	// 属性：操作类别说明 （caoZuoLBSM）
	private java.lang.String caoZuoLBSM = null;
	public java.lang.String getCaoZuoLBSM(){return this.caoZuoLBSM ;}
	public void setCaoZuoLBSM(java.lang.String caoZuoLBSM){this.caoZuoLBSM = caoZuoLBSM;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.CaoZuoLB)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.CaoZuoLB entity = (com.poweruniverse.nim.data.entity.sys.CaoZuoLB) obj;
			if (null == this.getCaoZuoLBDM() || null == entity.getCaoZuoLBDM()) return false;
			else return (this.getCaoZuoLBDM().equals(entity.getCaoZuoLBDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCaoZuoLBDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCaoZuoLBDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoLBMC+"";
	}

	public Integer pkValue() {
		return this.caoZuoLBDM;
	}

	public String pkName() {
		return "caoZuoLBDM";
	}

	public void pkNull() {
		this.caoZuoLBDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.CaoZuoLB obj = (com.poweruniverse.nim.data.entity.sys.CaoZuoLB)o;
		if(this.getCaoZuoLBDM()==null){
			return 1;
		}
		return this.getCaoZuoLBDM().compareTo(obj.getCaoZuoLBDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.CaoZuoLB clone(){
		com.poweruniverse.nim.data.entity.sys.CaoZuoLB caoZuoLB = new com.poweruniverse.nim.data.entity.sys.CaoZuoLB();
		
		caoZuoLB.setCaoZuoLBMC(caoZuoLBMC);
		caoZuoLB.setXianShiLJ(xianShiLJ);
		caoZuoLB.setCaoZuoLBSM(caoZuoLBSM);
		
		return caoZuoLB;
	}
	
	
	
	
	
	
}