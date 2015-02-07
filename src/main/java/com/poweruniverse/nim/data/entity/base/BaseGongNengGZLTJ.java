package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNengGZL;
import com.poweruniverse.nim.data.entity.GongNengGZLTJ;
import com.poweruniverse.nim.data.entity.GongNengGZLTJGS;
/*
* 实体类：功能工作流条件
*/
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
	
			
	// 属性：条件显示 （liuZhuanTJXS）
	private java.lang.String liuZhuanTJXS = null;
	public java.lang.String getLiuZhuanTJXS(){return this.liuZhuanTJXS ;}
	public void setLiuZhuanTJXS(java.lang.String liuZhuanTJXS){this.liuZhuanTJXS = liuZhuanTJXS;}
	
	// 集合：公式集合 （gss）
	private java.util.Set<GongNengGZLTJGS> gss = new java.util.TreeSet<GongNengGZLTJGS>();
	public java.util.Set<GongNengGZLTJGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<GongNengGZLTJGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		GongNengGZLTJ mainObj = (GongNengGZLTJ)parent;
		GongNengGZLTJGS subObj = (GongNengGZLTJGS)detail;
		subObj.setGongNengGZLTJ(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		GongNengGZLTJ mainObj = (GongNengGZLTJ)parent;
		GongNengGZLTJGS subObj = (GongNengGZLTJGS)detail;
		subObj.setGongNengGZLTJ(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<GongNengGZLTJGS> ds = this.getGss().iterator();
		GongNengGZLTJGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengGZLTJGSDM()!=null && d.getGongNengGZLTJGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public GongNengGZLTJGS newgssByParent(GongNengGZLTJ parent) throws Exception{
		GongNengGZLTJGS subObj = new GongNengGZLTJGS();
		//
		subObj.setGongNengGZLTJ(parent);
		//
		return subObj;
	}
	
	// 对象：功能工作流 （gongNengGZL）
	private GongNengGZL gongNengGZL;
	public GongNengGZL getGongNengGZL(){return this.gongNengGZL ;}
	public void setGongNengGZL(GongNengGZL gongNengGZL){this.gongNengGZL = gongNengGZL;}

	// 主键：gongNengGZLTJDM
	private java.lang.Integer gongNengGZLTJDM = null;
	public java.lang.Integer getGongNengGZLTJDM(){return this.gongNengGZLTJDM ;}
	public void setGongNengGZLTJDM(java.lang.Integer gongNengGZLTJDM){this.gongNengGZLTJDM = gongNengGZLTJDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNengGZLTJ)) return false;
		else {
			GongNengGZLTJ entity = (GongNengGZLTJ) obj;
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
		return this.liuZhuanTJXS+"";
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
		GongNengGZLTJ obj = (GongNengGZLTJ)o;
		if(this.getGongNengGZLTJDM()==null){
			return 1;
		}
		return this.getGongNengGZLTJDM().compareTo(obj.getGongNengGZLTJDM());
	}
	
	public GongNengGZLTJ clone(){
		GongNengGZLTJ gongNengGZLTJ = new GongNengGZLTJ();
		
		gongNengGZLTJ.setLiuZhuanTJXS(liuZhuanTJXS);
		for(GongNengGZLTJGS subObj:this.getGss()){
			gongNengGZLTJ.addTogss(gongNengGZLTJ, subObj.clone());
		}
		gongNengGZLTJ.setGongNengGZL(gongNengGZL);
		
		return gongNengGZLTJ;
	}
	
	
	
	
	
	
}