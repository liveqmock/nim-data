package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能操作
*/
@Version("2015-03-29 03:04:12")
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
	private com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng;
	public com.poweruniverse.nim.data.entity.sys.GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(com.poweruniverse.nim.data.entity.sys.GongNeng gongNeng){this.gongNeng = gongNeng;}

	// 对象：操作类别 （caoZuoLB）
	private com.poweruniverse.nim.data.entity.sys.CaoZuoLB caoZuoLB;
	public com.poweruniverse.nim.data.entity.sys.CaoZuoLB getCaoZuoLB(){return this.caoZuoLB ;}
	public void setCaoZuoLB(com.poweruniverse.nim.data.entity.sys.CaoZuoLB caoZuoLB){this.caoZuoLB = caoZuoLB;}

			
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
	
			
	// 属性：可以授权 （keYiSQ）
	private java.lang.Boolean keYiSQ = new java.lang.Boolean(false);
	public java.lang.Boolean getKeYiSQ(){return this.keYiSQ ;}
	public void setKeYiSQ(java.lang.Boolean keYiSQ){this.keYiSQ = keYiSQ;}
	
			
	// 属性：功能操作序号 （gongNengCZXH）
	private java.lang.Integer gongNengCZXH = new java.lang.Integer(0);
	public java.lang.Integer getGongNengCZXH(){return this.gongNengCZXH ;}
	public void setGongNengCZXH(java.lang.Integer gongNengCZXH){this.gongNengCZXH = gongNengCZXH;}
	
			
	// 属性：数据保存准备事件 （prepareAction）
	private java.lang.String prepareAction = null;
	public java.lang.String getPrepareAction(){return this.prepareAction ;}
	public void setPrepareAction(java.lang.String prepareAction){this.prepareAction = prepareAction;}
	
			
	// 属性：数据保存之前事件 （beforeAction）
	private java.lang.String beforeAction = null;
	public java.lang.String getBeforeAction(){return this.beforeAction ;}
	public void setBeforeAction(java.lang.String beforeAction){this.beforeAction = beforeAction;}
	
			
	// 属性：数据保存之后事件 （afterAction）
	private java.lang.String afterAction = null;
	public java.lang.String getAfterAction(){return this.afterAction ;}
	public void setAfterAction(java.lang.String afterAction){this.afterAction = afterAction;}
	
			
	// 属性：数据加载事件 （loadAction）
	private java.lang.String loadAction = null;
	public java.lang.String getLoadAction(){return this.loadAction ;}
	public void setLoadAction(java.lang.String loadAction){this.loadAction = loadAction;}
	
			
	// 属性：数据保存事件 （onAction）
	private java.lang.String onAction = null;
	public java.lang.String getOnAction(){return this.onAction ;}
	public void setOnAction(java.lang.String onAction){this.onAction = onAction;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengCZ)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengCZ entity = (com.poweruniverse.nim.data.entity.sys.GongNengCZ) obj;
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
		com.poweruniverse.nim.data.entity.sys.GongNengCZ obj = (com.poweruniverse.nim.data.entity.sys.GongNengCZ)o;
		if(this.getGongNengCZDM()==null){
			return 1;
		}
		return this.getGongNengCZDM().compareTo(obj.getGongNengCZDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengCZ clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengCZ gongNengCZ = new com.poweruniverse.nim.data.entity.sys.GongNengCZ();
		
		gongNengCZ.setGongNeng(gongNeng);
		gongNengCZ.setCaoZuoLB(caoZuoLB);
		gongNengCZ.setCaoZuoDH(caoZuoDH);
		gongNengCZ.setCaoZuoMC(caoZuoMC);
		gongNengCZ.setDuiXiangXG(duiXiangXG);
		gongNengCZ.setKeYiSQ(keYiSQ);
		gongNengCZ.setGongNengCZXH(gongNengCZXH);
		gongNengCZ.setPrepareAction(prepareAction);
		gongNengCZ.setBeforeAction(beforeAction);
		gongNengCZ.setAfterAction(afterAction);
		gongNengCZ.setLoadAction(loadAction);
		gongNengCZ.setOnAction(onAction);
		
		return gongNengCZ;
	}
	
	
	
	
	
	
}