package com.poweruniverse.nim.data.entity.system.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：职务
*/
@Version("2015-03-08 11:15:59")
public abstract class BaseZhiWu  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseZhiWu () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseZhiWu (java.lang.Integer id) {
		this.setZhiWuDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：zhiWuDM
	private java.lang.Integer zhiWuDM = null;
	public java.lang.Integer getZhiWuDM(){return this.zhiWuDM ;}
	public void setZhiWuDM(java.lang.Integer zhiWuDM){this.zhiWuDM = zhiWuDM;}

			
	// 属性：备注 （zhiWuBZ）
	private java.lang.String zhiWuBZ = null;
	public java.lang.String getZhiWuBZ(){return this.zhiWuBZ ;}
	public void setZhiWuBZ(java.lang.String zhiWuBZ){this.zhiWuBZ = zhiWuBZ;}
	
			
	// 属性：名称 （zhiWuMC）
	private java.lang.String zhiWuMC = null;
	public java.lang.String getZhiWuMC(){return this.zhiWuMC ;}
	public void setZhiWuMC(java.lang.String zhiWuMC){this.zhiWuMC = zhiWuMC;}
	
			
	// 属性：编号 （zhiWuBH）
	private java.lang.String zhiWuBH = null;
	public java.lang.String getZhiWuBH(){return this.zhiWuBH ;}
	public void setZhiWuBH(java.lang.String zhiWuBH){this.zhiWuBH = zhiWuBH;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.system.ZhiWu)) return false;
		else {
			com.poweruniverse.nim.data.entity.system.ZhiWu entity = (com.poweruniverse.nim.data.entity.system.ZhiWu) obj;
			if (null == this.getZhiWuDM() || null == entity.getZhiWuDM()) return false;
			else return (this.getZhiWuDM().equals(entity.getZhiWuDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getZhiWuDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getZhiWuDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.zhiWuMC+"";
	}

	public Integer pkValue() {
		return this.zhiWuDM;
	}

	public String pkName() {
		return "zhiWuDM";
	}

	public void pkNull() {
		this.zhiWuDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.system.ZhiWu obj = (com.poweruniverse.nim.data.entity.system.ZhiWu)o;
		if(this.getZhiWuDM()==null){
			return 1;
		}
		return this.getZhiWuDM().compareTo(obj.getZhiWuDM());
	}
	
	public com.poweruniverse.nim.data.entity.system.ZhiWu clone(){
		com.poweruniverse.nim.data.entity.system.ZhiWu zhiWu = new com.poweruniverse.nim.data.entity.system.ZhiWu();
		
		zhiWu.setZhiWuBZ(zhiWuBZ);
		zhiWu.setZhiWuMC(zhiWuMC);
		zhiWu.setZhiWuBH(zhiWuBH);
		
		return zhiWu;
	}
	
	
	
	
	
	
}