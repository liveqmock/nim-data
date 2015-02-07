package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.BuMen;
/*
* 实体类：部门
*/
@Version("2012-12-31 12:12:23")
public abstract class BaseXiaoXiLB  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseXiaoXiLB () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseXiaoXiLB (java.lang.Integer id) {
		this.setXiaoXiLBDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：buMenDM
	private java.lang.Integer xiaoXiLBDM = null;
	public java.lang.Integer getXiaoXiLBDM(){return this.xiaoXiLBDM ;}
	public void setXiaoXiLBDM(java.lang.Integer xiaoXiLBDM){this.xiaoXiLBDM = xiaoXiLBDM;}

			


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof BuMen)) return false;
		else {
			BuMen entity = (BuMen) obj;
			if (null == this.getXiaoXiLBDM() || null == entity.getBuMenDM()) return false;
			else return (this.getXiaoXiLBDM().equals(entity.getBuMenDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getXiaoXiLBDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getXiaoXiLBDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return "";
	}

	public Integer pkValue() {
		return this.xiaoXiLBDM;
	}

	public String pkName() {
		return "buMenDM";
	}

	public void pkNull() {
		this.xiaoXiLBDM = null;;
	}
	
	public int compareTo(Object o) {
		BuMen obj = (BuMen)o;
		if(this.getXiaoXiLBDM()==null){
			return 1;
		}
		return this.getXiaoXiLBDM().compareTo(obj.getBuMenDM());
	}
	
	public BuMen clone(){
		BuMen buMen = new BuMen();
		
		
		return buMen;
	}
	
	
	
	
	
	
}