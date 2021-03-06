package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：实体类类型
*/
@Version("2015-04-04 02:05:46")
public abstract class BaseShiTiLeiLX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseShiTiLeiLX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseShiTiLeiLX (java.lang.Integer id) {
		this.setShiTiLeiLXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：实体类类型名称 （shiTiLeiLXMC）
	private java.lang.String shiTiLeiLXMC = null;
	public java.lang.String getShiTiLeiLXMC(){return this.shiTiLeiLXMC ;}
	public void setShiTiLeiLXMC(java.lang.String shiTiLeiLXMC){this.shiTiLeiLXMC = shiTiLeiLXMC;}
	
	// 主键：shiTiLeiLXDM
	private java.lang.Integer shiTiLeiLXDM = null;
	public java.lang.Integer getShiTiLeiLXDM(){return this.shiTiLeiLXDM ;}
	public void setShiTiLeiLXDM(java.lang.Integer shiTiLeiLXDM){this.shiTiLeiLXDM = shiTiLeiLXDM;}

			
	// 属性：实体类类型代号 （shiTiLeiLXDH）
	private java.lang.String shiTiLeiLXDH = null;
	public java.lang.String getShiTiLeiLXDH(){return this.shiTiLeiLXDH ;}
	public void setShiTiLeiLXDH(java.lang.String shiTiLeiLXDH){this.shiTiLeiLXDH = shiTiLeiLXDH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX entity = (com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX) obj;
			if (null == this.getShiTiLeiLXDM() || null == entity.getShiTiLeiLXDM()) return false;
			else return (this.getShiTiLeiLXDM().equals(entity.getShiTiLeiLXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getShiTiLeiLXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getShiTiLeiLXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.shiTiLeiLXMC+"";
	}

	public Integer pkValue() {
		return this.shiTiLeiLXDM;
	}

	public String pkName() {
		return "shiTiLeiLXDM";
	}

	public void pkNull() {
		this.shiTiLeiLXDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX obj = (com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX)o;
		if(this.getShiTiLeiLXDM()==null){
			return 1;
		}
		return this.getShiTiLeiLXDM().compareTo(obj.getShiTiLeiLXDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX clone(){
		com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX shiTiLeiLX = new com.poweruniverse.nim.data.entity.sys.ShiTiLeiLX();
		
		shiTiLeiLX.setShiTiLeiLXMC(shiTiLeiLXMC);
		shiTiLeiLX.setShiTiLeiLXDH(shiTiLeiLXDH);
		
		return shiTiLeiLX;
	}
	
	
	
	
	
	
}