package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：操作模版
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseCaoZuoMB  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseCaoZuoMB () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCaoZuoMB (java.lang.Integer id) {
		this.setCaoZuoMBDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：操作模版名称 （caoZuoMBMC）
	private java.lang.String caoZuoMBMC = null;
	public java.lang.String getCaoZuoMBMC(){return this.caoZuoMBMC ;}
	public void setCaoZuoMBMC(java.lang.String caoZuoMBMC){this.caoZuoMBMC = caoZuoMBMC;}
	
	// 主键：caoZuoMBDM
	private java.lang.Integer caoZuoMBDM = null;
	public java.lang.Integer getCaoZuoMBDM(){return this.caoZuoMBDM ;}
	public void setCaoZuoMBDM(java.lang.Integer caoZuoMBDM){this.caoZuoMBDM = caoZuoMBDM;}

			
	// 属性：操作模版代号 （caoZuoMBDH）
	private java.lang.String caoZuoMBDH = null;
	public java.lang.String getCaoZuoMBDH(){return this.caoZuoMBDH ;}
	public void setCaoZuoMBDH(java.lang.String caoZuoMBDH){this.caoZuoMBDH = caoZuoMBDH;}
	
	// 对象：功能操作 （gongNengCZ）
	private com.poweruniverse.nim.data.entity.system.GongNengCZ gongNengCZ;
	public com.poweruniverse.nim.data.entity.system.GongNengCZ getGongNengCZ(){return this.gongNengCZ ;}
	public void setGongNengCZ(com.poweruniverse.nim.data.entity.system.GongNengCZ gongNengCZ){this.gongNengCZ = gongNengCZ;}

			
	// 属性：操作模版路径 （caoZuoMBLJ）
	private java.lang.String caoZuoMBLJ = null;
	public java.lang.String getCaoZuoMBLJ(){return this.caoZuoMBLJ ;}
	public void setCaoZuoMBLJ(java.lang.String caoZuoMBLJ){this.caoZuoMBLJ = caoZuoMBLJ;}
	
	// 对象：用户 （yongHu）
	private com.poweruniverse.nim.data.entity.system.YongHu yongHu;
	public com.poweruniverse.nim.data.entity.system.YongHu getYongHu(){return this.yongHu ;}
	public void setYongHu(com.poweruniverse.nim.data.entity.system.YongHu yongHu){this.yongHu = yongHu;}

			
	// 属性：基础页面 （jiChuYM）
	private java.lang.String jiChuYM = null;
	public java.lang.String getJiChuYM(){return this.jiChuYM ;}
	public void setJiChuYM(java.lang.String jiChuYM){this.jiChuYM = jiChuYM;}
	
	// 集合：条件明细集合 （mxs）
	private java.util.Set<com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX> mxs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX>();
	public java.util.Set<com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX> getMxs(){return this.mxs ;}
	public void setMxs(java.util.Set<com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX> mxs){this.mxs = mxs;}
	public void addTomxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.CaoZuoMB mainObj = (com.poweruniverse.nim.data.entity.system.CaoZuoMB)parent;
		com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX subObj = (com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX)detail;
		subObj.setCaoZuoMB(mainObj);
		mainObj.getMxs().add(subObj);
	}
	public void removeFrommxs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.system.CaoZuoMB mainObj = (com.poweruniverse.nim.data.entity.system.CaoZuoMB)parent;
		com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX subObj = (com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX)detail;
		subObj.setCaoZuoMB(null);
		mainObj.getMxs().remove(subObj);
	}
	public Object getmxsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX> ds = this.getMxs().iterator();
		com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getCaoZuoMBTJMXDM()!=null && d.getCaoZuoMBTJMXDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX newmxsByParent(com.poweruniverse.nim.data.entity.system.CaoZuoMB parent) throws Exception{
		com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX subObj = new com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX();
		//
		subObj.setCaoZuoMB(parent);
		//
		return subObj;
	}
	
			
	// 属性：条件显示 （moBanTJXS）
	private java.lang.String moBanTJXS = null;
	public java.lang.String getMoBanTJXS(){return this.moBanTJXS ;}
	public void setMoBanTJXS(java.lang.String moBanTJXS){this.moBanTJXS = moBanTJXS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.CaoZuoMB)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.CaoZuoMB entity = (com.poweruniverse.nim.data.entity.system.CaoZuoMB) obj;
			if (null == this.getCaoZuoMBDM() || null == entity.getCaoZuoMBDM()) return false;
			else return (this.getCaoZuoMBDM().equals(entity.getCaoZuoMBDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCaoZuoMBDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCaoZuoMBDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoMBMC+"";
	}

	public Integer pkValue() {
		return this.caoZuoMBDM;
	}

	public String pkName() {
		return "caoZuoMBDM";
	}

	public void pkNull() {
		this.caoZuoMBDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.system.CaoZuoMB obj = (com.poweruniverse.nim.data.entity.system.CaoZuoMB)o;
		if(this.getCaoZuoMBDM()==null){
			return 1;
		}
		return this.getCaoZuoMBDM().compareTo(obj.getCaoZuoMBDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.CaoZuoMB clone(){
		com.poweruniverse.nim.data.entity.system.CaoZuoMB caoZuoMB = new com.poweruniverse.nim.data.entity.system.CaoZuoMB();
		
		caoZuoMB.setCaoZuoMBMC(caoZuoMBMC);
		caoZuoMB.setCaoZuoMBDH(caoZuoMBDH);
		caoZuoMB.setGongNengCZ(gongNengCZ);
		caoZuoMB.setCaoZuoMBLJ(caoZuoMBLJ);
		caoZuoMB.setYongHu(yongHu);
		caoZuoMB.setJiChuYM(jiChuYM);
		for(com.poweruniverse.nim.data.entity.system.CaoZuoMBTJMX subObj:this.getMxs()){
			caoZuoMB.addTomxs(caoZuoMB, subObj.clone());
		}
		caoZuoMB.setMoBanTJXS(moBanTJXS);
		
		return caoZuoMB;
	}
	
	
	
	
	
	
}