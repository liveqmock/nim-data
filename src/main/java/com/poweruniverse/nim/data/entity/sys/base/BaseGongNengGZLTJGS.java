package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：功能工作流条件公式
*/
@Version("2015-03-16 11:15:59")
public abstract class BaseGongNengGZLTJGS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseGongNengGZLTJGS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGongNengGZLTJGS (java.lang.Integer id) {
		this.setGongNengGZLTJGSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
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
	
	// 对象：功能工作流条件 （gongNengGZLTJ）
	private com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ gongNengGZLTJ;
	public com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ getGongNengGZLTJ(){return this.gongNengGZLTJ ;}
	public void setGongNengGZLTJ(com.poweruniverse.nim.data.entity.sys.GongNengGZLTJ gongNengGZLTJ){this.gongNengGZLTJ = gongNengGZLTJ;}

	// 主键：gongNengGZLTJGSDM
	private java.lang.Integer gongNengGZLTJGSDM = null;
	public java.lang.Integer getGongNengGZLTJGSDM(){return this.gongNengGZLTJGSDM ;}
	public void setGongNengGZLTJGSDM(java.lang.Integer gongNengGZLTJGSDM){this.gongNengGZLTJGSDM = gongNengGZLTJGSDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS entity = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS) obj;
			if (null == this.getGongNengGZLTJGSDM() || null == entity.getGongNengGZLTJGSDM()) return false;
			else return (this.getGongNengGZLTJGSDM().equals(entity.getGongNengGZLTJGSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGongNengGZLTJGSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGongNengGZLTJGSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.gongNengGZLTJGSDM+"";
	}

	public Integer pkValue() {
		return this.gongNengGZLTJGSDM;
	}

	public String pkName() {
		return "gongNengGZLTJGSDM";
	}

	public void pkNull() {
		this.gongNengGZLTJGSDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS obj = (com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS)o;
		if(this.getGongNengGZLTJGSDM()==null){
			return 1;
		}
		return this.getGongNengGZLTJGSDM().compareTo(obj.getGongNengGZLTJGSDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS clone(){
		com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS gongNengGZLTJGS = new com.poweruniverse.nim.data.entity.sys.GongNengGZLTJGS();
		
		gongNengGZLTJGS.setZiDuanSX(ziDuanSX);
		gongNengGZLTJGS.setZiDuanCZF(ziDuanCZF);
		gongNengGZLTJGS.setZiDuanQZ(ziDuanQZ);
		gongNengGZLTJGS.setCaoZuoQXXS(caoZuoQXXS);
		gongNengGZLTJGS.setCaoZuoQXGS(caoZuoQXGS);
		gongNengGZLTJGS.setZiDuanMS(ziDuanMS);
		gongNengGZLTJGS.setGongNengGZLTJ(gongNengGZLTJ);
		
		return gongNengGZLTJGS;
	}
	
	
	
	
	
	
}