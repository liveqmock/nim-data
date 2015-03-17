package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：角色权限功能操作明细
*/
@Version("2015-03-16 11:15:59")
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
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS> gss = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX mainObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS)detail;
		subObj.setJueSeQXMX(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX mainObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS)detail;
		subObj.setJueSeQXMX(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS> ds = this.getGss().iterator();
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXMXGSDM()!=null && d.getJueSeQXMXGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS newgssByParent(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS subObj = new com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS();
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
	private com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ jueSeQXGNCZ;
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ getJueSeQXGNCZ(){return this.jueSeQXGNCZ ;}
	public void setJueSeQXGNCZ(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ jueSeQXGNCZ){this.jueSeQXGNCZ = jueSeQXGNCZ;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX entity = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX) obj;
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
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX obj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX)o;
		if(this.getJueSeQXMXDM()==null){
			return 1;
		}
		return this.getJueSeQXMXDM().compareTo(obj.getJueSeQXMXDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX clone(){
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX jueSeQXGNCZMX = new com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMX();
		
		jueSeQXGNCZMX.setQuanXianSM(quanXianSM);
		for(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZMXGS subObj:this.getGss()){
			jueSeQXGNCZMX.addTogss(jueSeQXGNCZMX, subObj.clone());
		}
		jueSeQXGNCZMX.setJueSeQXGNCZ(jueSeQXGNCZ);
		
		return jueSeQXGNCZMX;
	}
	
	
	
	
	
	
}