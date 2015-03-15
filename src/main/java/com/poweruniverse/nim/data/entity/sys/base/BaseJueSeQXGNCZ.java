package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：角色权限功能操作
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseJueSeQXGNCZ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseJueSeQXGNCZ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJueSeQXGNCZ (java.lang.Integer id) {
		this.setJueSeQXGNCZDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：jueSeQXGNCZDM
	private java.lang.Integer jueSeQXGNCZDM = null;
	public java.lang.Integer getJueSeQXGNCZDM(){return this.jueSeQXGNCZDM ;}
	public void setJueSeQXGNCZDM(java.lang.Integer jueSeQXGNCZDM){this.jueSeQXGNCZDM = jueSeQXGNCZDM;}

	// 对象：角色 （jueSe）
	private com.poweruniverse.nim.data.entity.sys.JueSe jueSe;
	public com.poweruniverse.nim.data.entity.sys.JueSe getJueSe(){return this.jueSe ;}
	public void setJueSe(com.poweruniverse.nim.data.entity.sys.JueSe jueSe){this.jueSe = jueSe;}

	// 对象：功能操作 （gongNengCZ）
	private com.poweruniverse.nim.data.entity.sys.GongNengCZ gongNengCZ;
	public com.poweruniverse.nim.data.entity.sys.GongNengCZ getGongNengCZ(){return this.gongNengCZ ;}
	public void setGongNengCZ(com.poweruniverse.nim.data.entity.sys.GongNengCZ gongNengCZ){this.gongNengCZ = gongNengCZ;}

	// 集合：权限明细 （mxs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX> mxs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ mainObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)detail;
		subObj.setJueSeQXGNCZ(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ mainObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)detail;
		subObj.setJueSeQXGNCZ(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX> ds = this.getMxs().iterator();
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXMXDM()!=null && d.getJueSeQXMXDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX newmxsByParent(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX subObj = new com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX();
		//
		subObj.setJueSeQXGNCZ(parent);
		//
		return subObj;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ entity = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ) obj;
			if (null == this.getJueSeQXGNCZDM() || null == entity.getJueSeQXGNCZDM()) return false;
			else return (this.getJueSeQXGNCZDM().equals(entity.getJueSeQXGNCZDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getJueSeQXGNCZDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getJueSeQXGNCZDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.jueSeQXGNCZDM+"";
	}

	public Integer pkValue() {
		return this.jueSeQXGNCZDM;
	}

	public String pkName() {
		return "jueSeQXGNCZDM";
	}

	public void pkNull() {
		this.jueSeQXGNCZDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ obj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)o;
		if(this.getJueSeQXGNCZDM()==null){
			return 1;
		}
		return this.getJueSeQXGNCZDM().compareTo(obj.getJueSeQXGNCZDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ clone(){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ jueSeQXGNCZ = new com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ();
		
		jueSeQXGNCZ.setJueSe(jueSe);
		jueSeQXGNCZ.setGongNengCZ(gongNengCZ);
		for(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX subObj:this.getMxs()){
			jueSeQXGNCZ.addTomxs(jueSeQXGNCZ, subObj.clone());
		}
		
		return jueSeQXGNCZ;
	}
	
	
	
	
	
	
}