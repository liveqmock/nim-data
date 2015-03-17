package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：流程检视
*/
@Version("2015-03-16 11:15:59")
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
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ> sjs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ> getSjs(){return this.sjs ;}
	public void setSjs(java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ> sjs){this.sjs = sjs;}
	public void addTosjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getSjs().add(subObj);
	}
	public void removeFromsjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getSjs().remove(subObj);
	}
	public Object getsjsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ> ds = this.getSjs().iterator();
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSSJDM()!=null && d.getLiuChengJSSJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ newsjsByParent(com.poweruniverse.nim.data.entity.sys.LiuChengJS parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ subObj = new com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
	// 集合：下级集合 （xjs）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ> xjs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ> getXjs(){return this.xjs ;}
	public void setXjs(java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ> xjs){this.xjs = xjs;}
	public void addToxjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getXjs().add(subObj);
	}
	public void removeFromxjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getXjs().remove(subObj);
	}
	public Object getxjsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ> ds = this.getXjs().iterator();
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSXJDM()!=null && d.getLiuChengJSXJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ newxjsByParent(com.poweruniverse.nim.data.entity.sys.LiuChengJS parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ subObj = new com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ();
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
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ> pjs = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ> getPjs(){return this.pjs ;}
	public void setPjs(java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ> pjs){this.pjs = pjs;}
	public void addTopjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getPjs().add(subObj);
	}
	public void removeFrompjs(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ)detail;
		subObj.setLiuChengJS(null);
		mainObj.getPjs().remove(subObj);
	}
	public Object getpjsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ> ds = this.getPjs().iterator();
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSPJDM()!=null && d.getLiuChengJSPJDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ newpjsByParent(com.poweruniverse.nim.data.entity.sys.LiuChengJS parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ subObj = new com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
			
	// 属性：操作信息 （caoZuoXX）
	private java.lang.String caoZuoXX = null;
	public java.lang.String getCaoZuoXX(){return this.caoZuoXX ;}
	public void setCaoZuoXX(java.lang.String caoZuoXX){this.caoZuoXX = caoZuoXX;}
	
	// 集合：流程变量集合 （bls）
	private java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSBL> bls = new java.util.TreeSet<com.poweruniverse.nim.data.entity.sys.LiuChengJSBL>();
	public java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSBL> getBls(){return this.bls ;}
	public void setBls(java.util.Set<com.poweruniverse.nim.data.entity.sys.LiuChengJSBL> bls){this.bls = bls;}
	public void addTobls(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSBL)detail;
		subObj.setLiuChengJS(mainObj);
		mainObj.getBls().add(subObj);
	}
	public void removeFrombls(Object parent,Object detail){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS mainObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)parent;
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL subObj = (com.poweruniverse.nim.data.entity.sys.LiuChengJSBL)detail;
		subObj.setLiuChengJS(null);
		mainObj.getBls().remove(subObj);
	}
	public Object getblsById(Object id){
		java.util.Iterator<com.poweruniverse.nim.data.entity.sys.LiuChengJSBL> ds = this.getBls().iterator();
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.getLiuChengJSBLDM()!=null && d.getLiuChengJSBLDM().equals(id)){
				return d;
			}
		}
		return null;
	}
	public com.poweruniverse.nim.data.entity.sys.LiuChengJSBL newblsByParent(com.poweruniverse.nim.data.entity.sys.LiuChengJS parent) throws Exception{
		com.poweruniverse.nim.data.entity.sys.LiuChengJSBL subObj = new com.poweruniverse.nim.data.entity.sys.LiuChengJSBL();
		//
		subObj.setLiuChengJS(parent);
		//
		return subObj;
	}
	
	// 对象：用户 （wanChengYH）
	private com.poweruniverse.nim.data.entity.sys.YongHu wanChengYH;
	public com.poweruniverse.nim.data.entity.sys.YongHu getWanChengYH(){return this.wanChengYH ;}
	public void setWanChengYH(com.poweruniverse.nim.data.entity.sys.YongHu wanChengYH){this.wanChengYH = wanChengYH;}

			
	// 属性：历史记录信息 （historyInfo）
	private java.lang.String historyInfo = null;
	public java.lang.String getHistoryInfo(){return this.historyInfo ;}
	public void setHistoryInfo(java.lang.String historyInfo){this.historyInfo = historyInfo;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.LiuChengJS)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.LiuChengJS entity = (com.poweruniverse.nim.data.entity.sys.LiuChengJS) obj;
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
		com.poweruniverse.nim.data.entity.sys.LiuChengJS obj = (com.poweruniverse.nim.data.entity.sys.LiuChengJS)o;
		if(this.getLiuChengJSDM()==null){
			return 1;
		}
		return this.getLiuChengJSDM().compareTo(obj.getLiuChengJSDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.LiuChengJS clone(){
		com.poweruniverse.nim.data.entity.sys.LiuChengJS liuChengJS = new com.poweruniverse.nim.data.entity.sys.LiuChengJS();
		
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
		for(com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ subObj:this.getSjs()){
			liuChengJS.addTosjs(liuChengJS, subObj.clone());
		}
		for(com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ subObj:this.getXjs()){
			liuChengJS.addToxjs(liuChengJS, subObj.clone());
		}
		liuChengJS.setShiFouGDJD(shiFouGDJD);
		liuChengJS.setChuangJianRQ(chuangJianRQ);
		liuChengJS.setShiFouSC(shiFouSC);
		for(com.poweruniverse.nim.data.entity.sys.LiuChengJSPJ subObj:this.getPjs()){
			liuChengJS.addTopjs(liuChengJS, subObj.clone());
		}
		liuChengJS.setCaoZuoXX(caoZuoXX);
		for(com.poweruniverse.nim.data.entity.sys.LiuChengJSBL subObj:this.getBls()){
			liuChengJS.addTobls(liuChengJS, subObj.clone());
		}
		liuChengJS.setWanChengYH(wanChengYH);
		liuChengJS.setHistoryInfo(historyInfo);
		
		return liuChengJS;
	}
	
	
	
	
	
	
}