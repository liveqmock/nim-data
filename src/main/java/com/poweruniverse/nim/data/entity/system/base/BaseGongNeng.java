package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.Version;

/*
* 实体类：功能
*/
@Version("2015-03-05 15:49:09")
public abstract class BaseGongNeng  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNeng () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNeng (java.lang.Integer id) {
		this.setGongNengDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：gongNengDM
	private java.lang.Integer gongNengDM = null;
	public java.lang.Integer getGongNengDM(){return this.gongNengDM ;}
	public void setGongNengDM(java.lang.Integer gongNengDM){this.gongNengDM = gongNengDM;}

			
	// 属性：功能名称 （gongNengMC）
	private java.lang.String gongNengMC = null;
	public java.lang.String getGongNengMC(){return this.gongNengMC ;}
	public void setGongNengMC(java.lang.String gongNengMC){this.gongNengMC = gongNengMC;}
	
			
	// 属性：功能代号 （gongNengDH）
	private java.lang.String gongNengDH = null;
	public java.lang.String getGongNengDH(){return this.gongNengDH ;}
	public void setGongNengDH(java.lang.String gongNengDH){this.gongNengDH = gongNengDH;}
	
			
	// 属性：功能实现类 （gongNengClass）
	private java.lang.String gongNengClass = null;
	public java.lang.String getGongNengClass(){return this.gongNengClass ;}
	public void setGongNengClass(java.lang.String gongNengClass){this.gongNengClass = gongNengClass;}
	
