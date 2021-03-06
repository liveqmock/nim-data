package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：流程检视上级
*/
@Version("2015-04-04 02:05:45")
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
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS shangJiLCJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getShangJiLCJS(){return this.shangJiLCJS ;}
	public void setShangJiLCJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS shangJiLCJS){this.shangJiLCJS = shangJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

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
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ entity = (com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ) obj;
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
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ obj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ)o;
		if(this.getLiuChengJSSJDM()==null){
			return 1;
		}
		return this.getLiuChengJSSJDM().compareTo(obj.getLiuChengJSSJDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ clone(){
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ liuChengJSSJ = new com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ();
		
		liuChengJSSJ.setShangJiLCJS(shangJiLCJS);
		liuChengJSSJ.setLiuChengJS(liuChengJS);
		liuChengJSSJ.setTransitionId(transitionId);
		
		return liuChengJSSJ;
	}
	
	
	
	
	
	
}