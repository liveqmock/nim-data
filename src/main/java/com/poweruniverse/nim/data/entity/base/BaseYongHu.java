package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.BuMen;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuJS;
import com.poweruniverse.nim.data.entity.YongHuZT;

/*
* 实体类：用户
*/
@Version("2015-01-20 18:30:35")
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
	
			
	// 属性：邮政编码 （youZhengBM）
	private java.lang.String youZhengBM = null;
	public java.lang.String getYouZhengBM(){return this.youZhengBM ;}
	public void setYouZhengBM(java.lang.String youZhengBM){this.youZhengBM = youZhengBM;}
	
			
	// 对象：用户状态 （yongHuZT）
	private YongHuZT yongHuZT;
	public YongHuZT getYongHuZT(){return this.yongHuZT ;}
	public void setYongHuZT(YongHuZT yongHuZT){this.yongHuZT = yongHuZT;}


	// 属性：电子邮箱 （dianZiYX）
	private java.lang.String dianZiYX = null;
	public java.lang.String getDianZiYX(){return this.dianZiYX ;}
	public void setDianZiYX(java.lang.String dianZiYX){this.dianZiYX = dianZiYX;}
	
			
	// 属性：通讯地址 （tongXunDZ）
	private java.lang.String tongXunDZ = null;
	public java.lang.String getTongXunDZ(){return this.tongXunDZ ;}
	public void setTongXunDZ(java.lang.String tongXunDZ){this.tongXunDZ = tongXunDZ;}
	
			
	// 属性：是否有签名 （shiFouQM）
	private java.lang.Boolean shiFouQM = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouQM(){return this.shiFouQM ;}
	public void setShiFouQM(java.lang.Boolean shiFouQM){this.shiFouQM = shiFouQM;}
	
			
	// 属性：最后登录日期 （zuiHouDLRQ）
	private java.util.Date zuiHouDLRQ = null;
	public java.util.Date getZuiHouDLRQ(){return this.zuiHouDLRQ ;}
	public void setZuiHouDLRQ(java.util.Date zuiHouDLRQ){this.zuiHouDLRQ = zuiHouDLRQ;}
	
			
	// 属性：首次是否登录 （shouCiDL）
	private java.lang.Boolean shouCiDL = new java.lang.Boolean(false);
	public java.lang.Boolean getShouCiDL(){return this.shouCiDL ;}
	public void setShouCiDL(java.lang.Boolean shouCiDL){this.shouCiDL = shouCiDL;}
	
	// 对象：所属部门 （suoShuBM）
	private BuMen buMen;
	public BuMen getBuMen(){return this.buMen ;}
	public void setBuMen(BuMen suoShuBM){this.buMen = suoShuBM;}

			
	// 属性：身份证号码 （shenFenZHM）
	private java.lang.String shenFenZHM = null;
	public java.lang.String getShenFenZHM(){return this.shenFenZHM ;}
	public void setShenFenZHM(java.lang.String shenFenZHM){this.shenFenZHM = shenFenZHM;}
	
			
	// 属性：最后登录IP （zuiHouDLIP）
	private java.lang.String zuiHouDLIP = null;
	public java.lang.String getZuiHouDLIP(){return this.zuiHouDLIP ;}
	public void setZuiHouDLIP(java.lang.String zuiHouDLIP){this.zuiHouDLIP = zuiHouDLIP;}
	
			
	// 属性：是否邮件提醒 （shiFouYJTX）
	private java.lang.Boolean shiFouYJTX = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouYJTX(){return this.shiFouYJTX ;}
	public void setShiFouYJTX(java.lang.Boolean shiFouYJTX){this.shiFouYJTX = shiFouYJTX;}
	
			
	// 属性：开始IP （ipKaiShiFW）
	private java.lang.String ipKaiShiFW = null;
	public java.lang.String getIpKaiShiFW(){return this.ipKaiShiFW ;}
	public void setIpKaiShiFW(java.lang.String ipKaiShiFW){this.ipKaiShiFW = ipKaiShiFW;}
	
			
	// 属性：结束IP （ipJieShuFW）
	private java.lang.String ipJieShuFW = null;
	public java.lang.String getIpJieShuFW(){return this.ipJieShuFW ;}
	public void setIpJieShuFW(java.lang.String ipJieShuFW){this.ipJieShuFW = ipJieShuFW;}
	
			
	// 属性：昵称 （niCheng）
	private java.lang.String niCheng = null;
	public java.lang.String getNiCheng(){return this.niCheng ;}
	public void setNiCheng(java.lang.String niCheng){this.niCheng = niCheng;}
	
			
	// 属性：欢迎页面 （huanYingYM）
	private java.lang.String huanYingYM = null;
	public java.lang.String getHuanYingYM(){return this.huanYingYM ;}
	public void setHuanYingYM(java.lang.String huanYingYM){this.huanYingYM = huanYingYM;}
	
	// 集合：角色集合 （jss）
	private java.util.Set<YongHuJS> jss = new java.util.TreeSet<YongHuJS>();
	public java.util.Set<YongHuJS> getJss(){return this.jss ;}
	public void setJss(java.util.Set<YongHuJS> jss){this.jss = jss;}
	public void addTojss(Object parent,Object detail){
		YongHu mainObj = (YongHu)parent;
		YongHuJS subObj = (YongHuJS)detail;
		subObj.setYongHu(mainObj);
		mainObj.getJss().add(subObj);
	}
	public void removeFromjss(Object parent,Object detail){
		YongHu mainObj = (YongHu)parent;
		YongHuJS subObj = (YongHuJS)detail;
		subObj.setYongHu(null);
		mainObj.getJss().remove(subObj);
	}
	public Object getjssById(Object id){
		java.util.Iterator<YongHuJS> ds = this.getJss().iterator();
		YongHuJS d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getYongHuJSDM()!=null && d.getYongHuJSDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public YongHuJS newjssByParent(YongHu parent) throws Exception{
		YongHuJS subObj = new YongHuJS();
		//
		subObj.setYongHu(parent);
		//
		return subObj;
	}
			
	// 属性：入职日期 （ruZhiRQ）
	private java.util.Date ruZhiRQ = null;
	public java.util.Date getRuZhiRQ(){return this.ruZhiRQ ;}
	public void setRuZhiRQ(java.util.Date ruZhiRQ){this.ruZhiRQ = ruZhiRQ;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof YongHu)) return false;
		else {
			YongHu entity = (YongHu) obj;
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
		YongHu obj = (YongHu)o;
		if(this.getYongHuDM()==null){
			return 1;
		}
		return this.getYongHuDM().compareTo(obj.getYongHuDM());
	}
	
	public YongHu clone(){
		YongHu yongHu = new YongHu();
		
		yongHu.setYongHuMC(yongHuMC);
		yongHu.setDengLuDH(dengLuDH);
		yongHu.setDengLuMM(dengLuMM);
		yongHu.setLianXiDH(lianXiDH);
		yongHu.setYouZhengBM(youZhengBM);
		yongHu.setDianZiYX(dianZiYX);
		yongHu.setTongXunDZ(tongXunDZ);
		yongHu.setShiFouQM(shiFouQM);
		yongHu.setZuiHouDLRQ(zuiHouDLRQ);
		yongHu.setShouCiDL(shouCiDL);
		yongHu.setShenFenZHM(shenFenZHM);
		yongHu.setZuiHouDLIP(zuiHouDLIP);
		yongHu.setShiFouYJTX(shiFouYJTX);
		yongHu.setIpKaiShiFW(ipKaiShiFW);
		yongHu.setIpJieShuFW(ipJieShuFW);
		yongHu.setNiCheng(niCheng);
		yongHu.setHuanYingYM(huanYingYM);
		yongHu.setRuZhiRQ(ruZhiRQ);
		
		return yongHu;
	}
	
	
	
	
	
	
}