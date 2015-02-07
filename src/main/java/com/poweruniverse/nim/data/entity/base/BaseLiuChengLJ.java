package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.LiuChengLJ;
/*
* 实体类：流程路径
*/
public abstract class BaseLiuChengLJ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengLJ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengLJ (java.lang.Integer id) {
		this.setLiuChengLJDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：流程路径代号 （liuChengLJDH）
	private java.lang.String liuChengLJDH = null;
	public java.lang.String getLiuChengLJDH(){return this.liuChengLJDH ;}
	public void setLiuChengLJDH(java.lang.String liuChengLJDH){this.liuChengLJDH = liuChengLJDH;}
	
	// 主键：liuChengLJDM
	private java.lang.Integer liuChengLJDM = null;
	public java.lang.Integer getLiuChengLJDM(){return this.liuChengLJDM ;}
	public void setLiuChengLJDM(java.lang.Integer liuChengLJDM){this.liuChengLJDM = liuChengLJDM;}

			
	// 属性：功能代号 （gongNengDH）
	private java.lang.String gongNengDH = null;
	public java.lang.String getGongNengDH(){return this.gongNengDH ;}
	public void setGongNengDH(java.lang.String gongNengDH){this.gongNengDH = gongNengDH;}
	
			
	// 属性：对象代码 （gongNengObjId）
	private java.lang.Integer gongNengObjId = new java.lang.Integer(0);
	public java.lang.Integer getGongNengObjId(){return this.gongNengObjId ;}
	public void setGongNengObjId(java.lang.Integer gongNengObjId){this.gongNengObjId = gongNengObjId;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof LiuChengLJ)) return false;
		else {
			LiuChengLJ entity = (LiuChengLJ) obj;
			if (null == this.getLiuChengLJDM() || null == entity.getLiuChengLJDM()) return false;
			else return (this.getLiuChengLJDM().equals(entity.getLiuChengLJDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengLJDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengLJDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengLJDM+"";
	}

	public Integer pkValue() {
		return this.liuChengLJDM;
	}

	public String pkName() {
		return "liuChengLJDM";
	}

	public void pkNull() {
		this.liuChengLJDM = null;;
	}
	
	public int compareTo(Object o) {
		LiuChengLJ obj = (LiuChengLJ)o;
		if(this.getLiuChengLJDM()==null){
			return 1;
		}
		return this.getLiuChengLJDM().compareTo(obj.getLiuChengLJDM());
	}
	
	public LiuChengLJ clone(){
		LiuChengLJ liuChengLJ = new LiuChengLJ();
		
		liuChengLJ.setLiuChengLJDH(liuChengLJDH);
		liuChengLJ.setGongNengDH(gongNengDH);
		liuChengLJ.setGongNengObjId(gongNengObjId);
		
		return liuChengLJ;
	}
	
	
	
	
	
	
}