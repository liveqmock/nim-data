package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能工作流
*/
@Version("2015-04-04 02:05:44")
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
	
	// 集合：条件集合 （tjs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ> tjs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ> getTjs(){return this.tjs ;}
	public void setTjs(java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ> tjs){this.tjs = tjs;}
	public void addTotjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengGZL mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZL)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ subObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)detail;
		subObj.setGongNengGZL(mainObj);
		mainObj.getTjs().add(subObj);
	}
	public void removeFromtjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengGZL mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZL)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ subObj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ)detail;
		subObj.setGongNengGZL(null);
		mainObj.getTjs().remove(subObj);
	}
	public Object gettjsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ> ds = this.getTjs().iterator();
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengGZLTJDM()!=null && d.getGongNengGZLTJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ newtjsByParent(com.poweruniverse.nim.data.entity.sys.GongNengGZL parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ subObj = new com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ();
		//
		subObj.setGongNengGZL(parent);
		//
		return subObj;
	}
	
	// 对象：启动功能操作 （startGNCZ）
	private com.poweruniverse.nim.data.entity.sys.GongNengCZ startGNCZ;
	public com.poweruniverse.nim.data.entity.sys.GongNengCZ getStartGNCZ(){return this.startGNCZ ;}
	public void setStartGNCZ(com.poweruniverse.nim.data.entity.sys.GongNengCZ startGNCZ){this.startGNCZ = startGNCZ;}

			
	// 属性：条件显示 （tiaoJianXS）
	private java.lang.String tiaoJianXS = null;
	public java.lang.String getTiaoJianXS(){return this.tiaoJianXS ;}
	public void setTiaoJianXS(java.lang.String tiaoJianXS){this.tiaoJianXS = tiaoJianXS;}
	
	// 对象：功能 （gongNeng）
	private com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng;
	public com.poweruniverse.nim.data.entity.sys.GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng){this.gongNeng = gongNeng;}

			
	// 属性：功能工作流编号 （gongNengGZLBH）
	private java.lang.String gongNengGZLBH = null;
	public java.lang.String getGongNengGZLBH(){return this.gongNengGZLBH ;}
	public void setGongNengGZLBH(java.lang.String gongNengGZLBH){this.gongNengGZLBH = gongNengGZLBH;}
	
			
	// 属性：功能工作流名称 （gongNengGZLMC）
	private java.lang.String gongNengGZLMC = null;
	public java.lang.String getGongNengGZLMC(){return this.gongNengGZLMC ;}
	public void setGongNengGZLMC(java.lang.String gongNengGZLMC){this.gongNengGZLMC = gongNengGZLMC;}
	
	// 主键：gongNengGZLDM
	private java.lang.Integer gongNengGZLDM = null;
	public java.lang.Integer getGongNengGZLDM(){return this.gongNengGZLDM ;}
	public void setGongNengGZLDM(java.lang.Integer gongNengGZLDM){this.gongNengGZLDM = gongNengGZLDM;}

			
	// 属性：版本 （gongNengGZLBB）
	private java.lang.String gongNengGZLBB = null;
	public java.lang.String getGongNengGZLBB(){return this.gongNengGZLBB ;}
	public void setGongNengGZLBB(java.lang.String gongNengGZLBB){this.gongNengGZLBB = gongNengGZLBB;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengGZL)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengGZL entity = (com.poweruniverse.nim.data.entity.sys.GongNengGZL) obj;
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
		com.poweruniverse.nim.data.entity.sys.GongNengGZL obj = (com.poweruniverse.nim.data.entity.sys.GongNengGZL)o;
		if(this.getGongNengGZLDM()==null){
			return 1;
		}
		return this.getGongNengGZLDM().compareTo(obj.getGongNengGZLDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengGZL clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengGZL gongNengGZL = new com.poweruniverse.nim.data.entity.sys.GongNengGZL();
		
		for(com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ subObj:this.getTjs()){
			gongNengGZL.addTotjs(gongNengGZL, subObj.clone());
		}
		gongNengGZL.setStartGNCZ(startGNCZ);
		gongNengGZL.setTiaoJianXS(tiaoJianXS);
		gongNengGZL.setGongNeng(gongNeng);
		gongNengGZL.setGongNengGZLBH(gongNengGZLBH);
		gongNengGZL.setGongNengGZLMC(gongNengGZLMC);
		gongNengGZL.setGongNengGZLBB(gongNengGZLBB);
		
		return gongNengGZL;
	}
	
	
	
	
	
	
}