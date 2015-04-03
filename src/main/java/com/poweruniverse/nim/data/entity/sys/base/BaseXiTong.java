package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：系统
*/
@Version("2015-04-04 02:05:46")
public abstract class BaseXiTong  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseXiTong () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseXiTong (java.lang.Integer id) {
		this.setXiTongDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：xiTongDM
	private java.lang.Integer xiTongDM = null;
	public java.lang.Integer getXiTongDM(){return this.xiTongDM ;}
	public void setXiTongDM(java.lang.Integer xiTongDM){this.xiTongDM = xiTongDM;}

			
	// 属性：系统名称 （xiTongMC）
	private java.lang.String xiTongMC = null;
	public java.lang.String getXiTongMC(){return this.xiTongMC ;}
	public void setXiTongMC(java.lang.String xiTongMC){this.xiTongMC = xiTongMC;}
	
			
	// 属性：系统代号 （xiTongDH）
	private java.lang.String xiTongDH = null;
	public java.lang.String getXiTongDH(){return this.xiTongDH ;}
	public void setXiTongDH(java.lang.String xiTongDH){this.xiTongDH = xiTongDH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.XiTong)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.XiTong entity = (com.poweruniverse.nim.data.entity.sys.XiTong) obj;
			if (null == this.getXiTongDM() || null == entity.getXiTongDM()) return false;
			else return (this.getXiTongDM().equals(entity.getXiTongDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getXiTongDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getXiTongDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.xiTongMC+"";
	}

	public Integer pkValue() {
		return this.xiTongDM;
	}

	public String pkName() {
		return "xiTongDM";
	}

	public void pkNull() {
		this.xiTongDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.XiTong obj = (com.poweruniverse.nim.data.entity.sys.XiTong)o;
		if(this.getXiTongDM()==null){
			return 1;
		}
		return this.getXiTongDM().compareTo(obj.getXiTongDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.XiTong clone(){
		com.poweruniverse.nim.data.entity.sys.XiTong xiTong = new com.poweruniverse.nim.data.entity.sys.XiTong();
		
		xiTong.setXiTongMC(xiTongMC);
		xiTong.setXiTongDH(xiTongDH);
		
		return xiTong;
	}
	
	
	
	
	
	
}