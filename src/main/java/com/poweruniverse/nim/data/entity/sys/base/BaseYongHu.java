package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：用户
*/
@Version("2015-03-16 11:42:13")
public abstract class BaseYongHu  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseYongHu () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseYongHu (java.lang.Integer id) {
		this.setYongHuDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：yongHuDM
	private java.lang.Integer yongHuDM = null;
	public java.lang.Integer getYongHuDM(){return this.yongHuDM ;}
	public void setYongHuDM(java.lang.Integer yongHuDM){this.yongHuDM = yongHuDM;}

			
	// 属性：用户名称 （yongHuMC）
	private java.lang.String yongHuMC = null;
	public java.lang.String getYongHuMC(){return this.yongHuMC ;}
	public void setYongHuMC(java.lang.String yongHuMC){this.yongHuMC = yongHuMC;}
	
			
	// 属性：登录名 （dengLuDH）
	private java.lang.String dengLuDH = null;
	public java.lang.String getDengLuDH(){return this.dengLuDH ;}
	public void setDengLuDH(java.lang.String dengLuDH){this.dengLuDH = dengLuDH;}
	
			
	// 属性：登录密码 （dengLuMM）
	private java.lang.String dengLuMM = null;
	public java.lang.String getDengLuMM(){return this.dengLuMM ;}
	public void setDengLuMM(java.lang.String dengLuMM){this.dengLuMM = dengLuMM;}
	
			
	// 属性：联系电话 （lianXiDH）
	private java.lang.String lianXiDH = null;
	public java.lang.String getLianXiDH(){return this.lianXiDH ;}
	public void setLianXiDH(java.lang.String lianXiDH){this.lianXiDH = lianXiDH;}
	
			
	// 属性：电子邮箱 （dianZiYX）
	private java.lang.String dianZiYX = null;
	public java.lang.String getDianZiYX(){return this.dianZiYX ;}
	public void setDianZiYX(java.lang.String dianZiYX){this.dianZiYX = dianZiYX;}
	
	// 对象：用户状态 （yongHuZT）
	private com.poweruniverse.nim.data.entity.sys.YongHuZT yongHuZT;
	public com.poweruniverse.nim.data.entity.sys.YongHuZT getYongHuZT(){return this.yongHuZT ;}
	public void setYongHuZT(com.poweruniverse.nim.data.entity.sys.YongHuZT yongHuZT){this.yongHuZT = yongHuZT;}

