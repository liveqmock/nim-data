package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.ShiTiLeiLX;
import com.poweruniverse.nim.data.entity.XiTong;
import com.poweruniverse.nim.data.entity.ZiDuan;

/*
* 实体类：实体类
*/
@Version("2015-01-20 18:30:36")
public abstract class BaseShiTiLei  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseShiTiLei () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseShiTiLei (java.lang.Integer id) {
		this.setShiTiLeiDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：实体类名称 （shiTiLeiMC）
	private java.lang.String shiTiLeiMC = null;
	public java.lang.String getShiTiLeiMC(){return this.shiTiLeiMC ;}
	public void setShiTiLeiMC(java.lang.String shiTiLeiMC){this.shiTiLeiMC = shiTiLeiMC;}
	
	// 主键：shiTiLeiDM
	private java.lang.Integer shiTiLeiDM = null;
	public java.lang.Integer getShiTiLeiDM(){return this.shiTiLeiDM ;}
	public void setShiTiLeiDM(java.lang.Integer shiTiLeiDM){this.shiTiLeiDM = shiTiLeiDM;}

			
	// 属性：主键列 （zhuJianLie）
	private java.lang.String zhuJianLie = null;
	public java.lang.String getZhuJianLie(){return this.zhuJianLie ;}
	public void setZhuJianLie(java.lang.String zhuJianLie){this.zhuJianLie = zhuJianLie;}
	
			
	// 属性：显示列 （xianShiLie）
	private java.lang.String xianShiLie = null;
	public java.lang.String getXianShiLie(){return this.xianShiLie ;}
	public void setXianShiLie(java.lang.String xianShiLie){this.xianShiLie = xianShiLie;}
	
			
	// 属性：JAVA类名 （shiTiLeiClassName）
	private java.lang.String shiTiLeiClassName = null;
	public java.lang.String getShiTiLeiClassName(){return this.shiTiLeiClassName ;}
	public void setShiTiLeiClassName(java.lang.String shiTiLeiClassName){this.shiTiLeiClassName = shiTiLeiClassName;}
	
			
	// 属性：数据库表名 （biaoMing）
	private java.lang.String biaoMing = null;
	public java.lang.String getBiaoMing(){return this.biaoMing ;}
	public void setBiaoMing(java.lang.String biaoMing){this.biaoMing = biaoMing;}
	
