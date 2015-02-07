package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.XiTong;

/*
* 实体类：功能
*/
@Version("2015-01-20 18:30:34")
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
	private java.util.Set<GongNengCZ> czs = new java.util.TreeSet<GongNengCZ>();
	public java.util.Set<GongNengCZ> getCzs(){return this.czs ;}
	public void setCzs(java.util.Set<GongNengCZ> czs){this.czs = czs;}
	public void addToczs(Object parent,Object detail){
		GongNeng mainObj = (GongNeng)parent;
		GongNengCZ subObj = (GongNengCZ)detail;
		subObj.setGongNeng(mainObj);
		mainObj.getCzs().add(subObj);
	}
	public void removeFromczs(Object parent,Object detail){
		GongNeng mainObj = (GongNeng)parent;
		GongNengCZ subObj = (GongNengCZ)detail;
		subObj.setGongNeng(null);
		mainObj.getCzs().remove(subObj);
	}
	public Object getczsById(Object id){
		java.util.Iterator<GongNengCZ> ds = this.getCzs().iterator();
		GongNengCZ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengCZDM()!=null && d.getGongNengCZDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public GongNengCZ newczsByParent(GongNeng parent) throws Exception{
		GongNengCZ subObj = new GongNengCZ();
		//
		subObj.setGongNeng(parent);
		//
		return subObj;
	}
	
			
	// 属性：功能图片 （gongNengIMG）
	private java.lang.String gongNengIMG = null;
	public java.lang.String getGongNengIMG(){return this.gongNengIMG ;}
	public void setGongNengIMG(java.lang.String gongNengIMG){this.gongNengIMG = gongNengIMG;}
	
			
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
	private ShiTiLei shiTiLei;
	public ShiTiLei getShiTiLei(){return this.shiTiLei ;}
	public void setShiTiLei(ShiTiLei shiTiLei){this.shiTiLei = shiTiLei;}

	// 对象：系统 （xiTong）
	private XiTong xiTong;
	public XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(XiTong xiTong){this.xiTong = xiTong;}

			
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
	
	// 对象：开始任务 （startGNCZ）
	private GongNengCZ startGNCZ;
	public GongNengCZ getStartGNCZ(){return this.startGNCZ ;}
	public void setStartGNCZ(GongNengCZ startGNCZ){this.startGNCZ = startGNCZ;}

			
	// 属性：是否记录日志 （shiFouJLRZ）
	private java.lang.Boolean shiFouJLRZ = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouJLRZ(){return this.shiFouJLRZ ;}
	public void setShiFouJLRZ(java.lang.Boolean shiFouJLRZ){this.shiFouJLRZ = shiFouJLRZ;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof GongNeng)) return false;
		else {
			GongNeng entity = (GongNeng) obj;
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
		GongNeng obj = (GongNeng)o;
		if(this.getGongNengDM()==null){
			return 1;
		}
		return this.getGongNengDM().compareTo(obj.getGongNengDM());
	}
	
	public GongNeng clone(){
		GongNeng gongNeng = new GongNeng();
		
		gongNeng.setGongNengMC(gongNengMC);
		gongNeng.setGongNengDH(gongNengDH);
		gongNeng.setGongNengClass(gongNengClass);
		for(GongNengCZ subObj:this.getCzs()){
			gongNeng.addToczs(gongNeng, subObj.clone());
		}
		gongNeng.setGongNengIMG(gongNengIMG);
		gongNeng.setGongNengText(gongNengText);
		gongNeng.setGongNengURL(gongNengURL);
		gongNeng.setGongNengXH(gongNengXH);
		gongNeng.setShiTiLei(shiTiLei);
		gongNeng.setXiTong(xiTong);
		gongNeng.setShiFouWS(shiFouWS);
		gongNeng.setWsClass(wsClass);
		gongNeng.setShiFouLCGN(shiFouLCGN);
		gongNeng.setStartGNCZ(startGNCZ);
		gongNeng.setShiFouJLRZ(shiFouJLRZ);
		
		return gongNeng;
	}
	
	
	
	
	
	
}