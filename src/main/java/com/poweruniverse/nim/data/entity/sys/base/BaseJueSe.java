package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：角色
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseJueSe  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseJueSe () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJueSe (java.lang.Integer id) {
		this.setJueSeDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 集合：角色权限功能操作 （qxs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ> qxs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ> getQxs(){return this.qxs ;}
	public void setQxs(java.util.Set<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ> qxs){this.qxs = qxs;}
	public void addToqxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSe mainObj = (com.poweruniverse.nim.data.entity.sys.JueSe)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)detail;
		subObj.setJueSe(mainObj);
		mainObj.getQxs().add(subObj);
	}
	public void removeFromqxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.JueSe mainObj = (com.poweruniverse.nim.data.entity.sys.JueSe)parent;
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ subObj = (com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ)detail;
		subObj.setJueSe(null);
		mainObj.getQxs().remove(subObj);
	}
	public Object getqxsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ> ds = this.getQxs().iterator();
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXGNCZDM()!=null && d.getJueSeQXGNCZDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ newqxsByParent(com.poweruniverse.nim.data.entity.sys.JueSe parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ subObj = new com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ();
		//
		subObj.setJueSe(parent);
		//
		return subObj;
	}
	
	// 主键：jueSeDM
	private java.lang.Integer jueSeDM = null;
	public java.lang.Integer getJueSeDM(){return this.jueSeDM ;}
	public void setJueSeDM(java.lang.Integer jueSeDM){this.jueSeDM = jueSeDM;}

			
	// 属性：角色名称 （jueSeMC）
	private java.lang.String jueSeMC = null;
	public java.lang.String getJueSeMC(){return this.jueSeMC ;}
	public void setJueSeMC(java.lang.String jueSeMC){this.jueSeMC = jueSeMC;}
	
	// 对象：系统 （xiTong）
	private com.poweruniverse.nim.data.entity.sys.XiTong xiTong;
	public com.poweruniverse.nim.data.entity.sys.XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(com.poweruniverse.nim.data.entity.sys.XiTong xiTong){this.xiTong = xiTong;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.JueSe)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.JueSe entity = (com.poweruniverse.nim.data.entity.sys.JueSe) obj;
			if (null == this.getJueSeDM() || null == entity.getJueSeDM()) return false;
			else return (this.getJueSeDM().equals(entity.getJueSeDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getJueSeDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getJueSeDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.jueSeMC+"";
	}

	public Integer pkValue() {
		return this.jueSeDM;
	}

	public String pkName() {
		return "jueSeDM";
	}

	public void pkNull() {
		this.jueSeDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.JueSe obj = (com.poweruniverse.nim.data.entity.sys.JueSe)o;
		if(this.getJueSeDM()==null){
			return 1;
		}
		return this.getJueSeDM().compareTo(obj.getJueSeDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.JueSe clone(){
		com.poweruniverse.nim.data.entity.sys.JueSe jueSe = new com.poweruniverse.nim.data.entity.sys.JueSe();
		
		for(com.poweruniverse.nim.data.entity.sys.JueSeQXGNCZ subObj:this.getQxs()){
			jueSe.addToqxs(jueSe, subObj.clone());
		}
		jueSe.setJueSeMC(jueSeMC);
		jueSe.setXiTong(xiTong);
		
		return jueSe;
	}
	
	
	
	
	
	
}