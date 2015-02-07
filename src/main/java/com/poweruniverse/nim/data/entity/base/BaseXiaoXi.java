package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.XiTong;
import com.poweruniverse.nim.data.entity.XiaoXi;
import com.poweruniverse.nim.data.entity.XiaoXiLX;

/*
* 实体类：消息
*/
@Version("2015-01-20 18:30:38")
public abstract class BaseXiaoXi  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseXiaoXi () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseXiaoXi (java.lang.Integer id) {
		this.setXiaoXiDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 主键：xiaoXiDM
	private java.lang.Integer xiaoXiDM = null;
	public java.lang.Integer getXiaoXiDM(){return this.xiaoXiDM ;}
	public void setXiaoXiDM(java.lang.Integer xiaoXiDM){this.xiaoXiDM = xiaoXiDM;}

			
	// 属性：消息标题 （xiaoXiBT）
	private java.lang.String xiaoXiBT = null;
	public java.lang.String getXiaoXiBT(){return this.xiaoXiBT ;}
	public void setXiaoXiBT(java.lang.String xiaoXiBT){this.xiaoXiBT = xiaoXiBT;}
	
			
	// 属性：消息内容 （xiaoXiNR）
	private java.lang.String xiaoXiNR = null;
	public java.lang.String getXiaoXiNR(){return this.xiaoXiNR ;}
	public void setXiaoXiNR(java.lang.String xiaoXiNR){this.xiaoXiNR = xiaoXiNR;}
	
	// 对象：系统 （xiTong）
	private XiTong xiTong;
	public XiTong getXiTong(){return this.xiTong ;}
	public void setXiTong(XiTong xiTong){this.xiTong = xiTong;}

	// 对象：消息类型 （xiaoXiLX）
	private XiaoXiLX xiaoXiLX;
	public XiaoXiLX getXiaoXiLX(){return this.xiaoXiLX ;}
	public void setXiaoXiLX(XiaoXiLX xiaoXiLX){this.xiaoXiLX = xiaoXiLX;}

			
	// 属性：创建人 （chuangJianRen）
	private java.lang.String chuangJianRen = null;
	public java.lang.String getChuangJianRen(){return this.chuangJianRen ;}
	public void setChuangJianRen(java.lang.String chuangJianRen){this.chuangJianRen = chuangJianRen;}
	
			
	// 属性：创建IP （chuangJianIP）
	private java.lang.String chuangJianIP = null;
	public java.lang.String getChuangJianIP(){return this.chuangJianIP ;}
	public void setChuangJianIP(java.lang.String chuangJianIP){this.chuangJianIP = chuangJianIP;}
	
			
	// 属性：创建时间 （chuangJianSJ）
	private java.util.Date chuangJianSJ = null;
	public java.util.Date getChuangJianSJ(){return this.chuangJianSJ ;}
	public void setChuangJianSJ(java.util.Date chuangJianSJ){this.chuangJianSJ = chuangJianSJ;}
	
			
	// 属性：接收人 （jieShouRen）
	private java.lang.String jieShouRen = null;
	public java.lang.String getJieShouRen(){return this.jieShouRen ;}
	public void setJieShouRen(java.lang.String jieShouRen){this.jieShouRen = jieShouRen;}
	
			
	// 属性：接收手机号码 （jieShouSJHM）
	private java.lang.String jieShouSJHM = null;
	public java.lang.String getJieShouSJHM(){return this.jieShouSJHM ;}
	public void setJieShouSJHM(java.lang.String jieShouSJHM){this.jieShouSJHM = jieShouSJHM;}
	
			
	// 属性：接收邮箱地址 （jieShouYXDZ）
	private java.lang.String jieShouYXDZ = null;
	public java.lang.String getJieShouYXDZ(){return this.jieShouYXDZ ;}
	public void setJieShouYXDZ(java.lang.String jieShouYXDZ){this.jieShouYXDZ = jieShouYXDZ;}
	
			
	// 属性：发送状态 （faSongZT）
	private java.lang.Boolean faSongZT = new java.lang.Boolean(false);
	public java.lang.Boolean getFaSongZT(){return this.faSongZT ;}
	public void setFaSongZT(java.lang.Boolean faSongZT){this.faSongZT = faSongZT;}
	
			
	// 属性：发送错误信息 （faSongCWXX）
	private java.lang.String faSongCWXX = null;
	public java.lang.String getFaSongCWXX(){return this.faSongCWXX ;}
	public void setFaSongCWXX(java.lang.String faSongCWXX){this.faSongCWXX = faSongCWXX;}
	
			
	// 属性：发送次数 （faSongCS）
	private java.lang.Integer faSongCS = new java.lang.Integer(0);
	public java.lang.Integer getFaSongCS(){return this.faSongCS ;}
	public void setFaSongCS(java.lang.Integer faSongCS){this.faSongCS = faSongCS;}
	
			
	// 属性：发送时间 （faSongSJ）
	private java.util.Date faSongSJ = null;
	public java.util.Date getFaSongSJ(){return this.faSongSJ ;}
	public void setFaSongSJ(java.util.Date faSongSJ){this.faSongSJ = faSongSJ;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof XiaoXi)) return false;
		else {
			XiaoXi entity = (XiaoXi) obj;
			if (null == this.getXiaoXiDM() || null == entity.getXiaoXiDM()) return false;
			else return (this.getXiaoXiDM().equals(entity.getXiaoXiDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getXiaoXiDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getXiaoXiDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.xiaoXiBT+"";
	}

	public Integer pkValue() {
		return this.xiaoXiDM;
	}

	public String pkName() {
		return "xiaoXiDM";
	}

	public void pkNull() {
		this.xiaoXiDM = null;;
	}
	
	public int compareTo(Object o) {
		XiaoXi obj = (XiaoXi)o;
		if(this.getXiaoXiDM()==null){
			return 1;
		}
		return this.getXiaoXiDM().compareTo(obj.getXiaoXiDM());
	}
	
	public XiaoXi clone(){
		XiaoXi xiaoXi = new XiaoXi();
		
		xiaoXi.setXiaoXiBT(xiaoXiBT);
		xiaoXi.setXiaoXiNR(xiaoXiNR);
		xiaoXi.setXiTong(xiTong);
		xiaoXi.setXiaoXiLX(xiaoXiLX);
		xiaoXi.setChuangJianRen(chuangJianRen);
		xiaoXi.setChuangJianIP(chuangJianIP);
		xiaoXi.setChuangJianSJ(chuangJianSJ);
		xiaoXi.setJieShouRen(jieShouRen);
		xiaoXi.setJieShouSJHM(jieShouSJHM);
		xiaoXi.setJieShouYXDZ(jieShouYXDZ);
		xiaoXi.setFaSongZT(faSongZT);
		xiaoXi.setFaSongCWXX(faSongCWXX);
		xiaoXi.setFaSongCS(faSongCS);
		xiaoXi.setFaSongSJ(faSongSJ);
		
		return xiaoXi;
	}
	
	
	
	
	
	
}