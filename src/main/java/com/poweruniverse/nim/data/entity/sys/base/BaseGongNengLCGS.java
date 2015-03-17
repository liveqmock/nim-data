package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能流程公式
*/
@Version("2015-03-16 11:15:59")
public abstract class BaseGongNengLCGS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengLCGS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengLCGS (java.lang.Integer id) {
		this.setGongNengLCGSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：gongNengLCGSDM
	private java.lang.Integer gongNengLCGSDM = null;
	public java.lang.Integer getGongNengLCGSDM(){return this.gongNengLCGSDM ;}
	public void setGongNengLCGSDM(java.lang.Integer gongNengLCGSDM){this.gongNengLCGSDM = gongNengLCGSDM;}

	// 对象：功能流程明细 （gongNengLCMX）
	private com.poweruniverse.nim.data.entity.sys.GongNengLCMX gongNengLCMX;
	public com.poweruniverse.nim.data.entity.sys.GongNengLCMX getGongNengLCMX(){return this.gongNengLCMX ;}
	public void setGongNengLCMX(com.poweruniverse.nim.data.entity.sys.GongNengLCMX gongNengLCMX){this.gongNengLCMX = gongNengLCMX;}

			
	// 属性：最后字段属性 （ziDuanSX）
	private java.lang.String ziDuanSX = null;
	public java.lang.String getZiDuanSX(){return this.ziDuanSX ;}
	public void setZiDuanSX(java.lang.String ziDuanSX){this.ziDuanSX = ziDuanSX;}
	
			
	// 属性：操作符 （ziDuanCZF）
	private java.lang.String ziDuanCZF = null;
	public java.lang.String getZiDuanCZF(){return this.ziDuanCZF ;}
	public void setZiDuanCZF(java.lang.String ziDuanCZF){this.ziDuanCZF = ziDuanCZF;}
	
			
	// 属性：操作值 （ziDuanQZ）
	private java.lang.String ziDuanQZ = null;
	public java.lang.String getZiDuanQZ(){return this.ziDuanQZ ;}
	public void setZiDuanQZ(java.lang.String ziDuanQZ){this.ziDuanQZ = ziDuanQZ;}
	
			
	// 属性：授权结果显示 （caoZuoQXXS）
	private java.lang.String caoZuoQXXS = null;
	public java.lang.String getCaoZuoQXXS(){return this.caoZuoQXXS ;}
	public void setCaoZuoQXXS(java.lang.String caoZuoQXXS){this.caoZuoQXXS = caoZuoQXXS;}
	
			
	// 属性：授权结果公式 （caoZuoQXGS）
	private java.lang.String caoZuoQXGS = null;
	public java.lang.String getCaoZuoQXGS(){return this.caoZuoQXGS ;}
	public void setCaoZuoQXGS(java.lang.String caoZuoQXGS){this.caoZuoQXGS = caoZuoQXGS;}
	
			
	// 属性：字段描述 （ziDuanMS）
	private java.lang.String ziDuanMS = null;
	public java.lang.String getZiDuanMS(){return this.ziDuanMS ;}
	public void setZiDuanMS(java.lang.String ziDuanMS){this.ziDuanMS = ziDuanMS;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengLCGS)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengLCGS entity = (com.poweruniverse.nim.data.entity.sys.GongNengLCGS) obj;
			if (null == this.getGongNengLCGSDM() || null == entity.getGongNengLCGSDM()) return false;
			else return (this.getGongNengLCGSDM().equals(entity.getGongNengLCGSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengLCGSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengLCGSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoQXXS+"";
	}

	public Integer pkValue() {
		return this.gongNengLCGSDM;
	}

	public String pkName() {
		return "gongNengLCGSDM";
	}

	public void pkNull() {
		this.gongNengLCGSDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.GongNengLCGS obj = (com.poweruniverse.nim.data.entity.sys.GongNengLCGS)o;
		if(this.getGongNengLCGSDM()==null){
			return 1;
		}
		return this.getGongNengLCGSDM().compareTo(obj.getGongNengLCGSDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengLCGS clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengLCGS gongNengLCGS = new com.poweruniverse.nim.data.entity.sys.GongNengLCGS();
		
		gongNengLCGS.setGongNengLCMX(gongNengLCMX);
		gongNengLCGS.setZiDuanSX(ziDuanSX);
		gongNengLCGS.setZiDuanCZF(ziDuanCZF);
		gongNengLCGS.setZiDuanQZ(ziDuanQZ);
		gongNengLCGS.setCaoZuoQXXS(caoZuoQXXS);
		gongNengLCGS.setCaoZuoQXGS(caoZuoQXGS);
		gongNengLCGS.setZiDuanMS(ziDuanMS);
		
		return gongNengLCGS;
	}
	
	
	
	
	
	
}