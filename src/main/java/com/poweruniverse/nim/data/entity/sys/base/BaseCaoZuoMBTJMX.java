package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：操作模板条件明细
*/
@Version("2015-04-04 02:05:43")
public abstract class BaseCaoZuoMBTJMX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseCaoZuoMBTJMX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCaoZuoMBTJMX (java.lang.Integer id) {
		this.setCaoZuoMBTJMXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 集合：公式集合 （gss）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS> gss = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS> getGss(){return this.gss ;}
	public void setGss(java.util.Set<com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS> gss){this.gss = gss;}
	public void addTogss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX mainObj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX)parent;
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS subObj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS)detail;
		subObj.setCaoZuoMBTJMX(mainObj);
		mainObj.getGss().add(subObj);
	}
	public void removeFromgss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX mainObj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX)parent;
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS subObj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS)detail;
		subObj.setCaoZuoMBTJMX(null);
		mainObj.getGss().remove(subObj);
	}
	public Object getgssById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS> ds = this.getGss().iterator();
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getCaoZuoMBTJGSDM()!=null && d.getCaoZuoMBTJGSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS newgssByParent(com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS subObj = new com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS();
		//
		subObj.setCaoZuoMBTJMX(parent);
		//
		return subObj;
	}
	
	// 主键：caoZuoMBTJMXDM
	private java.lang.Integer caoZuoMBTJMXDM = null;
	public java.lang.Integer getCaoZuoMBTJMXDM(){return this.caoZuoMBTJMXDM ;}
	public void setCaoZuoMBTJMXDM(java.lang.Integer caoZuoMBTJMXDM){this.caoZuoMBTJMXDM = caoZuoMBTJMXDM;}

	// 对象：操作模板 （caoZuoMB）
	private com.poweruniverse.nim.data.entity.sys.CaoZuoMB caoZuoMB;
	public com.poweruniverse.nim.data.entity.sys.CaoZuoMB getCaoZuoMB(){return this.caoZuoMB ;}
	public void setCaoZuoMB(com.poweruniverse.nim.data.entity.sys.CaoZuoMB caoZuoMB){this.caoZuoMB = caoZuoMB;}

			
	// 属性：条件显示 （liuZhuanTJXS）
	private java.lang.String liuZhuanTJXS = null;
	public java.lang.String getLiuZhuanTJXS(){return this.liuZhuanTJXS ;}
	public void setLiuZhuanTJXS(java.lang.String liuZhuanTJXS){this.liuZhuanTJXS = liuZhuanTJXS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX entity = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX) obj;
			if (null == this.getCaoZuoMBTJMXDM() || null == entity.getCaoZuoMBTJMXDM()) return false;
			else return (this.getCaoZuoMBTJMXDM().equals(entity.getCaoZuoMBTJMXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCaoZuoMBTJMXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCaoZuoMBTJMXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoMBTJMXDM+"";
	}

	public Integer pkValue() {
		return this.caoZuoMBTJMXDM;
	}

	public String pkName() {
		return "caoZuoMBTJMXDM";
	}

	public void pkNull() {
		this.caoZuoMBTJMXDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX obj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX)o;
		if(this.getCaoZuoMBTJMXDM()==null){
			return 1;
		}
		return this.getCaoZuoMBTJMXDM().compareTo(obj.getCaoZuoMBTJMXDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX clone(){
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX caoZuoMBTJMX = new com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX();
		
		for(com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS subObj:this.getGss()){
			caoZuoMBTJMX.addTogss(caoZuoMBTJMX, subObj.clone());
		}
		caoZuoMBTJMX.setCaoZuoMB(caoZuoMB);
		caoZuoMBTJMX.setLiuZhuanTJXS(liuZhuanTJXS);
		
		return caoZuoMBTJMX;
	}
	
	
	
	
	
	
}