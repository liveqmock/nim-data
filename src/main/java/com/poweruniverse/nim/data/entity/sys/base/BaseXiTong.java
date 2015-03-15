package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：系统
*/
@Version("2015-03-08 11:15:59")
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
	
			
	// 属性：系统首页 （xiTongSY）
	private java.lang.String xiTongSY = null;
	public java.lang.String getXiTongSY(){return this.xiTongSY ;}
	public void setXiTongSY(java.lang.String xiTongSY){this.xiTongSY = xiTongSY;}
	
			
	// 属性：是否问题 （shiFouWT）
	private java.lang.Boolean shiFouWT = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouWT(){return this.shiFouWT ;}
	public void setShiFouWT(java.lang.Boolean shiFouWT){this.shiFouWT = shiFouWT;}
	
			
	// 属性：需要登录 （xuYaoDL）
	private java.lang.Boolean xuYaoDL = new java.lang.Boolean(false);
	public java.lang.Boolean getXuYaoDL(){return this.xuYaoDL ;}
	public void setXuYaoDL(java.lang.Boolean xuYaoDL){this.xuYaoDL = xuYaoDL;}
	
			
	// 属性：form类型 （formType）
	private java.lang.String formType = null;
	public java.lang.String getFormType(){return this.formType ;}
	public void setFormType(java.lang.String formType){this.formType = formType;}
	
			
	// 属性：是否历史 （shiFouLS）
	private java.lang.Boolean shiFouLS = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouLS(){return this.shiFouLS ;}
	public void setShiFouLS(java.lang.Boolean shiFouLS){this.shiFouLS = shiFouLS;}
	
			
	// 属性：登录叶面 （dengLuYM）
	private java.lang.String dengLuYM = null;
	public java.lang.String getDengLuYM(){return this.dengLuYM ;}
	public void setDengLuYM(java.lang.String dengLuYM){this.dengLuYM = dengLuYM;}
	
			
	// 属性：后台页面 （houTaiYM）
	private java.lang.String houTaiYM = null;
	public java.lang.String getHouTaiYM(){return this.houTaiYM ;}
	public void setHouTaiYM(java.lang.String houTaiYM){this.houTaiYM = houTaiYM;}
	
			
	// 属性：是否使用验证码 （shiFouSYYZM）
	private java.lang.Boolean shiFouSYYZM = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSYYZM(){return this.shiFouSYYZM ;}
	public void setShiFouSYYZM(java.lang.Boolean shiFouSYYZM){this.shiFouSYYZM = shiFouSYYZM;}
	
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
		xiTong.setXiTongSY(xiTongSY);
		xiTong.setShiFouWT(shiFouWT);
		xiTong.setXuYaoDL(xuYaoDL);
		xiTong.setFormType(formType);
		xiTong.setShiFouLS(shiFouLS);
		xiTong.setDengLuYM(dengLuYM);
		xiTong.setHouTaiYM(houTaiYM);
		xiTong.setShiFouSYYZM(shiFouSYYZM);
		
		return xiTong;
	}
	
	
	
	
	
	
}