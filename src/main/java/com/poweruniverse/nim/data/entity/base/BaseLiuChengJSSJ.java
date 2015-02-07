package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.LiuChengJSSJ;
/*
* 实体类：流程检视上级
*/
public abstract class BaseLiuChengJSSJ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengJSSJ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengJSSJ (java.lang.Integer id) {
		this.setLiuChengJSSJDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 对象：上级流程检视 （shangJiLCJS）
	private LiuChengJS shangJiLCJS;
	public LiuChengJS getShangJiLCJS(){return this.shangJiLCJS ;}
	public void setShangJiLCJS(LiuChengJS shangJiLCJS){this.shangJiLCJS = shangJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private LiuChengJS liuChengJS;
	public LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

	// 主键：liuChengJSSJDM
	private java.lang.Integer liuChengJSSJDM = null;
	public java.lang.Integer getLiuChengJSSJDM(){return this.liuChengJSSJDM ;}
	public void setLiuChengJSSJDM(java.lang.Integer liuChengJSSJDM){this.liuChengJSSJDM = liuChengJSSJDM;}

			
	// 属性：流程路径 （transitionId）
	private java.lang.String transitionId = null;
	public java.lang.String getTransitionId(){return this.transitionId ;}
	public void setTransitionId(java.lang.String transitionId){this.transitionId = transitionId;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof LiuChengJSSJ)) return false;
		else {
			LiuChengJSSJ entity = (LiuChengJSSJ) obj;
			if (null == this.getLiuChengJSSJDM() || null == entity.getLiuChengJSSJDM()) return false;
			else return (this.getLiuChengJSSJDM().equals(entity.getLiuChengJSSJDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengJSSJDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengJSSJDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengJSSJDM+"";
	}

	public Integer pkValue() {
		return this.liuChengJSSJDM;
	}

	public String pkName() {
		return "liuChengJSSJDM";
	}

	public void pkNull() {
		this.liuChengJSSJDM = null;;
	}
	
	public int compareTo(Object o) {
		LiuChengJSSJ obj = (LiuChengJSSJ)o;
		if(this.getLiuChengJSSJDM()==null){
			return 1;
		}
		return this.getLiuChengJSSJDM().compareTo(obj.getLiuChengJSSJDM());
	}
	
	public LiuChengJSSJ clone(){
		LiuChengJSSJ liuChengJSSJ = new LiuChengJSSJ();
		
		liuChengJSSJ.setShangJiLCJS(shangJiLCJS);
		liuChengJSSJ.setLiuChengJS(liuChengJS);
		liuChengJSSJ.setTransitionId(transitionId);
		
		return liuChengJSSJ;
	}
	
	
	
	
	
	
}