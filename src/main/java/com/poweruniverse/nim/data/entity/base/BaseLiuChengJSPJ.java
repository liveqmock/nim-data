package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.LiuChengJSPJ;
/*
* 实体类：流程检视平级
*/
public abstract class BaseLiuChengJSPJ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengJSPJ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengJSPJ (java.lang.Integer id) {
		this.setLiuChengJSPJDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 对象：平级流程检视 （pingJiLCJS）
	private LiuChengJS pingJiLCJS;
	public LiuChengJS getPingJiLCJS(){return this.pingJiLCJS ;}
	public void setPingJiLCJS(LiuChengJS pingJiLCJS){this.pingJiLCJS = pingJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private LiuChengJS liuChengJS;
	public LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

	// 主键：liuChengJSPJDM
	private java.lang.Integer liuChengJSPJDM = null;
	public java.lang.Integer getLiuChengJSPJDM(){return this.liuChengJSPJDM ;}
	public void setLiuChengJSPJDM(java.lang.Integer liuChengJSPJDM){this.liuChengJSPJDM = liuChengJSPJDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof LiuChengJSPJ)) return false;
		else {
			LiuChengJSPJ entity = (LiuChengJSPJ) obj;
			if (null == this.getLiuChengJSPJDM() || null == entity.getLiuChengJSPJDM()) return false;
			else return (this.getLiuChengJSPJDM().equals(entity.getLiuChengJSPJDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengJSPJDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengJSPJDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengJSPJDM+"";
	}

	public Integer pkValue() {
		return this.liuChengJSPJDM;
	}

	public String pkName() {
		return "liuChengJSPJDM";
	}

	public void pkNull() {
		this.liuChengJSPJDM = null;;
	}
	
	public int compareTo(Object o) {
		LiuChengJSPJ obj = (LiuChengJSPJ)o;
		if(this.getLiuChengJSPJDM()==null){
			return 1;
		}
		return this.getLiuChengJSPJDM().compareTo(obj.getLiuChengJSPJDM());
	}
	
	public LiuChengJSPJ clone(){
		LiuChengJSPJ liuChengJSPJ = new LiuChengJSPJ();
		
		liuChengJSPJ.setPingJiLCJS(pingJiLCJS);
		liuChengJSPJ.setLiuChengJS(liuChengJS);
		
		return liuChengJSPJ;
	}
	
	
	
	
	
	
}