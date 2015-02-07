package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.JueSeQXGNCZ;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMX;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMXGS;
/*
* 实体类：角色权限功能操作明细
*/
public abstract class BaseJueSeQXGNCZMX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseJueSeQXGNCZMX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJueSeQXGNCZMX (java.lang.Integer id) {
		this.setJueSeQXMXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：权限说明 （quanXianSM）
	private java.lang.String quanXianSM = null;
	public java.lang.String getQuanXianSM(){return this.quanXianSM ;}
	public void setQuanXianSM(java.lang.String quanXianSM){this.quanXianSM = quanXianSM;}
	
	// 集合：公式集合 （gss）
	private java.util.Set<JueSeQXGNCZMXGS> gss = new java.util.TreeSet<JueSeQXGNCZMXGS>();
	public java.util.Set<JueSeQXGNCZMXGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<JueSeQXGNCZMXGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		JueSeQXGNCZMX mainObj = (JueSeQXGNCZMX)parent;
		JueSeQXGNCZMXGS subObj = (JueSeQXGNCZMXGS)detail;
		subObj.setJueSeQXMX(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		JueSeQXGNCZMX mainObj = (JueSeQXGNCZMX)parent;
		JueSeQXGNCZMXGS subObj = (JueSeQXGNCZMXGS)detail;
		subObj.setJueSeQXMX(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<JueSeQXGNCZMXGS> ds = this.getGss().iterator();
		JueSeQXGNCZMXGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXMXGSDM()!=null && d.getJueSeQXMXGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public JueSeQXGNCZMXGS newgssByParent(JueSeQXGNCZMX parent) throws Exception{
		JueSeQXGNCZMXGS subObj = new JueSeQXGNCZMXGS();
		//
		subObj.setJueSeQXMX(parent);
		//
		return subObj;
	}
	
	// 主键：jueSeQXMXDM
	private java.lang.Integer jueSeQXMXDM = null;
	public java.lang.Integer getJueSeQXMXDM(){return this.jueSeQXMXDM ;}
	public void setJueSeQXMXDM(java.lang.Integer jueSeQXMXDM){this.jueSeQXMXDM = jueSeQXMXDM;}

	// 对象：角色权限功能操作 （jueSeQXGNCZ）
	private JueSeQXGNCZ jueSeQXGNCZ;
	public JueSeQXGNCZ getJueSeQXGNCZ(){return this.jueSeQXGNCZ ;}
	public void setJueSeQXGNCZ(JueSeQXGNCZ jueSeQXGNCZ){this.jueSeQXGNCZ = jueSeQXGNCZ;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof JueSeQXGNCZMX)) return false;
		else {
			JueSeQXGNCZMX entity = (JueSeQXGNCZMX) obj;
			if (null == this.getJueSeQXMXDM() || null == entity.getJueSeQXMXDM()) return false;
			else return (this.getJueSeQXMXDM().equals(entity.getJueSeQXMXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getJueSeQXMXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getJueSeQXMXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.jueSeQXMXDM+"";
	}

	public Integer pkValue() {
		return this.jueSeQXMXDM;
	}

	public String pkName() {
		return "jueSeQXMXDM";
	}

	public void pkNull() {
		this.jueSeQXMXDM = null;;
	}
	
	public int compareTo(Object o) {
		JueSeQXGNCZMX obj = (JueSeQXGNCZMX)o;
		if(this.getJueSeQXMXDM()==null){
			return 1;
		}
		return this.getJueSeQXMXDM().compareTo(obj.getJueSeQXMXDM());
	}
	
	public JueSeQXGNCZMX clone(){
		JueSeQXGNCZMX jueSeQXGNCZMX = new JueSeQXGNCZMX();
		
		jueSeQXGNCZMX.setQuanXianSM(quanXianSM);
		for(JueSeQXGNCZMXGS subObj:this.getGss()){
			jueSeQXGNCZMX.addTogss(jueSeQXGNCZMX, subObj.clone());
		}
		jueSeQXGNCZMX.setJueSeQXGNCZ(jueSeQXGNCZ);
		
		return jueSeQXGNCZMX;
	}
	
	
	
	
	
	
}