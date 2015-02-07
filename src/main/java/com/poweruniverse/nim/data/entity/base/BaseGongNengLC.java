package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengLC;
import com.poweruniverse.nim.data.entity.GongNengLCMX;
/*
* 实体类：功能流程
*/
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
	private java.util.Set<GongNengLCMX> mxs = new java.util.TreeSet<GongNengLCMX>();
	public java.util.Set<GongNengLCMX> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<GongNengLCMX> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		GongNengLC mainObj = (GongNengLC)parent;
		GongNengLCMX subObj = (GongNengLCMX)detail;
		subObj.setGongNengLC(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		GongNengLC mainObj = (GongNengLC)parent;
		GongNengLCMX subObj = (GongNengLCMX)detail;
		subObj.setGongNengLC(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<GongNengLCMX> ds = this.getMxs().iterator();
		GongNengLCMX d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengLCMXDM()!=null && d.getGongNengLCMXDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public GongNengLCMX newmxsByParent(GongNengLC parent) throws Exception{
		GongNengLCMX subObj = new GongNengLCMX();
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
	private GongNeng gongNeng;
	public GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(GongNeng gongNeng){this.gongNeng = gongNeng;}

			
	// 属性：是否启用 （shiFouQY）
	private java.lang.Boolean shiFouQY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouQY(){return this.shiFouQY ;}
	public void setShiFouQY(java.lang.Boolean shiFouQY){this.shiFouQY = shiFouQY;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNengLC)) return false;
		else {
			GongNengLC entity = (GongNengLC) obj;
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
		GongNengLC obj = (GongNengLC)o;
		if(this.getGongNengLCDM()==null){
			return 1;
		}
		return this.getGongNengLCDM().compareTo(obj.getGongNengLCDM());
	}
	
	public GongNengLC clone(){
		GongNengLC gongNengLC = new GongNengLC();
		
		for(GongNengLCMX subObj:this.getMxs()){
			gongNengLC.addTomxs(gongNengLC, subObj.clone());
		}
		gongNengLC.setGongNengLCMC(gongNengLCMC);
		gongNengLC.setGongNeng(gongNeng);
		gongNengLC.setShiFouQY(shiFouQY);
		
		return gongNengLC;
	}
	
	
	
	
	
	
}