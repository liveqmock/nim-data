package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.LiuChengJSBL;
import com.poweruniverse.nim.data.entity.LiuChengJSPJ;
import com.poweruniverse.nim.data.entity.LiuChengJSSJ;
import com.poweruniverse.nim.data.entity.LiuChengJSXJ;
import com.poweruniverse.nim.data.entity.YongHu;
/*
* 实体类：流程检视
*/
public abstract class BaseLiuChengJS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseLiuChengJS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLiuChengJS (java.lang.Integer id) {
		this.setLiuChengJSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：liuChengJSDM
	private java.lang.Integer liuChengJSDM = null;
	public java.lang.Integer getLiuChengJSDM(){return this.liuChengJSDM ;}
	public void setLiuChengJSDM(java.lang.Integer liuChengJSDM){this.liuChengJSDM = liuChengJSDM;}

			
	// 属性：操作代号 （caoZuoDH）
	private java.lang.String caoZuoDH = null;
	public java.lang.String getCaoZuoDH(){return this.caoZuoDH ;}
	public void setCaoZuoDH(java.lang.String caoZuoDH){this.caoZuoDH = caoZuoDH;}
	
			
	// 属性：操作名称 （caoZuoMC）
	private java.lang.String caoZuoMC = null;
	public java.lang.String getCaoZuoMC(){return this.caoZuoMC ;}
	public void setCaoZuoMC(java.lang.String caoZuoMC){this.caoZuoMC = caoZuoMC;}
	
			
	// 属性：操作人 （caoZuoRen）
	private java.lang.String caoZuoRen = null;
	public java.lang.String getCaoZuoRen(){return this.caoZuoRen ;}
	public void setCaoZuoRen(java.lang.String caoZuoRen){this.caoZuoRen = caoZuoRen;}
	
			
	// 属性：完成日期 （wanChengRQ）
	private java.util.Date wanChengRQ = null;
	public java.util.Date getWanChengRQ(){return this.wanChengRQ ;}
	public void setWanChengRQ(java.util.Date wanChengRQ){this.wanChengRQ = wanChengRQ;}
	
			
	// 属性：功能对象代码 （gongNengObjId）
	private java.lang.Integer gongNengObjId = new java.lang.Integer(0);
	public java.lang.Integer getGongNengObjId(){return this.gongNengObjId ;}
	public void setGongNengObjId(java.lang.Integer gongNengObjId){this.gongNengObjId = gongNengObjId;}
	
			
	// 属性：功能代号 （gongNengDH）
	private java.lang.String gongNengDH = null;
	public java.lang.String getGongNengDH(){return this.gongNengDH ;}
	public void setGongNengDH(java.lang.String gongNengDH){this.gongNengDH = gongNengDH;}
	
			
	// 属性：是否处理 （shiFouCL）
	private java.lang.Boolean shiFouCL = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouCL(){return this.shiFouCL ;}
	public void setShiFouCL(java.lang.Boolean shiFouCL){this.shiFouCL = shiFouCL;}
	
			
	// 属性：是否完成 （shiFouWC）
	private java.lang.Boolean shiFouWC = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWC(){return this.shiFouWC ;}
	public void setShiFouWC(java.lang.Boolean shiFouWC){this.shiFouWC = shiFouWC;}
	
			
	// 属性：历史记录id （historyId）
	private java.lang.Integer historyId = new java.lang.Integer(0);
	public java.lang.Integer getHistoryId(){return this.historyId ;}
	public void setHistoryId(java.lang.Integer historyId){this.historyId = historyId;}
	
			
	// 属性：是否查看 （shiFouCK）
	private java.lang.Boolean shiFouCK = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouCK(){return this.shiFouCK ;}
	public void setShiFouCK(java.lang.Boolean shiFouCK){this.shiFouCK = shiFouCK;}
	
