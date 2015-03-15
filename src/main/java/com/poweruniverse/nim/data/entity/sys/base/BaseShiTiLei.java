package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：实体类
*/
@Version("2015-03-08 11:42:34")
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
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.ZiDuan> zds = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.ZiDuan>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.ZiDuan> getZds(){return this.zds ;}
	public void setZds(java.util.Set<com.poweruniverse.nim.data.entity.sys.ZiDuan> zds){this.zds = zds;}
	public void addTozds(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.ShiTiLei mainObj = (com.poweruniverse.nim.data.entity.sys.ShiTiLei)parent;
		com.poweruniverse.nim.data.entity.sys.ZiDuan subObj = (com.poweruniverse.nim.data.entity.sys.ZiDuan)detail;
		subObj.setShiTiLei(mainObj);
		mainObj.getZds().add(subObj);
	}
	public void removeFromzds(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.ShiTiLei mainObj = (com.poweruniverse.nim.data.entity.sys.ShiTiLei)parent;
		com.poweruniverse.nim.data.entity.sys.ZiDuan subObj = (com.poweruniverse.nim.data.entity.sys.ZiDuan)detail;
		subObj.setShiTiLei(null);
		mainObj.getZds().remove(subObj);
	}
	public Object getzdsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.ZiDuan> ds = this.getZds().iterator();
		com.poweruniverse.nim.data.entity.sys.ZiDuan d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getZiDuanDM()!=null && d.getZiDuanDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.ZiDuan newzdsByParent(com.poweruniverse.nim.data.entity.sys.ShiTiLei parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.ZiDuan subObj = new com.poweruniverse.nim.data.entity.sys.ZiDuan();
		//
		subObj.setShiTiLei(parent);
		//
		return subObj;
	}
	
			
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
	private com.poweruniverse.nim.data.entity.sys.XiTong xiTong;
	public com.poweruniverse.nim.data.entity.sys.XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(com.poweruniverse.nim.data.entity.sys.XiTong xiTong){this.xiTong = xiTong;}

			
	// 属性：实体类版本 （shiTiLeiBB）
	private java.lang.String shiTiLeiBB = null;
	public java.lang.String getShiTiLeiBB(){return this.shiTiLeiBB ;}
	public void setShiTiLeiBB(java.lang.String shiTiLeiBB){this.shiTiLeiBB = shiTiLeiBB;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.ShiTiLei)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.ShiTiLei entity = (com.poweruniverse.nim.data.entity.sys.ShiTiLei) obj;
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
		com.poweruniverse.nim.data.entity.sys.ShiTiLei obj = (com.poweruniverse.nim.data.entity.sys.ShiTiLei)o;
		if(this.getShiTiLeiDM()==null){
			return 1;
		}
		return this.getShiTiLeiDM().compareTo(obj.getShiTiLeiDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.ShiTiLei clone(){
		com.poweruniverse.nim.data.entity.sys.ShiTiLei shiTiLei = new com.poweruniverse.nim.data.entity.sys.ShiTiLei();
		
		shiTiLei.setShiTiLeiMC(shiTiLeiMC);
		shiTiLei.setZhuJianLie(zhuJianLie);
		shiTiLei.setXianShiLie(xianShiLie);
		shiTiLei.setShiTiLeiClassName(shiTiLeiClassName);
		shiTiLei.setBiaoMing(biaoMing);
		for(com.poweruniverse.nim.data.entity.sys.ZiDuan subObj:this.getZds()){
			shiTiLei.addTozds(shiTiLei, subObj.clone());
		}
		shiTiLei.setShiFouYWB(shiFouYWB);
		shiTiLei.setPaiXuLie(paiXuLie);
		shiTiLei.setShiTiLeiDH(shiTiLeiDH);
		shiTiLei.setXiTong(xiTong);
		shiTiLei.setShiTiLeiBB(shiTiLeiBB);
		
		return shiTiLei;
	}
	
	
	
	
	
	
}