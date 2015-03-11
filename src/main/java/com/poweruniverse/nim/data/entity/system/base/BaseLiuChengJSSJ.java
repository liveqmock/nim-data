package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：流程检视上级
*/
@Version("2015-03-08 11:16:00")
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
	private com.poweruniverse.nim.data.entity.system.LiuChengJS shangJiLCJS;
	public com.poweruniverse.nim.data.entity.system.LiuChengJS getShangJiLCJS(){return this.shangJiLCJS ;}
	public void setShangJiLCJS(com.poweruniverse.nim.data.entity.system.LiuChengJS shangJiLCJS){this.shangJiLCJS = shangJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private com.poweruniverse.nim.data.entity.system.LiuChengJS liuChengJS;
	public com.poweruniverse.nim.data.entity.system.LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(com.poweruniverse.nim.data.entity.system.LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

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
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.LiuChengJSSJ)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.LiuChengJSSJ entity = (com.poweruniverse.nim.data.entity.system.LiuChengJSSJ) obj;
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
		com.poweruniverse.nim.data.entity.system.LiuChengJSSJ obj = (com.poweruniverse.nim.data.entity.system.LiuChengJSSJ)o;
		if(this.getLiuChengJSSJDM()==null){
			return 1;
		}
		return this.getLiuChengJSSJDM().compareTo(obj.getLiuChengJSSJDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.LiuChengJSSJ clone(){
		com.poweruniverse.nim.data.entity.system.LiuChengJSSJ liuChengJSSJ = new com.poweruniverse.nim.data.entity.system.LiuChengJSSJ();
		
		liuChengJSSJ.setShangJiLCJS(shangJiLCJS);
		liuChengJSSJ.setLiuChengJS(liuChengJS);
		liuChengJSSJ.setTransitionId(transitionId);
		
		return liuChengJSSJ;
	}
	
	
	
	
	
	
}