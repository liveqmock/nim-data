package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.JueSe;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZ;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMX;
/*
* 实体类：角色权限功能操作
*/
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
	private JueSe jueSe;
	public JueSe getJueSe(){return this.jueSe ;}
	public void setJueSe(JueSe jueSe){this.jueSe = jueSe;}

	// 对象：功能操作 （gongNengCZ）
	private GongNengCZ gongNengCZ;
	public GongNengCZ getGongNengCZ(){return this.gongNengCZ ;}
	public void setGongNengCZ(GongNengCZ gongNengCZ){this.gongNengCZ = gongNengCZ;}

	// 集合：权限明细 （mxs）
	private java.util.Set<JueSeQXGNCZMX> mxs = new java.util.TreeSet<JueSeQXGNCZMX>();
	public java.util.Set<JueSeQXGNCZMX> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<JueSeQXGNCZMX> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		JueSeQXGNCZ mainObj = (JueSeQXGNCZ)parent;
		JueSeQXGNCZMX subObj = (JueSeQXGNCZMX)detail;
		subObj.setJueSeQXGNCZ(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		JueSeQXGNCZ mainObj = (JueSeQXGNCZ)parent;
		JueSeQXGNCZMX subObj = (JueSeQXGNCZMX)detail;
		subObj.setJueSeQXGNCZ(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<JueSeQXGNCZMX> ds = this.getMxs().iterator();
		JueSeQXGNCZMX d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXMXDM()!=null && d.getJueSeQXMXDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public JueSeQXGNCZMX newmxsByParent(JueSeQXGNCZ parent) throws Exception{
		JueSeQXGNCZMX subObj = new JueSeQXGNCZMX();
		//
		subObj.setJueSeQXGNCZ(parent);
		//
		return subObj;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof JueSeQXGNCZ)) return false;
		else {
			JueSeQXGNCZ entity = (JueSeQXGNCZ) obj;
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
		JueSeQXGNCZ obj = (JueSeQXGNCZ)o;
		if(this.getJueSeQXGNCZDM()==null){
			return 1;
		}
		return this.getJueSeQXGNCZDM().compareTo(obj.getJueSeQXGNCZDM());
	}
	
	public JueSeQXGNCZ clone(){
		JueSeQXGNCZ jueSeQXGNCZ = new JueSeQXGNCZ();
		
		jueSeQXGNCZ.setJueSe(jueSe);
		jueSeQXGNCZ.setGongNengCZ(gongNengCZ);
		for(JueSeQXGNCZMX subObj:this.getMxs()){
			jueSeQXGNCZ.addTomxs(jueSeQXGNCZ, subObj.clone());
		}
		return jueSeQXGNCZ;
	}
	
	
	
	
	
	
}