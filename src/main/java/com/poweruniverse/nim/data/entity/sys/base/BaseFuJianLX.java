package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：附件类型
*/
@Version("2015-04-04 02:05:43")
public abstract class BaseFuJianLX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseFuJianLX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFuJianLX (java.lang.Integer id) {
		this.setFuJianLXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：需要静态文件 （xuYaoJTFW）
	private java.lang.Boolean xuYaoJTFW = new java.lang.Boolean(false);
	public java.lang.Boolean getXuYaoJTFW(){return this.xuYaoJTFW ;}
	public void setXuYaoJTFW(java.lang.Boolean xuYaoJTFW){this.xuYaoJTFW = xuYaoJTFW;}
	
			
	// 属性：附件类型名称 （fuJianLXMC）
	private java.lang.String fuJianLXMC = null;
	public java.lang.String getFuJianLXMC(){return this.fuJianLXMC ;}
	public void setFuJianLXMC(java.lang.String fuJianLXMC){this.fuJianLXMC = fuJianLXMC;}
	
	// 主键：fuJianLXDM
	private java.lang.Integer fuJianLXDM = null;
	public java.lang.Integer getFuJianLXDM(){return this.fuJianLXDM ;}
	public void setFuJianLXDM(java.lang.Integer fuJianLXDM){this.fuJianLXDM = fuJianLXDM;}

			
	// 属性：附件类型代号 （fuJianLXDH）
	private java.lang.String fuJianLXDH = null;
	public java.lang.String getFuJianLXDH(){return this.fuJianLXDH ;}
	public void setFuJianLXDH(java.lang.String fuJianLXDH){this.fuJianLXDH = fuJianLXDH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.FuJianLX)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.FuJianLX entity = (com.poweruniverse.nim.data.entity.sys.FuJianLX) obj;
			if (null == this.getFuJianLXDM() || null == entity.getFuJianLXDM()) return false;
			else return (this.getFuJianLXDM().equals(entity.getFuJianLXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getFuJianLXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getFuJianLXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.fuJianLXMC+"";
	}

	public Integer pkValue() {
		return this.fuJianLXDM;
	}

	public String pkName() {
		return "fuJianLXDM";
	}

	public void pkNull() {
		this.fuJianLXDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.FuJianLX obj = (com.poweruniverse.nim.data.entity.sys.FuJianLX)o;
		if(this.getFuJianLXDM()==null){
			return 1;
		}
		return this.getFuJianLXDM().compareTo(obj.getFuJianLXDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.FuJianLX clone(){
		com.poweruniverse.nim.data.entity.sys.FuJianLX fuJianLX = new com.poweruniverse.nim.data.entity.sys.FuJianLX();
		
		fuJianLX.setXuYaoJTFW(xuYaoJTFW);
		fuJianLX.setFuJianLXMC(fuJianLXMC);
		fuJianLX.setFuJianLXDH(fuJianLXDH);
		
		return fuJianLX;
	}
	
	
	
	
	
	
}