	// 集合：上级集合 （sjs）
	private java.util.Set<LiuChengJSSJ> sjs = new java.util.TreeSet<LiuChengJSSJ>();
	public java.util.Set<LiuChengJSSJ> getSjs(){return this.sjs ;}
	public void setSjs(java.util.Set<LiuChengJSSJ> sjs){this.sjs = sjs;}
	public void addTosjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSSJ subObj = (LiuChengJSSJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getSjs().add(subObj);
	}
	public void removeFromsjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSSJ subObj = (LiuChengJSSJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getSjs().remove(subObj);
	}
	public Object getsjsById(Object id){
		java.util.Iterator<LiuChengJSSJ> ds = this.getSjs().iterator();
		LiuChengJSSJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSSJDM()!=null && d.getLiuChengJSSJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public LiuChengJSSJ newsjsByParent(LiuChengJS parent) throws Exception{
		LiuChengJSSJ subObj = new LiuChengJSSJ();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
	// 集合：下级集合 （xjs）
	private java.util.Set<LiuChengJSXJ> xjs = new java.util.TreeSet<LiuChengJSXJ>();
	public java.util.Set<LiuChengJSXJ> getXjs(){return this.xjs ;}
	public void setXjs(java.util.Set<LiuChengJSXJ> xjs){this.xjs = xjs;}
	public void addToxjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSXJ subObj = (LiuChengJSXJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getXjs().add(subObj);
	}
	public void removeFromxjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSXJ subObj = (LiuChengJSXJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getXjs().remove(subObj);
	}
	public Object getxjsById(Object id){
		java.util.Iterator<LiuChengJSXJ> ds = this.getXjs().iterator();
		LiuChengJSXJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSXJDM()!=null && d.getLiuChengJSXJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public LiuChengJSXJ newxjsByParent(LiuChengJS parent) throws Exception{
		LiuChengJSXJ subObj = new LiuChengJSXJ();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
			
	// 属性：是否过度节点 （shiFouGDJD）
	private java.lang.Boolean shiFouGDJD = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouGDJD(){return this.shiFouGDJD ;}
	public void setShiFouGDJD(java.lang.Boolean shiFouGDJD){this.shiFouGDJD = shiFouGDJD;}
	
			
	// 属性：创建日期 （chuangJianRQ）
	private java.util.Date chuangJianRQ = null;
	public java.util.Date getChuangJianRQ(){return this.chuangJianRQ ;}
	public void setChuangJianRQ(java.util.Date chuangJianRQ){this.chuangJianRQ = chuangJianRQ;}
	
			
	// 属性：是否删除 （shiFouSC）
	private java.lang.Boolean shiFouSC = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSC(){return this.shiFouSC ;}
	public void setShiFouSC(java.lang.Boolean shiFouSC){this.shiFouSC = shiFouSC;}
	
	// 集合：平级集合 （pjs）
	private java.util.Set<LiuChengJSPJ> pjs = new java.util.TreeSet<LiuChengJSPJ>();
	public java.util.Set<LiuChengJSPJ> getPjs(){return this.pjs ;}
	public void setPjs(java.util.Set<LiuChengJSPJ> pjs){this.pjs = pjs;}
	public void addTopjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSPJ subObj = (LiuChengJSPJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getPjs().add(subObj);
	}
	public void removeFrompjs(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSPJ subObj = (LiuChengJSPJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getPjs().remove(subObj);
	}
	public Object getpjsById(Object id){
		java.util.Iterator<LiuChengJSPJ> ds = this.getPjs().iterator();
		LiuChengJSPJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSPJDM()!=null && d.getLiuChengJSPJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public LiuChengJSPJ newpjsByParent(LiuChengJS parent) throws Exception{
		LiuChengJSPJ subObj = new LiuChengJSPJ();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
			
	// 属性：操作信息 （caoZuoXX）
	private java.lang.String caoZuoXX = null;
	public java.lang.String getCaoZuoXX(){return this.caoZuoXX ;}
	public void setCaoZuoXX(java.lang.String caoZuoXX){this.caoZuoXX = caoZuoXX;}
	
	// 集合：变量集合 （bls）
	private java.util.Set<LiuChengJSBL> bls = new java.util.TreeSet<LiuChengJSBL>();
	public java.util.Set<LiuChengJSBL> getBls(){return this.bls ;}
	public void setBls(java.util.Set<LiuChengJSBL> bls){this.bls = bls;}
	public void addTobls(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSBL subObj = (LiuChengJSBL)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getBls().add(subObj);
	}
	public void removeFrombls(Object parent,Object detail){
		LiuChengJS mainObj = (LiuChengJS)parent;
		LiuChengJSBL subObj = (LiuChengJSBL)detail;
		subObj.setLiuChengJS(null);
		mainObj.getBls().remove(subObj);
	}
	public Object getblsById(Object id){
		java.util.Iterator<LiuChengJSBL> ds = this.getBls().iterator();
		LiuChengJSBL d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSBLDM()!=null && d.getLiuChengJSBLDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public LiuChengJSBL newblsByParent(LiuChengJS parent) throws Exception{
		LiuChengJSBL subObj = new LiuChengJSBL();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
	// 对象：用户 （wanChengYH）
	private YongHu wanChengYH;
	public YongHu getWanChengYH(){return this.wanChengYH ;}
	public void setWanChengYH(YongHu wanChengYH){this.wanChengYH = wanChengYH;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof LiuChengJS)) return false;
		else {
			LiuChengJS entity = (LiuChengJS) obj;
			if (null == this.getLiuChengJSDM() || null == entity.getLiuChengJSDM()) return false;
			else return (this.getLiuChengJSDM().equals(entity.getLiuChengJSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLiuChengJSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLiuChengJSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.liuChengJSDM+"";
	}

	public Integer pkValue() {
		return this.liuChengJSDM;
	}

	public String pkName() {
		return "liuChengJSDM";
	}

	public void pkNull() {
		this.liuChengJSDM = null;;
	}
	
	public int compareTo(Object o) {
		LiuChengJS obj = (LiuChengJS)o;
		if(this.getLiuChengJSDM()==null){
			return 1;
		}
		return this.getLiuChengJSDM().compareTo(obj.getLiuChengJSDM());
	}
	
	public LiuChengJS clone(){
		LiuChengJS liuChengJS = new LiuChengJS();
		
		liuChengJS.setCaoZuoDH(caoZuoDH);
		liuChengJS.setCaoZuoMC(caoZuoMC);
		liuChengJS.setCaoZuoRen(caoZuoRen);
		liuChengJS.setWanChengRQ(wanChengRQ);
		liuChengJS.setGongNengObjId(gongNengObjId);
		liuChengJS.setGongNengDH(gongNengDH);
		liuChengJS.setShiFouCL(shiFouCL);
		liuChengJS.setShiFouWC(shiFouWC);
		liuChengJS.setHistoryId(historyId);
		liuChengJS.setShiFouCK(shiFouCK);
		for(LiuChengJSSJ subObj:this.getSjs()){
			liuChengJS.addTosjs(liuChengJS, subObj.clone());
		}
		for(LiuChengJSXJ subObj:this.getXjs()){
			liuChengJS.addToxjs(liuChengJS, subObj.clone());
		}
		liuChengJS.setShiFouGDJD(shiFouGDJD);
		liuChengJS.setChuangJianRQ(chuangJianRQ);
		liuChengJS.setShiFouSC(shiFouSC);
		for(LiuChengJSPJ subObj:this.getPjs()){
			liuChengJS.addTopjs(liuChengJS, subObj.clone());
		}
		liuChengJS.setCaoZuoXX(caoZuoXX);
		for(LiuChengJSBL subObj:this.getBls()){
			liuChengJS.addTobls(liuChengJS, subObj.clone());
		}
		liuChengJS.setWanChengYH(wanChengYH);
		
		return liuChengJS;
	}
	
	
	
	
	
	
}