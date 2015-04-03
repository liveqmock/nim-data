package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：流程检视下级
*/
@Version("2015-04-04 02:05:45")
public abstract class BaseLiuChengJSXJ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengJSXJ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengJSXJ (java.lang.Integer id) {
		this.setLiuChengJSXJDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 对象：下级流程检视 （xiaJiLCJS）
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS xiaJiLCJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getXiaJiLCJS(){return this.xiaJiLCJS ;}
	public void setXiaJiLCJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS xiaJiLCJS){this.xiaJiLCJS = xiaJiLCJS;}

	// 对象：流程检视 （liuChengJS）
	private com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS;
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS getLiuChengJS(){return this.liuChengJS ;}
	public void setLiuChengJS(com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS){this.liuChengJS = liuChengJS;}

	// 主键：liuChengJSXJDM
	private java.lang.Integer liuChengJSXJDM = null;
	public java.lang.Integer getLiuChengJSXJDM(){return this.liuChengJSXJDM ;}
	public void setLiuChengJSXJDM(java.lang.Integer liuChengJSXJDM){this.liuChengJSXJDM = liuChengJSXJDM;}

			
	// 属性：流程路径 （transitionId）
	private java.lang.String transitionId = null;
	public java.lang.String getTransitionId(){return this.transitionId ;}
	public void setTransitionId(java.lang.String transitionId){this.transitionId = transitionId;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ entity = (com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ) obj;
			if (null == this.getLiuChengJSXJDM() || null == entity.getLiuChengJSXJDM()) return false;
			else return (this.getLiuChengJSXJDM().equals(entity.getLiuChengJSXJDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengJSXJDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengJSXJDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengJSXJDM+"";
	}

	public Integer pkValue() {
		return this.liuChengJSXJDM;
	}

	public String pkName() {
		return "liuChengJSXJDM";
	}

	public void pkNull() {
		this.liuChengJSXJDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ obj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ)o;
		if(this.getLiuChengJSXJDM()==null){
			return 1;
		}
		return this.getLiuChengJSXJDM().compareTo(obj.getLiuChengJSXJDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ clone(){
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ liuChengJSXJ = new com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ();
		
		liuChengJSXJ.setXiaJiLCJS(xiaJiLCJS);
		liuChengJSXJ.setLiuChengJS(liuChengJS);
		liuChengJSXJ.setTransitionId(transitionId);
		
		return liuChengJSXJ;
	}
	
	
	
	
	
	
}