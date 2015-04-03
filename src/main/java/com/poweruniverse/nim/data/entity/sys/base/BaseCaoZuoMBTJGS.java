package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：操作模板条件公式
*/
@Version("2015-04-04 02:05:43")
public abstract class BaseCaoZuoMBTJGS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseCaoZuoMBTJGS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCaoZuoMBTJGS (java.lang.Integer id) {
		this.setCaoZuoMBTJGSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：字段描述 （ziDuanMS）
	private java.lang.String ziDuanMS = null;
	public java.lang.String getZiDuanMS(){return this.ziDuanMS ;}
	public void setZiDuanMS(java.lang.String ziDuanMS){this.ziDuanMS = ziDuanMS;}
	
			
	// 属性：授权结果公式 （caoZuoQXGS）
	private java.lang.String caoZuoQXGS = null;
	public java.lang.String getCaoZuoQXGS(){return this.caoZuoQXGS ;}
	public void setCaoZuoQXGS(java.lang.String caoZuoQXGS){this.caoZuoQXGS = caoZuoQXGS;}
	
			
	// 属性：授权结果显示 （caoZuoQXXS）
	private java.lang.String caoZuoQXXS = null;
	public java.lang.String getCaoZuoQXXS(){return this.caoZuoQXXS ;}
	public void setCaoZuoQXXS(java.lang.String caoZuoQXXS){this.caoZuoQXXS = caoZuoQXXS;}
	
			
	// 属性：操作值 （ziDuanQZ）
	private java.lang.String ziDuanQZ = null;
	public java.lang.String getZiDuanQZ(){return this.ziDuanQZ ;}
	public void setZiDuanQZ(java.lang.String ziDuanQZ){this.ziDuanQZ = ziDuanQZ;}
	
			
	// 属性：操作符 （ziDuanCZF）
	private java.lang.String ziDuanCZF = null;
	public java.lang.String getZiDuanCZF(){return this.ziDuanCZF ;}
	public void setZiDuanCZF(java.lang.String ziDuanCZF){this.ziDuanCZF = ziDuanCZF;}
	
			
	// 属性：最后字段属性 （ziDuanSX）
	private java.lang.String ziDuanSX = null;
	public java.lang.String getZiDuanSX(){return this.ziDuanSX ;}
	public void setZiDuanSX(java.lang.String ziDuanSX){this.ziDuanSX = ziDuanSX;}
	
	// 对象：操作模板条件明细 （caoZuoMBTJMX）
	private com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX caoZuoMBTJMX;
	public com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX getCaoZuoMBTJMX(){return this.caoZuoMBTJMX ;}
	public void setCaoZuoMBTJMX(com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJMX caoZuoMBTJMX){this.caoZuoMBTJMX = caoZuoMBTJMX;}

	// 主键：caoZuoMBTJGSDM
	private java.lang.Integer caoZuoMBTJGSDM = null;
	public java.lang.Integer getCaoZuoMBTJGSDM(){return this.caoZuoMBTJGSDM ;}
	public void setCaoZuoMBTJGSDM(java.lang.Integer caoZuoMBTJGSDM){this.caoZuoMBTJGSDM = caoZuoMBTJGSDM;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS entity = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS) obj;
			if (null == this.getCaoZuoMBTJGSDM() || null == entity.getCaoZuoMBTJGSDM()) return false;
			else return (this.getCaoZuoMBTJGSDM().equals(entity.getCaoZuoMBTJGSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCaoZuoMBTJGSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCaoZuoMBTJGSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.caoZuoMBTJGSDM+"";
	}

	public Integer pkValue() {
		return this.caoZuoMBTJGSDM;
	}

	public String pkName() {
		return "caoZuoMBTJGSDM";
	}

	public void pkNull() {
		this.caoZuoMBTJGSDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS obj = (com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS)o;
		if(this.getCaoZuoMBTJGSDM()==null){
			return 1;
		}
		return this.getCaoZuoMBTJGSDM().compareTo(obj.getCaoZuoMBTJGSDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS clone(){
		com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS caoZuoMBTJGS = new com.poweruniverse.nim.data.entity.sys.CaoZuoMBTJGS();
		
		caoZuoMBTJGS.setZiDuanMS(ziDuanMS);
		caoZuoMBTJGS.setCaoZuoQXGS(caoZuoQXGS);
		caoZuoMBTJGS.setCaoZuoQXXS(caoZuoQXXS);
		caoZuoMBTJGS.setZiDuanQZ(ziDuanQZ);
		caoZuoMBTJGS.setZiDuanCZF(ziDuanCZF);
		caoZuoMBTJGS.setZiDuanSX(ziDuanSX);
		caoZuoMBTJGS.setCaoZuoMBTJMX(caoZuoMBTJMX);
		
		return caoZuoMBTJGS;
	}
	
	
	
	
	
	
}