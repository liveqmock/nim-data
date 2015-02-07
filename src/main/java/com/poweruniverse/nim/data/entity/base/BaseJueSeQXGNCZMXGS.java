package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.JueSeQXGNCZMX;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZMXGS;
/*
* 实体类：角色权限功能操作明细公式
*/
public abstract class BaseJueSeQXGNCZMXGS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseJueSeQXGNCZMXGS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseJueSeQXGNCZMXGS (java.lang.Integer id) {
		this.setJueSeQXMXGSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：jueSeQXMXGSDM
	private java.lang.Integer jueSeQXMXGSDM = null;
	public java.lang.Integer getJueSeQXMXGSDM(){return this.jueSeQXMXGSDM ;}
	public void setJueSeQXMXGSDM(java.lang.Integer jueSeQXMXGSDM){this.jueSeQXMXGSDM = jueSeQXMXGSDM;}

			
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
	
	// 对象：角色权限功能操作明细 （jueSeQXMX）
	private JueSeQXGNCZMX jueSeQXMX;
	public JueSeQXGNCZMX getJueSeQXMX(){return this.jueSeQXMX ;}
	public void setJueSeQXMX(JueSeQXGNCZMX jueSeQXMX){this.jueSeQXMX = jueSeQXMX;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof JueSeQXGNCZMXGS)) return false;
		else {
			JueSeQXGNCZMXGS entity = (JueSeQXGNCZMXGS) obj;
			if (null == this.getJueSeQXMXGSDM() || null == entity.getJueSeQXMXGSDM()) return false;
			else return (this.getJueSeQXMXGSDM().equals(entity.getJueSeQXMXGSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getJueSeQXMXGSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getJueSeQXMXGSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.jueSeQXMXGSDM+"";
	}

	public Integer pkValue() {
		return this.jueSeQXMXGSDM;
	}

	public String pkName() {
		return "jueSeQXMXGSDM";
	}

	public void pkNull() {
		this.jueSeQXMXGSDM = null;;
	}
	
	public int compareTo(Object o) {
		JueSeQXGNCZMXGS obj = (JueSeQXGNCZMXGS)o;
		if(this.getJueSeQXMXGSDM()==null){
			return 1;
		}
		return this.getJueSeQXMXGSDM().compareTo(obj.getJueSeQXMXGSDM());
	}
	
	public JueSeQXGNCZMXGS clone(){
		JueSeQXGNCZMXGS jueSeQXGNCZMXGS = new JueSeQXGNCZMXGS();
		
		jueSeQXGNCZMXGS.setCaoZuoQXXS(caoZuoQXXS);
		jueSeQXGNCZMXGS.setCaoZuoQXGS(caoZuoQXGS);
		jueSeQXGNCZMXGS.setZiDuanMS(ziDuanMS);
		jueSeQXGNCZMXGS.setZiDuanSX(ziDuanSX);
		jueSeQXGNCZMXGS.setZiDuanCZF(ziDuanCZF);
		jueSeQXGNCZMXGS.setZiDuanQZ(ziDuanQZ);
		jueSeQXGNCZMXGS.setJueSeQXMX(jueSeQXMX);
		
		return jueSeQXGNCZMXGS;
	}
	
	
	
	
	
	
}