	// 集合：操作集合 （czs）
	private java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengCZ> czs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.system.GongNengCZ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengCZ> getCzs(){return this.czs ;}
	public void setCzs(java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengCZ> czs){this.czs = czs;}
	public void addToczs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengCZ subObj = (com.poweruniverse.nim.data.entity.system.GongNengCZ)detail;
		subObj.setGongNeng(mainObj);
		mainObj.getCzs().add(subObj);
	}
	public void removeFromczs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengCZ subObj = (com.poweruniverse.nim.data.entity.system.GongNengCZ)detail;
		subObj.setGongNeng(null);
		mainObj.getCzs().remove(subObj);
	}
	public Object getczsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.system.GongNengCZ> ds = this.getCzs().iterator();
		com.poweruniverse.nim.data.entity.system.GongNengCZ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengCZDM()!=null && d.getGongNengCZDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.system.GongNengCZ newczsByParent(com.poweruniverse.nim.data.entity.system.GongNeng parent) throws Exception{
		com.poweruniverse.nim.data.entity.system.GongNengCZ subObj = new com.poweruniverse.nim.data.entity.system.GongNengCZ();
		//
		subObj.setGongNeng(parent);
		//
		return subObj;
	}
	
			
	// 属性：功能文本 （gongNengText）
	private java.lang.String gongNengText = null;
	public java.lang.String getGongNengText(){return this.gongNengText ;}
	public void setGongNengText(java.lang.String gongNengText){this.gongNengText = gongNengText;}
	
			
	// 属性：功能URL （gongNengURL）
	private java.lang.String gongNengURL = null;
	public java.lang.String getGongNengURL(){return this.gongNengURL ;}
	public void setGongNengURL(java.lang.String gongNengURL){this.gongNengURL = gongNengURL;}
	
			
	// 属性：功能序号 （gongNengXH）
	private java.lang.Integer gongNengXH = new java.lang.Integer(0);
	public java.lang.Integer getGongNengXH(){return this.gongNengXH ;}
	public void setGongNengXH(java.lang.Integer gongNengXH){this.gongNengXH = gongNengXH;}
	
	// 对象：实体类 （shiTiLei）
	private com.poweruniverse.nim.data.entity.system.ShiTiLei shiTiLei;
	public com.poweruniverse.nim.data.entity.system.ShiTiLei getShiTiLei(){return this.shiTiLei ;}
	public void setShiTiLei(com.poweruniverse.nim.data.entity.system.ShiTiLei shiTiLei){this.shiTiLei = shiTiLei;}

	// 对象：系统 （xiTong）
	private com.poweruniverse.nim.data.entity.system.XiTong xiTong;
	public com.poweruniverse.nim.data.entity.system.XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(com.poweruniverse.nim.data.entity.system.XiTong xiTong){this.xiTong = xiTong;}

			
	// 属性：是否webservice （shiFouWS）
	private java.lang.Boolean shiFouWS = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWS(){return this.shiFouWS ;}
	public void setShiFouWS(java.lang.Boolean shiFouWS){this.shiFouWS = shiFouWS;}
	
			
	// 属性：webservice类名 （wsClass）
	private java.lang.String wsClass = null;
	public java.lang.String getWsClass(){return this.wsClass ;}
	public void setWsClass(java.lang.String wsClass){this.wsClass = wsClass;}
	
			
	// 属性：是否流程功能 （shiFouLCGN）
	private java.lang.Boolean shiFouLCGN = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouLCGN(){return this.shiFouLCGN ;}
	public void setShiFouLCGN(java.lang.Boolean shiFouLCGN){this.shiFouLCGN = shiFouLCGN;}
	
	// 集合：功能流程集合 （lcs）
	private java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLC> lcs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.system.GongNengLC>();
	public java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLC> getLcs(){return this.lcs ;}
	public void setLcs(java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengLC> lcs){this.lcs = lcs;}
	public void addTolcs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengLC subObj = (com.poweruniverse.nim.data.entity.system.GongNengLC)detail;
		subObj.setGongNeng(mainObj);
		mainObj.getLcs().add(subObj);
	}
	public void removeFromlcs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengLC subObj = (com.poweruniverse.nim.data.entity.system.GongNengLC)detail;
		subObj.setGongNeng(null);
		mainObj.getLcs().remove(subObj);
	}
	public Object getlcsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.system.GongNengLC> ds = this.getLcs().iterator();
		com.poweruniverse.nim.data.entity.system.GongNengLC d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengLCDM()!=null && d.getGongNengLCDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.system.GongNengLC newlcsByParent(com.poweruniverse.nim.data.entity.system.GongNeng parent) throws Exception{
		com.poweruniverse.nim.data.entity.system.GongNengLC subObj = new com.poweruniverse.nim.data.entity.system.GongNengLC();
		//
		subObj.setGongNeng(parent);
		//
		return subObj;
	}
	
			
	// 属性：是否记录日志 （shiFouJLRZ）
	private java.lang.Boolean shiFouJLRZ = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouJLRZ(){return this.shiFouJLRZ ;}
	public void setShiFouJLRZ(java.lang.Boolean shiFouJLRZ){this.shiFouJLRZ = shiFouJLRZ;}
	
	// 集合：工作流集合 （gzls）
	private java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengGZL> gzls = new java.util.TreeSet<com.poweruniverse.nim.data.entity.system.GongNengGZL>();
	public java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengGZL> getGzls(){return this.gzls ;}
	public void setGzls(java.util.Set<com.poweruniverse.nim.data.entity.system.GongNengGZL> gzls){this.gzls = gzls;}
	public void addTogzls(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengGZL subObj = (com.poweruniverse.nim.data.entity.system.GongNengGZL)detail;
		subObj.setGongNeng(mainObj);
		mainObj.getGzls().add(subObj);
	}
	public void removeFromgzls(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.GongNeng mainObj = (com.poweruniverse.nim.data.entity.system.GongNeng)parent;
		com.poweruniverse.nim.data.entity.system.GongNengGZL subObj = (com.poweruniverse.nim.data.entity.system.GongNengGZL)detail;
		subObj.setGongNeng(null);
		mainObj.getGzls().remove(subObj);
	}
	public Object getgzlsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.system.GongNengGZL> ds = this.getGzls().iterator();
		com.poweruniverse.nim.data.entity.system.GongNengGZL d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengGZLDM()!=null && d.getGongNengGZLDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.system.GongNengGZL newgzlsByParent(com.poweruniverse.nim.data.entity.system.GongNeng parent) throws Exception{
		com.poweruniverse.nim.data.entity.system.GongNengGZL subObj = new com.poweruniverse.nim.data.entity.system.GongNengGZL();
		//
		subObj.setGongNeng(parent);
		//
		return subObj;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.GongNeng)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.GongNeng entity = (com.poweruniverse.nim.data.entity.system.GongNeng) obj;
			if (null == this.getGongNengDM() || null == entity.getGongNengDM()) return false;
			else return (this.getGongNengDM().equals(entity.getGongNengDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengMC+"";
	}

	public Integer pkValue() {
		return this.gongNengDM;
	}

	public String pkName() {
		return "gongNengDM";
	}

	public void pkNull() {
		this.gongNengDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.system.GongNeng obj = (com.poweruniverse.nim.data.entity.system.GongNeng)o;
		if(this.getGongNengDM()==null){
			return 1;
		}
		return this.getGongNengDM().compareTo(obj.getGongNengDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.GongNeng clone(){
		com.poweruniverse.nim.data.entity.system.GongNeng gongNeng = new com.poweruniverse.nim.data.entity.system.GongNeng();
		
		gongNeng.setGongNengMC(gongNengMC);
		gongNeng.setGongNengDH(gongNengDH);
		gongNeng.setGongNengClass(gongNengClass);
		for(com.poweruniverse.nim.data.entity.system.GongNengCZ subObj:this.getCzs()){
			gongNeng.addToczs(gongNeng, subObj.clone());
		}
		gongNeng.setGongNengText(gongNengText);
		gongNeng.setGongNengURL(gongNengURL);
		gongNeng.setGongNengXH(gongNengXH);
		gongNeng.setShiTiLei(shiTiLei);
		gongNeng.setXiTong(xiTong);
		gongNeng.setShiFouWS(shiFouWS);
		gongNeng.setWsClass(wsClass);
		gongNeng.setShiFouLCGN(shiFouLCGN);
		for(com.poweruniverse.nim.data.entity.system.GongNengLC subObj:this.getLcs()){
			gongNeng.addTolcs(gongNeng, subObj.clone());
		}
		gongNeng.setShiFouJLRZ(shiFouJLRZ);
		for(com.poweruniverse.nim.data.entity.system.GongNengGZL subObj:this.getGzls()){
			gongNeng.addTogzls(gongNeng, subObj.clone());
		}
		
		return gongNeng;
	}
	
	
	
	
	
	
}