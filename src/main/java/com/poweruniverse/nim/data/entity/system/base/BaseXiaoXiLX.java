package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.system.XiaoXiLX;

/*
* 实体类：消息类型
*/
@Version("2015-01-20 19:30:36")
public abstract class BaseXiaoXiLX  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseXiaoXiLX () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseXiaoXiLX (java.lang.Integer id) {
		this.setXiaoXiLXDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：xiaoXiLXDM
	private java.lang.Integer xiaoXiLXDM = null;
	public java.lang.Integer getXiaoXiLXDM(){return this.xiaoXiLXDM ;}
	public void setXiaoXiLXDM(java.lang.Integer xiaoXiLXDM){this.xiaoXiLXDM = xiaoXiLXDM;}

			
	// 属性：消息类型名称 （xiaoXiLXMC）
	private java.lang.String xiaoXiLXMC = null;
	public java.lang.String getXiaoXiLXMC(){return this.xiaoXiLXMC ;}
	public void setXiaoXiLXMC(java.lang.String xiaoXiLXMC){this.xiaoXiLXMC = xiaoXiLXMC;}
	
			
	// 属性：消息类型代号 （xiaoXiLXDH）
	private java.lang.String xiaoXiLXDH = null;
	public java.lang.String getXiaoXiLXDH(){return this.xiaoXiLXDH ;}
	public void setXiaoXiLXDH(java.lang.String xiaoXiLXDH){this.xiaoXiLXDH = xiaoXiLXDH;}
	
			
	// 属性：消息发送实现类 （xiaoXiFSSXL）
	private java.lang.String xiaoXiFSSXL = null;
	public java.lang.String getXiaoXiFSSXL(){return this.xiaoXiFSSXL ;}
	public void setXiaoXiFSSXL(java.lang.String xiaoXiFSSXL){this.xiaoXiFSSXL = xiaoXiFSSXL;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof XiaoXiLX)) return false;
		else {
			XiaoXiLX entity = (XiaoXiLX) obj;
			if (null == this.getXiaoXiLXDM() || null == entity.getXiaoXiLXDM()) return false;
			else return (this.getXiaoXiLXDM().equals(entity.getXiaoXiLXDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getXiaoXiLXDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getXiaoXiLXDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.xiaoXiLXMC+"";
	}

	public Integer pkValue() {
		return this.xiaoXiLXDM;
	}

	public String pkName() {
		return "xiaoXiLXDM";
	}

	public void pkNull() {
		this.xiaoXiLXDM = null;;
	}
	
	public int compareTo(Object o) {
		XiaoXiLX obj = (XiaoXiLX)o;
		if(this.getXiaoXiLXDM()==null){
			return 1;
		}
		return this.getXiaoXiLXDM().compareTo(obj.getXiaoXiLXDM());
	}
	
	public XiaoXiLX clone(){
		XiaoXiLX xiaoXiLX = new XiaoXiLX();
		
		xiaoXiLX.setXiaoXiLXMC(xiaoXiLXMC);
		xiaoXiLX.setXiaoXiLXDH(xiaoXiLXDH);
		xiaoXiLX.setXiaoXiFSSXL(xiaoXiFSSXL);
		
		return xiaoXiLX;
	}
	
	
	
	
	
	
}