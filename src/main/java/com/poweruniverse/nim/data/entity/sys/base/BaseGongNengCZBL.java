package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：操作变量
*/
@Version("2015-04-04 02:05:44")
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
	private com.poweruniverse.nim.data.entity.sys.GongNengCZ gongNengCZ;
	public com.poweruniverse.nim.data.entity.sys.GongNengCZ getGongNengCZ(){return this.gongNengCZ ;}
	public void setGongNengCZ(com.poweruniverse.nim.data.entity.sys.GongNengCZ gongNengCZ){this.gongNengCZ = gongNengCZ;}

	// 对象：字段 （ziDuan）
	private com.poweruniverse.nim.data.entity.sys.ZiDuan ziDuan;
	public com.poweruniverse.nim.data.entity.sys.ZiDuan getZiDuan(){return this.ziDuan ;}
	public void setZiDuan(com.poweruniverse.nim.data.entity.sys.ZiDuan ziDuan){this.ziDuan = ziDuan;}

			
	// 属性：是否仅记录编号 （shiFouJJLBH）
	private java.lang.Boolean shiFouJJLBH = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouJJLBH(){return this.shiFouJJLBH ;}
	public void setShiFouJJLBH(java.lang.Boolean shiFouJJLBH){this.shiFouJJLBH = shiFouJJLBH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengCZBL)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengCZBL entity = (com.poweruniverse.nim.data.entity.sys.GongNengCZBL) obj;
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
		com.poweruniverse.nim.data.entity.sys.GongNengCZBL obj = (com.poweruniverse.nim.data.entity.sys.GongNengCZBL)o;
		if(this.getCaoZuoBLDM()==null){
			return 1;
		}
		return this.getCaoZuoBLDM().compareTo(obj.getCaoZuoBLDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengCZBL clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengCZBL gongNengCZBL = new com.poweruniverse.nim.data.entity.sys.GongNengCZBL();
		
		gongNengCZBL.setGongNengCZ(gongNengCZ);
		gongNengCZBL.setZiDuan(ziDuan);
		gongNengCZBL.setShiFouJJLBH(shiFouJJLBH);
		
		return gongNengCZBL;
	}
	
	
	
	
	
	
}