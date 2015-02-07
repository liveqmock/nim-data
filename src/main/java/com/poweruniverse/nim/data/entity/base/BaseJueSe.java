package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.JueSe;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZ;
import com.poweruniverse.nim.data.entity.XiTong;
/*
* 实体类：角色
*/
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
	private java.util.Set<JueSeQXGNCZ> qxs = new java.util.TreeSet<JueSeQXGNCZ>();
	public java.util.Set<JueSeQXGNCZ> getQxs(){return this.qxs ;}
	public void setQxs(java.util.Set<JueSeQXGNCZ> qxs){this.qxs = qxs;}
	public void addToqxs(Object parent,Object detail){
		JueSe mainObj = (JueSe)parent;
		JueSeQXGNCZ subObj = (JueSeQXGNCZ)detail;
		subObj.setJueSe(mainObj);
		mainObj.getQxs().add(subObj);
	}
	public void removeFromqxs(Object parent,Object detail){
		JueSe mainObj = (JueSe)parent;
		JueSeQXGNCZ subObj = (JueSeQXGNCZ)detail;
		subObj.setJueSe(null);
		mainObj.getQxs().remove(subObj);
	}
	public Object getqxsById(Object id){
		java.util.Iterator<JueSeQXGNCZ> ds = this.getQxs().iterator();
		JueSeQXGNCZ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getJueSeQXGNCZDM()!=null && d.getJueSeQXGNCZDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public JueSeQXGNCZ newqxsByParent(JueSe parent) throws Exception{
		JueSeQXGNCZ subObj = new JueSeQXGNCZ();
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
	private XiTong xiTong;
	public XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(XiTong xiTong){this.xiTong = xiTong;}

			
	// 属性：角色描述 （jueSeMS）
	private java.lang.String jueSeMS = null;
	public java.lang.String getJueSeMS(){return this.jueSeMS ;}
	public void setJueSeMS(java.lang.String jueSeMS){this.jueSeMS = jueSeMS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof JueSe)) return false;
		else {
			JueSe entity = (JueSe) obj;
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
		JueSe obj = (JueSe)o;
		if(this.getJueSeDM()==null){
			return 1;
		}
		return this.getJueSeDM().compareTo(obj.getJueSeDM());
	}
	
	public JueSe clone(){
		JueSe jueSe = new JueSe();
		
		for(JueSeQXGNCZ subObj:this.getQxs()){
			jueSe.addToqxs(jueSe, subObj.clone());
		}
		jueSe.setJueSeMC(jueSeMC);
		jueSe.setXiTong(xiTong);
		jueSe.setJueSeMS(jueSeMS);
		
		return jueSe;
	}
	
	
	
	
	
	
}