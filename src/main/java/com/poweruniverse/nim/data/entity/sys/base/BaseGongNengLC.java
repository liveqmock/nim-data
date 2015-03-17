package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能流程
*/
@Version("2015-03-16 11:15:59")
public abstract class BaseGongNengLC  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengLC () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengLC (java.lang.Integer id) {
		this.setGongNengLCDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 集合：流程明细集合 （mxs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengLCMX> mxs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.GongNengLCMX>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengLCMX> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengLCMX> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengLC mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengLC)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengLCMX subObj = (com.poweruniverse.nim.data.entity.sys.GongNengLCMX)detail;
		subObj.setGongNengLC(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNengLC mainObj = (com.poweruniverse.nim.data.entity.sys.GongNengLC)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengLCMX subObj = (com.poweruniverse.nim.data.entity.sys.GongNengLCMX)detail;
		subObj.setGongNengLC(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.GongNengLCMX> ds = this.getMxs().iterator();
		com.poweruniverse.nim.data.entity.sys.GongNengLCMX d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengLCMXDM()!=null && d.getGongNengLCMXDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.GongNengLCMX newmxsByParent(com.poweruniverse.nim.data.entity.sys.GongNengLC parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.GongNengLCMX subObj = new com.poweruniverse.nim.data.entity.sys.GongNengLCMX();
		//
		subObj.setGongNengLC(parent);
		//
		return subObj;
	}
	
	// 主键：gongNengLCDM
	private java.lang.Integer gongNengLCDM = null;
	public java.lang.Integer getGongNengLCDM(){return this.gongNengLCDM ;}
	public void setGongNengLCDM(java.lang.Integer gongNengLCDM){this.gongNengLCDM = gongNengLCDM;}

			
	// 属性：功能流程名称 （gongNengLCMC）
	private java.lang.String gongNengLCMC = null;
	public java.lang.String getGongNengLCMC(){return this.gongNengLCMC ;}
	public void setGongNengLCMC(java.lang.String gongNengLCMC){this.gongNengLCMC = gongNengLCMC;}
	
	// 对象：功能 （gongNeng）
	private com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng;
	public com.poweruniverse.nim.data.entity.sys.GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng){this.gongNeng = gongNeng;}

			
	// 属性：是否启用 （shiFouQY）
	private java.lang.Boolean shiFouQY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouQY(){return this.shiFouQY ;}
	public void setShiFouQY(java.lang.Boolean shiFouQY){this.shiFouQY = shiFouQY;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengLC)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengLC entity = (com.poweruniverse.nim.data.entity.sys.GongNengLC) obj;
			if (null == this.getGongNengLCDM() || null == entity.getGongNengLCDM()) return false;
			else return (this.getGongNengLCDM().equals(entity.getGongNengLCDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengLCDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengLCDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengLCMC+"";
	}

	public Integer pkValue() {
		return this.gongNengLCDM;
	}

	public String pkName() {
		return "gongNengLCDM";
	}

	public void pkNull() {
		this.gongNengLCDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.GongNengLC obj = (com.poweruniverse.nim.data.entity.sys.GongNengLC)o;
		if(this.getGongNengLCDM()==null){
			return 1;
		}
		return this.getGongNengLCDM().compareTo(obj.getGongNengLCDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengLC clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengLC gongNengLC = new com.poweruniverse.nim.data.entity.sys.GongNengLC();
		
		for(com.poweruniverse.nim.data.entity.sys.GongNengLCMX subObj:this.getMxs()){
			gongNengLC.addTomxs(gongNengLC, subObj.clone());
		}
		gongNengLC.setGongNengLCMC(gongNengLCMC);
		gongNengLC.setGongNeng(gongNeng);
		gongNengLC.setShiFouQY(shiFouQY);
		
		return gongNengLC;
	}
	
	
	
	
	
	
}