	// 集合：字段集合 （zds）
	private java.util.Set<ZiDuan> zds = new java.util.TreeSet<ZiDuan>();
	public java.util.Set<ZiDuan> getZds(){return this.zds ;}
	public void setZds(java.util.Set<ZiDuan> zds){this.zds = zds;}
	public void addTozds(Object parent,Object detail){
		ShiTiLei mainObj = (ShiTiLei)parent;
		ZiDuan subObj = (ZiDuan)detail;
		subObj.setShiTiLei(mainObj);
		mainObj.getZds().add(subObj);
	}
	public void removeFromzds(Object parent,Object detail){
		ShiTiLei mainObj = (ShiTiLei)parent;
		ZiDuan subObj = (ZiDuan)detail;
		subObj.setShiTiLei(null);
		mainObj.getZds().remove(subObj);
	}
	public Object getzdsById(Object id){
		java.util.Iterator<ZiDuan> ds = this.getZds().iterator();
		ZiDuan d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getZiDuanDM()!=null && d.getZiDuanDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public ZiDuan newzdsByParent(ShiTiLei parent) throws Exception{
		ZiDuan subObj = new ZiDuan();
		//
		subObj.setShiTiLei(parent);
		//
		return subObj;
	}
	
			
	// 属性：是否数据库表 （shiFouSJKB）
	private java.lang.Boolean shiFouSJKB = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSJKB(){return this.shiFouSJKB ;}
	public void setShiFouSJKB(java.lang.Boolean shiFouSJKB){this.shiFouSJKB = shiFouSJKB;}
	
			
	// 属性：是否功能 （shiFouGN）
	private java.lang.Boolean shiFouGN = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouGN(){return this.shiFouGN ;}
	public void setShiFouGN(java.lang.Boolean shiFouGN){this.shiFouGN = shiFouGN;}
	
			
	// 属性：功能代号 （gongNengDH）
	private java.lang.String gongNengDH = null;
	public java.lang.String getGongNengDH(){return this.gongNengDH ;}
	public void setGongNengDH(java.lang.String gongNengDH){this.gongNengDH = gongNengDH;}
	
			
	// 属性：是否业务表 （shiFouYWB）
	private java.lang.Boolean shiFouYWB = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouYWB(){return this.shiFouYWB ;}
	public void setShiFouYWB(java.lang.Boolean shiFouYWB){this.shiFouYWB = shiFouYWB;}
	
			
	// 属性：排序列 （paiXuLie）
	private java.lang.String paiXuLie = null;
	public java.lang.String getPaiXuLie(){return this.paiXuLie ;}
	public void setPaiXuLie(java.lang.String paiXuLie){this.paiXuLie = paiXuLie;}
	
			
	// 属性：实体类代号 （shiTiLeiDH）
	private java.lang.String shiTiLeiDH = null;
	public java.lang.String getShiTiLeiDH(){return this.shiTiLeiDH ;}
	public void setShiTiLeiDH(java.lang.String shiTiLeiDH){this.shiTiLeiDH = shiTiLeiDH;}
	
	// 对象：系统 （xiTong）
	private XiTong xiTong;
	public XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(XiTong xiTong){this.xiTong = xiTong;}

	// 对象：实体类类型 （shiTiLeiLX）
	private ShiTiLeiLX shiTiLeiLX;
	public ShiTiLeiLX getShiTiLeiLX(){return this.shiTiLeiLX ;}
	public void setShiTiLeiLX(ShiTiLeiLX shiTiLeiLX){this.shiTiLeiLX = shiTiLeiLX;}

			
	// 属性：版本 （shiTiLeiBB）
	private java.lang.String shiTiLeiBB = null;
	public java.lang.String getShiTiLeiBB(){return this.shiTiLeiBB ;}
	public void setShiTiLeiBB(java.lang.String shiTiLeiBB){this.shiTiLeiBB = shiTiLeiBB;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof ShiTiLei)) return false;
		else {
			ShiTiLei entity = (ShiTiLei) obj;
			if (null == this.getShiTiLeiDM() || null == entity.getShiTiLeiDM()) return false;
			else return (this.getShiTiLeiDM().equals(entity.getShiTiLeiDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getShiTiLeiDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getShiTiLeiDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.shiTiLeiMC+"";
	}

	public Integer pkValue() {
		return this.shiTiLeiDM;
	}

	public String pkName() {
		return "shiTiLeiDM";
	}

	public void pkNull() {
		this.shiTiLeiDM = null;;
	}
	
	public int compareTo(Object o) {
		ShiTiLei obj = (ShiTiLei)o;
		if(this.getShiTiLeiDM()==null){
			return 1;
		}
		return this.getShiTiLeiDM().compareTo(obj.getShiTiLeiDM());
	}
	
	public ShiTiLei clone(){
		ShiTiLei shiTiLei = new ShiTiLei();
		
		shiTiLei.setShiTiLeiMC(shiTiLeiMC);
		shiTiLei.setZhuJianLie(zhuJianLie);
		shiTiLei.setXianShiLie(xianShiLie);
		shiTiLei.setShiTiLeiClassName(shiTiLeiClassName);
		shiTiLei.setBiaoMing(biaoMing);
		for(ZiDuan subObj:this.getZds()){
			shiTiLei.addTozds(shiTiLei, subObj.clone());
		}
		shiTiLei.setShiFouSJKB(shiFouSJKB);
		shiTiLei.setShiFouGN(shiFouGN);
		shiTiLei.setGongNengDH(gongNengDH);
		shiTiLei.setShiFouYWB(shiFouYWB);
		shiTiLei.setPaiXuLie(paiXuLie);
		shiTiLei.setShiTiLeiDH(shiTiLeiDH);
		shiTiLei.setXiTong(xiTong);
		shiTiLei.setShiTiLeiLX(shiTiLeiLX);
		shiTiLei.setShiTiLeiBB(shiTiLeiBB);
		
		return shiTiLei;
	}
	
	
	
	
	
	
}