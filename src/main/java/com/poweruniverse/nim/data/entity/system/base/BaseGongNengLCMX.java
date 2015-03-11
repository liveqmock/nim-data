package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：功能流程明细
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseGongNengLCMX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengLCMX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengLCMX (java.lang.Integer id) {
		this.setGongNengLCMXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：gongNengLCMXDM
	private java.lang.Integer gongNengLCMXDM = null;
	public java.lang.Integer getGongNengLCMXDM(){return this.gongNengLCMXDM ;}
	public void setGongNengLCMXDM(java.lang.Integer gongNengLCMXDM){this.gongNengLCMXDM = gongNengLCMXDM;}

	// 对象：功能流程 （gongNengLC）
	private com.poweruniverse.nim.data.entity.system.GongNengLC gongNengLC;
	public com.poweruniverse.nim.data.entity.system.GongNengLC getGongNengLC(){return this.gongNengLC ;}
	public void setGongNengLC(com.poweruniverse.nim.data.entity.system.GongNengLC gongNengLC){this.gongNengLC = gongNengLC;}

			
	// 属性：流转条件显示 （liuZhuanTJXS）
	private java.lang.String liuZhuanTJXS = null;
	public java.lang.String getLiuZhuanTJXS(){return this.liuZhuanTJXS ;}
	public void setLiuZhuanTJXS(java.lang.String liuZhuanTJXS){this.liuZhuanTJXS = liuZhuanTJXS;}
	
	// 集合：流程公式 （gss）
	private java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLCGS> gss = new java.util.TreeSet<com.poweruniverse.nim.data.entity.system.GongNengLCGS>();
	public java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLCGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLCGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNengLCMX mainObj = (com.poweruniverse.nim.data.entity.system.GongNengLCMX)parent;
		com.poweruniverse.nim.data.entity.system.GongNengLCGS subObj = (com.poweruniverse.nim.data.entity.system.GongNengLCGS)detail;
		subObj.setGongNengLCMX(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNengLCMX mainObj = (com.poweruniverse.nim.data.entity.system.GongNengLCMX)parent;
		com.poweruniverse.nim.data.entity.system.GongNengLCGS subObj = (com.poweruniverse.nim.data.entity.system.GongNengLCGS)detail;
		subObj.setGongNengLCMX(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.system.GongNengLCGS> ds = this.getGss().iterator();
		com.poweruniverse.nim.data.entity.system.GongNengLCGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengLCGSDM()!=null && d.getGongNengLCGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.system.GongNengLCGS newgssByParent(com.poweruniverse.nim.data.entity.system.GongNengLCMX parent) throws Exception{
		com.poweruniverse.nim.data.entity.system.GongNengLCGS subObj = new com.poweruniverse.nim.data.entity.system.GongNengLCGS();
		//
		subObj.setGongNengLCMX(parent);
		//
		return subObj;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.GongNengLCMX)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.GongNengLCMX entity = (com.poweruniverse.nim.data.entity.system.GongNengLCMX) obj;
			if (null == this.getGongNengLCMXDM() || null == entity.getGongNengLCMXDM()) return false;
			else return (this.getGongNengLCMXDM().equals(entity.getGongNengLCMXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengLCMXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengLCMXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuZhuanTJXS+"";
	}

	public Integer pkValue() {
		return this.gongNengLCMXDM;
	}

	public String pkName() {
		return "gongNengLCMXDM";
	}

	public void pkNull() {
		this.gongNengLCMXDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.system.GongNengLCMX obj = (com.poweruniverse.nim.data.entity.system.GongNengLCMX)o;
		if(this.getGongNengLCMXDM()==null){
			return 1;
		}
		return this.getGongNengLCMXDM().compareTo(obj.getGongNengLCMXDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.GongNengLCMX clone(){
		com.poweruniverse.nim.data.entity.system.GongNengLCMX gongNengLCMX = new com.poweruniverse.nim.data.entity.system.GongNengLCMX();
		
		gongNengLCMX.setGongNengLC(gongNengLC);
		gongNengLCMX.setLiuZhuanTJXS(liuZhuanTJXS);
		for(com.poweruniverse.nim.data.entity.system.GongNengLCGS subObj:this.getGss()){
			gongNengLCMX.addTogss(gongNengLCMX, subObj.clone());
		}
		
		return gongNengLCMX;
	}
	
	
	
	
	
	
}