package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：流程检视平级
*/
@Version("2015-03-16 11:16:00")
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
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS pingJiLCJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getPingJiLCJS(){return this.pingJiLCJS ;}
	public void setPingJiLCJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS pingJiLCJS){this.pingJiLCJS = pingJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

	// 主键：liuChengJSPJDM
	private java.lang.Integer liuChengJSPJDM = null;
	public java.lang.Integer getLiuChengJSPJDM(){return this.liuChengJSPJDM ;}
	public void setLiuChengJSPJDM(java.lang.Integer liuChengJSPJDM){this.liuChengJSPJDM = liuChengJSPJDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ entity = (com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ) obj;
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
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ obj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ)o;
		if(this.getLiuChengJSPJDM()==null){
			return 1;
		}
		return this.getLiuChengJSPJDM().compareTo(obj.getLiuChengJSPJDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ clone(){
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ liuChengJSPJ = new com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ();
		
		liuChengJSPJ.setPingJiLCJS(pingJiLCJS);
		liuChengJSPJ.setLiuChengJS(liuChengJS);
		
		return liuChengJSPJ;
	}
	
	
	
	
	
	
}