	// 集合：角色集合 （jss）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.YongHuJS> jss = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.YongHuJS>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.YongHuJS> getJss(){return this.jss ;}
	public void setJss(java.util.Set<com.poweruniverse.nim.data.entity.sys.YongHuJS> jss){this.jss = jss;}
	public void addTojss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.YongHu mainObj = (com.poweruniverse.nim.data.entity.sys.YongHu)parent;
		com.poweruniverse.nim.data.entity.sys.YongHuJS subObj = (com.poweruniverse.nim.data.entity.sys.YongHuJS)detail;
		subObj.setYongHu(mainObj);
		mainObj.getJss().add(subObj);
	}
	public void removeFromjss(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.YongHu mainObj = (com.poweruniverse.nim.data.entity.sys.YongHu)parent;
		com.poweruniverse.nim.data.entity.sys.YongHuJS subObj = (com.poweruniverse.nim.data.entity.sys.YongHuJS)detail;
		subObj.setYongHu(null);
		mainObj.getJss().remove(subObj);
	}
	public Object getjssById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.YongHuJS> ds = this.getJss().iterator();
		com.poweruniverse.nim.data.entity.sys.YongHuJS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getYongHuJSDM()!=null && d.getYongHuJSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.YongHuJS newjssByParent(com.poweruniverse.nim.data.entity.sys.YongHu parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.YongHuJS subObj = new com.poweruniverse.nim.data.entity.sys.YongHuJS();
		//
		subObj.setYongHu(parent);
		//
		return subObj;
	}
	
			
	// 属性：最后登录日期 （zuiHouDLRQ）
	private java.util.Date zuiHouDLRQ = null;
	public java.util.Date getZuiHouDLRQ(){return this.zuiHouDLRQ ;}
	public void setZuiHouDLRQ(java.util.Date zuiHouDLRQ){this.zuiHouDLRQ = zuiHouDLRQ;}
	
	// 对象：所属部门 （buMen）
	private com.poweruniverse.nim.data.entity.sys.BuMen buMen;
	public com.poweruniverse.nim.data.entity.sys.BuMen getBuMen(){return this.buMen ;}
	public void setBuMen(com.poweruniverse.nim.data.entity.sys.BuMen buMen){this.buMen = buMen;}

			
	// 属性：身份证号码 （shenFenZHM）
	private java.lang.String shenFenZHM = null;
	public java.lang.String getShenFenZHM(){return this.shenFenZHM ;}
	public void setShenFenZHM(java.lang.String shenFenZHM){this.shenFenZHM = shenFenZHM;}
	
			
	// 属性：最后登录IP （zuiHouDLIP）
	private java.lang.String zuiHouDLIP = null;
	public java.lang.String getZuiHouDLIP(){return this.zuiHouDLIP ;}
	public void setZuiHouDLIP(java.lang.String zuiHouDLIP){this.zuiHouDLIP = zuiHouDLIP;}
	
			
	// 属性：开始IP （ipKaiShiFW）
	private java.lang.String ipKaiShiFW = null;
	public java.lang.String getIpKaiShiFW(){return this.ipKaiShiFW ;}
	public void setIpKaiShiFW(java.lang.String ipKaiShiFW){this.ipKaiShiFW = ipKaiShiFW;}
	
			
	// 属性：结束IP （ipJieShuFW）
	private java.lang.String ipJieShuFW = null;
	public java.lang.String getIpJieShuFW(){return this.ipJieShuFW ;}
	public void setIpJieShuFW(java.lang.String ipJieShuFW){this.ipJieShuFW = ipJieShuFW;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.YongHu)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.YongHu entity = (com.poweruniverse.nim.data.entity.sys.YongHu) obj;
			if (null == this.getYongHuDM() || null == entity.getYongHuDM()) return false;
			else return (this.getYongHuDM().equals(entity.getYongHuDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getYongHuDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getYongHuDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.yongHuMC+"";
	}

	public Integer pkValue() {
		return this.yongHuDM;
	}

	public String pkName() {
		return "yongHuDM";
	}

	public void pkNull() {
		this.yongHuDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.YongHu obj = (com.poweruniverse.nim.data.entity.sys.YongHu)o;
		if(this.getYongHuDM()==null){
			return 1;
		}
		return this.getYongHuDM().compareTo(obj.getYongHuDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.YongHu clone(){
		com.poweruniverse.nim.data.entity.sys.YongHu yongHu = new com.poweruniverse.nim.data.entity.sys.YongHu();
		
		yongHu.setYongHuMC(yongHuMC);
		yongHu.setDengLuDH(dengLuDH);
		yongHu.setDengLuMM(dengLuMM);
		yongHu.setLianXiDH(lianXiDH);
		yongHu.setDianZiYX(dianZiYX);
		yongHu.setYongHuZT(yongHuZT);
		for(com.poweruniverse.nim.data.entity.sys.YongHuJS subObj:this.getJss()){
			yongHu.addTojss(yongHu, subObj.clone());
		}
		yongHu.setZuiHouDLRQ(zuiHouDLRQ);
		yongHu.setBuMen(buMen);
		yongHu.setShenFenZHM(shenFenZHM);
		yongHu.setZuiHouDLIP(zuiHouDLIP);
		yongHu.setIpKaiShiFW(ipKaiShiFW);
		yongHu.setIpJieShuFW(ipJieShuFW);
		
		return yongHu;
	}
	
	
	
	
	
	
}