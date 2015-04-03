package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能
*/
@Version("2015-04-04 02:05:43")
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
	
	// 集合：操作集合 （czs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengCZ> czs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.GongNengCZ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengCZ> getCzs(){return this.czs ;}
	public void setCzs(java.util.Set<com.poweruniverse.nim.data.entity.sys.GongNengCZ> czs){this.czs = czs;}
	public void addToczs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNeng mainObj = (com.poweruniverse.nim.data.entity.sys.GongNeng)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengCZ subObj = (com.poweruniverse.nim.data.entity.sys.GongNengCZ)detail;
		subObj.setGongNeng(mainObj);
		mainObj.getCzs().add(subObj);
	}
	public void removeFromczs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.GongNeng mainObj = (com.poweruniverse.nim.data.entity.sys.GongNeng)parent;
		com.poweruniverse.nim.data.entity.sys.GongNengCZ subObj = (com.poweruniverse.nim.data.entity.sys.GongNengCZ)detail;
		subObj.setGongNeng(null);
		mainObj.getCzs().remove(subObj);
	}
	public Object getczsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.GongNengCZ> ds = this.getCzs().iterator();
		com.poweruniverse.nim.data.entity.sys.GongNengCZ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getGongNengCZDM()!=null && d.getGongNengCZDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.GongNengCZ newczsByParent(com.poweruniverse.nim.data.entity.sys.GongNeng parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.GongNengCZ subObj = new com.poweruniverse.nim.data.entity.sys.GongNengCZ();
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
	
			
	// 属性：功能序号 （gongNengXH）
	private java.lang.Integer gongNengXH = new java.lang.Integer(0);
	public java.lang.Integer getGongNengXH(){return this.gongNengXH ;}
	public void setGongNengXH(java.lang.Integer gongNengXH){this.gongNengXH = gongNengXH;}
	
	// 对象：实体类 （shiTiLei）
	private com.poweruniverse.nim.data.entity.sys.ShiTiLei shiTiLei;
	public com.poweruniverse.nim.data.entity.sys.ShiTiLei getShiTiLei(){return this.shiTiLei ;}
	public void setShiTiLei(com.poweruniverse.nim.data.entity.sys.ShiTiLei shiTiLei){this.shiTiLei = shiTiLei;}

	// 对象：系统 （xiTong）
	private com.poweruniverse.nim.data.entity.sys.XiTong xiTong;
	public com.poweruniverse.nim.data.entity.sys.XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(com.poweruniverse.nim.data.entity.sys.XiTong xiTong){this.xiTong = xiTong;}

			
	// 属性：是否流程功能 （shiFouLCGN）
	private java.lang.Boolean shiFouLCGN = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouLCGN(){return this.shiFouLCGN ;}
	public void setShiFouLCGN(java.lang.Boolean shiFouLCGN){this.shiFouLCGN = shiFouLCGN;}
	
			
	// 属性：流程完成事件 （processEndAction）
	private java.lang.String processEndAction = null;
	public java.lang.String getProcessEndAction(){return this.processEndAction ;}
	public void setProcessEndAction(java.lang.String processEndAction){this.processEndAction = processEndAction;}
	
			
	// 属性：流程开始事件 （processStartAction）
	private java.lang.String processStartAction = null;
	public java.lang.String getProcessStartAction(){return this.processStartAction ;}
	public void setProcessStartAction(java.lang.String processStartAction){this.processStartAction = processStartAction;}
	
			
	// 属性：功能版本 （gongNengBB）
	private java.lang.String gongNengBB = null;
	public java.lang.String getGongNengBB(){return this.gongNengBB ;}
	public void setGongNengBB(java.lang.String gongNengBB){this.gongNengBB = gongNengBB;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNeng)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNeng entity = (com.poweruniverse.nim.data.entity.sys.GongNeng) obj;
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
		com.poweruniverse.nim.data.entity.sys.GongNeng obj = (com.poweruniverse.nim.data.entity.sys.GongNeng)o;
		if(this.getGongNengDM()==null){
			return 1;
		}
		return this.getGongNengDM().compareTo(obj.getGongNengDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNeng clone(){
		com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng = new com.poweruniverse.nim.data.entity.sys.GongNeng();
		
		gongNeng.setGongNengMC(gongNengMC);
		gongNeng.setGongNengDH(gongNengDH);
		for(com.poweruniverse.nim.data.entity.sys.GongNengCZ subObj:this.getCzs()){
			gongNeng.addToczs(gongNeng, subObj.clone());
		}
		gongNeng.setGongNengIMG(gongNengIMG);
		gongNeng.setGongNengText(gongNengText);
		gongNeng.setGongNengXH(gongNengXH);
		gongNeng.setShiTiLei(shiTiLei);
		gongNeng.setXiTong(xiTong);
		gongNeng.setShiFouLCGN(shiFouLCGN);
		gongNeng.setProcessEndAction(processEndAction);
		gongNeng.setProcessStartAction(processStartAction);
		gongNeng.setGongNengBB(gongNengBB);
		
		return gongNeng;
	}
	
	
	
	
	
	
}