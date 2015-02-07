package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.GongNengGZL;
import com.poweruniverse.nim.data.entity.GongNengGZLTJ;
/*
* 实体类：功能工作流
*/
public abstract class BaseGongNengGZL  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengGZL () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengGZL (java.lang.Integer id) {
		this.setGongNengGZLDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 对象：启动功能操作 （startGNCZ）
	private GongNengCZ startGNCZ;
	public GongNengCZ getStartGNCZ(){return this.startGNCZ ;}
	public void setStartGNCZ(GongNengCZ startGNCZ){this.startGNCZ = startGNCZ;}

			
	// 属性：条件显示 （tiaoJianXS）
	private java.lang.String tiaoJianXS = null;
	public java.lang.String getTiaoJianXS(){return this.tiaoJianXS ;}
	public void setTiaoJianXS(java.lang.String tiaoJianXS){this.tiaoJianXS = tiaoJianXS;}
	
	// 集合：明细集合 （mxs）
	private java.util.Set<GongNengGZLTJ> mxs = new java.util.TreeSet<GongNengGZLTJ>();
	public java.util.Set<GongNengGZLTJ> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<GongNengGZLTJ> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		GongNengGZL mainObj = (GongNengGZL)parent;
		GongNengGZLTJ subObj = (GongNengGZLTJ)detail;
		subObj.setGongNengGZL(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		GongNengGZL mainObj = (GongNengGZL)parent;
		GongNengGZLTJ subObj = (GongNengGZLTJ)detail;
		subObj.setGongNengGZL(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<GongNengGZLTJ> ds = this.getMxs().iterator();
		GongNengGZLTJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengGZLTJDM()!=null && d.getGongNengGZLTJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public GongNengGZLTJ newmxsByParent(GongNengGZL parent) throws Exception{
		GongNengGZLTJ subObj = new GongNengGZLTJ();
		//
		subObj.setGongNengGZL(parent);
		//
		return subObj;
	}
	
			
	// 属性：功能工作流编号 （gongNengGZLBH）
	private java.lang.String gongNengGZLBH = null;
	public java.lang.String getGongNengGZLBH(){return this.gongNengGZLBH ;}
	public void setGongNengGZLBH(java.lang.String gongNengGZLBH){this.gongNengGZLBH = gongNengGZLBH;}
	
	// 对象：功能 （gongNeng）
	private GongNeng gongNeng;
	public GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(GongNeng gongNeng){this.gongNeng = gongNeng;}

			
	// 属性：功能工作流名称 （gongNengGZLMC）
	private java.lang.String gongNengGZLMC = null;
	public java.lang.String getGongNengGZLMC(){return this.gongNengGZLMC ;}
	public void setGongNengGZLMC(java.lang.String gongNengGZLMC){this.gongNengGZLMC = gongNengGZLMC;}
	
	// 主键：gongNengGZLDM
	private java.lang.Integer gongNengGZLDM = null;
	public java.lang.Integer getGongNengGZLDM(){return this.gongNengGZLDM ;}
	public void setGongNengGZLDM(java.lang.Integer gongNengGZLDM){this.gongNengGZLDM = gongNengGZLDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNengGZL)) return false;
		else {
			GongNengGZL entity = (GongNengGZL) obj;
			if (null == this.getGongNengGZLDM() || null == entity.getGongNengGZLDM()) return false;
			else return (this.getGongNengGZLDM().equals(entity.getGongNengGZLDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengGZLDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengGZLDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengGZLMC+"";
	}

	public Integer pkValue() {
		return this.gongNengGZLDM;
	}

	public String pkName() {
		return "gongNengGZLDM";
	}

	public void pkNull() {
		this.gongNengGZLDM = null;;
	}
	
	public int compareTo(Object o) {
		GongNengGZL obj = (GongNengGZL)o;
		if(this.getGongNengGZLDM()==null){
			return 1;
		}
		return this.getGongNengGZLDM().compareTo(obj.getGongNengGZLDM());
	}
	
	public GongNengGZL clone(){
		GongNengGZL gongNengGZL = new GongNengGZL();
		
		gongNengGZL.setStartGNCZ(startGNCZ);
		gongNengGZL.setTiaoJianXS(tiaoJianXS);
		for(GongNengGZLTJ subObj:this.getMxs()){
			gongNengGZL.addTomxs(gongNengGZL, subObj.clone());
		}
		gongNengGZL.setGongNengGZLBH(gongNengGZLBH);
		gongNengGZL.setGongNeng(gongNeng);
		gongNengGZL.setGongNengGZLMC(gongNengGZLMC);
		
		return gongNengGZL;
	}
	
	
	
	
	
	
}