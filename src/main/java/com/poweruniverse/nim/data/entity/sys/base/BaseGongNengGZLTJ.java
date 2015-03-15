package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：功能工作流条件
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseGongNengGZLTJ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengGZLTJ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengGZLTJ (java.lang.Integer id) {
		this.setGongNengGZLTJDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 集合：公式集合 （gss）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS> gss = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS subObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS)detail;
		subObj.setGongNengGZLTJ(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS subObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS)detail;
		subObj.setGongNengGZLTJ(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS> ds = this.getGss().iterator();
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengGZLTJGSDM()!=null && d.getGongNengGZLTJGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS newgssByParent(com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS subObj = new com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS();
		//
		subObj.setGongNengGZLTJ(parent);
		//
		return subObj;
	}
	
			
	// 属性：条件显示 （liuZhuanTJXS）
	private java.lang.String liuZhuanTJXS = null;
	public java.lang.String getLiuZhuanTJXS(){return this.liuZhuanTJXS ;}
	public void setLiuZhuanTJXS(java.lang.String liuZhuanTJXS){this.liuZhuanTJXS = liuZhuanTJXS;}
	
	// 对象：功能工作流 （gongNengGZL）
	private com.poweruniverse.nim.data.entity.sys.GongNengGZL gongNengGZL;
	public com.poweruniverse.nim.data.entity.sys.GongNengGZL getGongNengGZL(){return this.gongNengGZL ;}
	public void setGongNengGZL(com.poweruniverse.nim.data.entity.sys.GongNengGZL gongNengGZL){this.gongNengGZL = gongNengGZL;}

	// 主键：gongNengGZLTJDM
	private java.lang.Integer gongNengGZLTJDM = null;
	public java.lang.Integer getGongNengGZLTJDM(){return this.gongNengGZLTJDM ;}
	public void setGongNengGZLTJDM(java.lang.Integer gongNengGZLTJDM){this.gongNengGZLTJDM = gongNengGZLTJDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ entity = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ) obj;
			if (null == this.getGongNengGZLTJDM() || null == entity.getGongNengGZLTJDM()) return false;
			else return (this.getGongNengGZLTJDM().equals(entity.getGongNengGZLTJDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengGZLTJDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengGZLTJDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengGZLTJDM+"";
	}

	public Integer pkValue() {
		return this.gongNengGZLTJDM;
	}

	public String pkName() {
		return "gongNengGZLTJDM";
	}

	public void pkNull() {
		this.gongNengGZLTJDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ obj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)o;
		if(this.getGongNengGZLTJDM()==null){
			return 1;
		}
		return this.getGongNengGZLTJDM().compareTo(obj.getGongNengGZLTJDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ gongNengGZLTJ = new com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ();
		
		for(com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS subObj:this.getGss()){
			gongNengGZLTJ.addTogss(gongNengGZLTJ, subObj.clone());
		}
		gongNengGZLTJ.setLiuZhuanTJXS(liuZhuanTJXS);
		gongNengGZLTJ.setGongNengGZL(gongNengGZL);
		
		return gongNengGZLTJ;
	}
	
	
	
	
	
	
}