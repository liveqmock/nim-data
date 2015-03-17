package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能类别
*/
@Version("2015-03-09 01:28:15")
public abstract class BaseGongNengLB  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengLB () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengLB (java.lang.Integer id) {
		this.setGongNengLBDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：gongNengLBDM
	private java.lang.Integer gongNengLBDM = null;
	public java.lang.Integer getGongNengLBDM(){return this.gongNengLBDM ;}
	public void setGongNengLBDM(java.lang.Integer gongNengLBDM){this.gongNengLBDM = gongNengLBDM;}

			
	// 属性：功能类别名称 （gongNengLBMC）
	private java.lang.String gongNengLBMC = null;
	public java.lang.String getGongNengLBMC(){return this.gongNengLBMC ;}
	public void setGongNengLBMC(java.lang.String gongNengLBMC){this.gongNengLBMC = gongNengLBMC;}
	
			
	// 属性：功能类别序号 （gongNengLBXH）
	private java.lang.Integer gongNengLBXH = new java.lang.Integer(0);
	public java.lang.Integer getGongNengLBXH(){return this.gongNengLBXH ;}
	public void setGongNengLBXH(java.lang.Integer gongNengLBXH){this.gongNengLBXH = gongNengLBXH;}
	
			
	// 属性：功能类别代号 （gongNengLBDH）
	private java.lang.String gongNengLBDH = null;
	public java.lang.String getGongNengLBDH(){return this.gongNengLBDH ;}
	public void setGongNengLBDH(java.lang.String gongNengLBDH){this.gongNengLBDH = gongNengLBDH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengLB)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengLB entity = (com.poweruniverse.nim.data.entity.sys.GongNengLB) obj;
			if (null == this.getGongNengLBDM() || null == entity.getGongNengLBDM()) return false;
			else return (this.getGongNengLBDM().equals(entity.getGongNengLBDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengLBDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengLBDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengLBMC+"";
	}

	public Integer pkValue() {
		return this.gongNengLBDM;
	}

	public String pkName() {
		return "gongNengLBDM";
	}

	public void pkNull() {
		this.gongNengLBDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.GongNengLB obj = (com.poweruniverse.nim.data.entity.sys.GongNengLB)o;
		if(this.getGongNengLBDM()==null){
			return 1;
		}
		return this.getGongNengLBDM().compareTo(obj.getGongNengLBDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengLB clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengLB gongNengLB = new com.poweruniverse.nim.data.entity.sys.GongNengLB();
		
		gongNengLB.setGongNengLBMC(gongNengLBMC);
		gongNengLB.setGongNengLBXH(gongNengLBXH);
		gongNengLB.setGongNengLBDH(gongNengLBDH);
		
		return gongNengLB;
	}
	
	
	
	
	
	
}