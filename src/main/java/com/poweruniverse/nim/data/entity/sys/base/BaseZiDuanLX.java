package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：字段类型
*/
@Version("2015-03-16 11:15:59")
public abstract class BaseZiDuanLX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseZiDuanLX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseZiDuanLX (java.lang.Integer id) {
		this.setZiDuanLXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：ziDuanLXDM
	private java.lang.Integer ziDuanLXDM = null;
	public java.lang.Integer getZiDuanLXDM(){return this.ziDuanLXDM ;}
	public void setZiDuanLXDM(java.lang.Integer ziDuanLXDM){this.ziDuanLXDM = ziDuanLXDM;}

			
	// 属性：字段类型名称 （ziDuanLXMC）
	private java.lang.String ziDuanLXMC = null;
	public java.lang.String getZiDuanLXMC(){return this.ziDuanLXMC ;}
	public void setZiDuanLXMC(java.lang.String ziDuanLXMC){this.ziDuanLXMC = ziDuanLXMC;}
	
			
	// 属性：字段类型代号 （ziDuanLXDH）
	private java.lang.String ziDuanLXDH = null;
	public java.lang.String getZiDuanLXDH(){return this.ziDuanLXDH ;}
	public void setZiDuanLXDH(java.lang.String ziDuanLXDH){this.ziDuanLXDH = ziDuanLXDH;}
	
			
	// 属性：oracleType （oracleType）
	private java.lang.String oracleType = null;
	public java.lang.String getOracleType(){return this.oracleType ;}
	public void setOracleType(java.lang.String oracleType){this.oracleType = oracleType;}
	
			
	// 属性：字段宽度 （ziDuanKD）
	private java.lang.Integer ziDuanKD = new java.lang.Integer(0);
	public java.lang.Integer getZiDuanKD(){return this.ziDuanKD ;}
	public void setZiDuanKD(java.lang.Integer ziDuanKD){this.ziDuanKD = ziDuanKD;}
	
			
	// 属性：字段精度 （ziDuanJD）
	private java.lang.Integer ziDuanJD = new java.lang.Integer(0);
	public java.lang.Integer getZiDuanJD(){return this.ziDuanJD ;}
	public void setZiDuanJD(java.lang.Integer ziDuanJD){this.ziDuanJD = ziDuanJD;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.ZiDuanLX)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.ZiDuanLX entity = (com.poweruniverse.nim.data.entity.sys.ZiDuanLX) obj;
			if (null == this.getZiDuanLXDM() || null == entity.getZiDuanLXDM()) return false;
			else return (this.getZiDuanLXDM().equals(entity.getZiDuanLXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getZiDuanLXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getZiDuanLXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.ziDuanLXMC+"";
	}

	public Integer pkValue() {
		return this.ziDuanLXDM;
	}

	public String pkName() {
		return "ziDuanLXDM";
	}

	public void pkNull() {
		this.ziDuanLXDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.ZiDuanLX obj = (com.poweruniverse.nim.data.entity.sys.ZiDuanLX)o;
		if(this.getZiDuanLXDM()==null){
			return 1;
		}
		return this.getZiDuanLXDM().compareTo(obj.getZiDuanLXDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.ZiDuanLX clone(){
		com.poweruniverse.nim.data.entity.sys.ZiDuanLX ziDuanLX = new com.poweruniverse.nim.data.entity.sys.ZiDuanLX();
		
		ziDuanLX.setZiDuanLXMC(ziDuanLXMC);
		ziDuanLX.setZiDuanLXDH(ziDuanLXDH);
		ziDuanLX.setOracleType(oracleType);
		ziDuanLX.setZiDuanKD(ziDuanKD);
		ziDuanLX.setZiDuanJD(ziDuanJD);
		
		return ziDuanLX;
	}
	
	
	
	
	
	
}