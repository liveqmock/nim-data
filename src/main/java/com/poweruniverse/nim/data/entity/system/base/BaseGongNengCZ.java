package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：功能操作
*/
@Version("2015-03-08 11:29:25")
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
	private com.poweruniverse.nim.data.entity.system.GongNeng gongNeng;
	public com.poweruniverse.nim.data.entity.system.GongNeng getGongNeng(){return this.gongNeng ;}
	public void setGongNeng(com.poweruniverse.nim.data.entity.system.GongNeng gongNeng){this.gongNeng = gongNeng;}

	// 对象：操作类别 （caoZuoLB）
	private com.poweruniverse.nim.data.entity.system.CaoZuoLB caoZuoLB;
	public com.poweruniverse.nim.data.entity.system.CaoZuoLB getCaoZuoLB(){return this.caoZuoLB ;}
	public void setCaoZuoLB(com.poweruniverse.nim.data.entity.system.CaoZuoLB caoZuoLB){this.caoZuoLB = caoZuoLB;}

			
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
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.GongNengCZ)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.GongNengCZ entity = (com.poweruniverse.nim.data.entity.system.GongNengCZ) obj;
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
		com.poweruniverse.nim.data.entity.system.GongNengCZ obj = (com.poweruniverse.nim.data.entity.system.GongNengCZ)o;
		if(this.getGongNengCZDM()==null){
			return 1;
		}
		return this.getGongNengCZDM().compareTo(obj.getGongNengCZDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.GongNengCZ clone(){
		com.poweruniverse.nim.data.entity.system.GongNengCZ gongNengCZ = new com.poweruniverse.nim.data.entity.system.GongNengCZ();
		
		gongNengCZ.setGongNeng(gongNeng);
		gongNengCZ.setCaoZuoLB(caoZuoLB);
		gongNengCZ.setCaoZuoDH(caoZuoDH);
		gongNengCZ.setCaoZuoMC(caoZuoMC);
		gongNengCZ.setDuiXiangXG(duiXiangXG);
		gongNengCZ.setKeYiSQ(keYiSQ);
		gongNengCZ.setGongNengCZXH(gongNengCZXH);
		
		return gongNengCZ;
	}
	
	
	
	
	
	
}