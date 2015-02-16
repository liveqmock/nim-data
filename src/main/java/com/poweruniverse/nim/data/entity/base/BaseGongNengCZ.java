package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.GongNengCZBL;

/*
* 实体类：功能操作
*/
@Version("2015-01-20 18:30:34")
public abstract class BaseGongNengCZ  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengCZ () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengCZ (java.lang.Integer id) {
		this.setGongNengCZDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：gongNengCZDM
	private java.lang.Integer gongNengCZDM = null;
	public java.lang.Integer getGongNengCZDM(){return this.gongNengCZDM ;}
	public void setGongNengCZDM(java.lang.Integer gongNengCZDM){this.gongNengCZDM = gongNengCZDM;}

	// 对象：功能 （gongNeng）
	private GongNeng gongNeng;
	public GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(GongNeng gongNeng){this.gongNeng = gongNeng;}

			
	// 属性：操作代号 （caoZuoDH）
	private java.lang.String caoZuoDH = null;
	public java.lang.String getCaoZuoDH(){return this.caoZuoDH ;}
	public void setCaoZuoDH(java.lang.String caoZuoDH){this.caoZuoDH = caoZuoDH;}
	
			
	// 属性：操作名称 （caoZuoMC）
	private java.lang.String caoZuoMC = null;
	public java.lang.String getCaoZuoMC(){return this.caoZuoMC ;}
	public void setCaoZuoMC(java.lang.String caoZuoMC){this.caoZuoMC = caoZuoMC;}
	
			
	// 属性：对象相关 （duiXiangXG）
	private java.lang.Boolean duiXiangXG = new java.lang.Boolean(false);
	public java.lang.Boolean getDuiXiangXG(){return this.duiXiangXG ;}
	public void setDuiXiangXG(java.lang.Boolean duiXiangXG){this.duiXiangXG = duiXiangXG;}
	
	// 集合：操作变量集合 （czbls）
	private java.util.Set<GongNengCZBL> czbls = new java.util.TreeSet<GongNengCZBL>();
	public java.util.Set<GongNengCZBL> getCzbls(){return this.czbls ;}
	public void setCzbls(java.util.Set<GongNengCZBL> czbls){this.czbls = czbls;}
	public void addToczbls(Object parent,Object detail){
		GongNengCZ mainObj = (GongNengCZ)parent;
		GongNengCZBL subObj = (GongNengCZBL)detail;
		subObj.setGongNengCZ(mainObj);
		mainObj.getCzbls().add(subObj);
	}
	public void removeFromczbls(Object parent,Object detail){
		GongNengCZ mainObj = (GongNengCZ)parent;
		GongNengCZBL subObj = (GongNengCZBL)detail;
		subObj.setGongNengCZ(null);
		mainObj.getCzbls().remove(subObj);
	}
	public Object getczblsById(Object id){
		java.util.Iterator<GongNengCZBL> ds = this.getCzbls().iterator();
		GongNengCZBL d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getCaoZuoBLDM()!=null && d.getCaoZuoBLDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public GongNengCZBL newczblsByParent(GongNengCZ parent) throws Exception{
		GongNengCZBL subObj = new GongNengCZBL();
		//
		subObj.setGongNengCZ(parent);
		//
		return subObj;
	}
			
	// 属性：可以授权 （keYiSQ）
	private java.lang.Boolean keYiSQ = new java.lang.Boolean(false);
	public java.lang.Boolean getKeYiSQ(){return this.keYiSQ ;}
	public void setKeYiSQ(java.lang.Boolean keYiSQ){this.keYiSQ = keYiSQ;}
	
			
	// 属性：功能操作序号 （gongNengCZXH）
	private java.lang.Integer gongNengCZXH = new java.lang.Integer(0);
	public java.lang.Integer getGongNengCZXH(){return this.gongNengCZXH ;}
	public void setGongNengCZXH(java.lang.Integer gongNengCZXH){this.gongNengCZXH = gongNengCZXH;}
	
			
	// 属性：显示链接 （xianShiLJ）
	private java.lang.Boolean xianShiLJ = new java.lang.Boolean(false);
	public java.lang.Boolean getXianShiLJ(){return this.xianShiLJ ;}
	public void setXianShiLJ(java.lang.Boolean xianShiLJ){this.xianShiLJ = xianShiLJ;}
	
			
	// 属性：是否webService （shiFouWS）
	private java.lang.Boolean shiFouWS = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWS(){return this.shiFouWS ;}
	public void setShiFouWS(java.lang.Boolean shiFouWS){this.shiFouWS = shiFouWS;}
	
	// 对象：替代功能操作 （tiDaiGNCZ）
	private GongNengCZ tiDaiGNCZ;
	public GongNengCZ getTiDaiGNCZ(){return this.tiDaiGNCZ ;}
	public void setTiDaiGNCZ(GongNengCZ tiDaiGNCZ){this.tiDaiGNCZ = tiDaiGNCZ;}

			
	// 属性：是否启用 （shiFouQY）
	private java.lang.Boolean shiFouQY = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouQY(){return this.shiFouQY ;}
	public void setShiFouQY(java.lang.Boolean shiFouQY){this.shiFouQY = shiFouQY;}
	
			
	// 属性：是否记录日志 （shiFouJLRZ）
	private java.lang.Boolean shiFouJLRZ = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouJLRZ(){return this.shiFouJLRZ ;}
	public void setShiFouJLRZ(java.lang.Boolean shiFouJLRZ){this.shiFouJLRZ = shiFouJLRZ;}
	
			
	// 属性：是否多行处理 （shiFouDHCL）
	private java.lang.Boolean shiFouDHCL = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouDHCL(){return this.shiFouDHCL ;}
	public void setShiFouDHCL(java.lang.Boolean shiFouDHCL){this.shiFouDHCL = shiFouDHCL;}
	
	// 对象：下一功能操作 （xiaYiGNCZ）
	private GongNengCZ xiaYiGNCZ;
	public GongNengCZ getXiaYiGNCZ(){return this.xiaYiGNCZ ;}
	public void setXiaYiGNCZ(GongNengCZ xiaYiGNCZ){this.xiaYiGNCZ = xiaYiGNCZ;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNengCZ)) return false;
		else {
			GongNengCZ entity = (GongNengCZ) obj;
			if (null == this.getGongNengCZDM() || null == entity.getGongNengCZDM()) return false;
			else return (this.getGongNengCZDM().equals(entity.getGongNengCZDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengCZDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengCZDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoMC+"";
	}

	public Integer pkValue() {
		return this.gongNengCZDM;
	}

	public String pkName() {
		return "gongNengCZDM";
	}

	public void pkNull() {
		this.gongNengCZDM = null;;
	}
	
	public int compareTo(Object o) {
		GongNengCZ obj = (GongNengCZ)o;
		if(this.getGongNengCZDM()==null){
			return 1;
		}
		return this.getGongNengCZDM().compareTo(obj.getGongNengCZDM());
	}
	
	public GongNengCZ clone(){
		GongNengCZ gongNengCZ = new GongNengCZ();
		
		gongNengCZ.setGongNeng(gongNeng);
		gongNengCZ.setCaoZuoDH(caoZuoDH);
		gongNengCZ.setCaoZuoMC(caoZuoMC);
		gongNengCZ.setDuiXiangXG(duiXiangXG);
		gongNengCZ.setKeYiSQ(keYiSQ);
		gongNengCZ.setGongNengCZXH(gongNengCZXH);
		gongNengCZ.setXianShiLJ(xianShiLJ);
		gongNengCZ.setShiFouWS(shiFouWS);
		gongNengCZ.setTiDaiGNCZ(tiDaiGNCZ);
		gongNengCZ.setShiFouQY(shiFouQY);
		gongNengCZ.setShiFouJLRZ(shiFouJLRZ);
		gongNengCZ.setShiFouDHCL(shiFouDHCL);
		gongNengCZ.setXiaYiGNCZ(xiaYiGNCZ);
		
		return gongNengCZ;
	}
	
	
	
	
	
	
}