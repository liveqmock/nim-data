package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：附件
*/
@Version("2015-03-19 09:57:11")
public abstract class BaseFuJian  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseFuJian () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFuJian (java.lang.Integer id) {
		this.setFuJianDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
			
	// 属性：上传长度 （shangChuanCD）
	private java.lang.Double shangChuanCD = new java.lang.Double(0);
	public java.lang.Double getShangChuanCD(){return this.shangChuanCD ;}
	public void setShangChuanCD(java.lang.Double shangChuanCD){this.shangChuanCD = shangChuanCD;}
	
			
	// 属性：静态文件名 （jingTaiWJM）
	private java.lang.String jingTaiWJM = null;
	public java.lang.String getJingTaiWJM(){return this.jingTaiWJM ;}
	public void setJingTaiWJM(java.lang.String jingTaiWJM){this.jingTaiWJM = jingTaiWJM;}
	
			
	// 属性：临时文件名 （lingShiWJM）
	private java.lang.String lingShiWJM = null;
	public java.lang.String getLingShiWJM(){return this.lingShiWJM ;}
	public void setLingShiWJM(java.lang.String lingShiWJM){this.lingShiWJM = lingShiWJM;}
	
			
	// 属性：上传文件名 （shangChuanWJM）
	private java.lang.String shangChuanWJM = null;
	public java.lang.String getShangChuanWJM(){return this.shangChuanWJM ;}
	public void setShangChuanWJM(java.lang.String shangChuanWJM){this.shangChuanWJM = shangChuanWJM;}
	
	// 对象：fuJianLX （fuJianLX）
	private com.poweruniverse.nim.data.entity.sys.FuJianLX fuJianLX;
	public com.poweruniverse.nim.data.entity.sys.FuJianLX getFuJianLX(){return this.fuJianLX ;}
	public void setFuJianLX(com.poweruniverse.nim.data.entity.sys.FuJianLX fuJianLX){this.fuJianLX = fuJianLX;}

			
	// 属性：下载次数 （xiaZaiCS）
	private java.lang.Integer xiaZaiCS = new java.lang.Integer(0);
	public java.lang.Integer getXiaZaiCS(){return this.xiaZaiCS ;}
	public void setXiaZaiCS(java.lang.Integer xiaZaiCS){this.xiaZaiCS = xiaZaiCS;}
	
			
	// 属性：修改人 （xiuGaiRen）
	private java.lang.String xiuGaiRen = null;
	public java.lang.String getXiuGaiRen(){return this.xiuGaiRen ;}
	public void setXiuGaiRen(java.lang.String xiuGaiRen){this.xiuGaiRen = xiuGaiRen;}
	
			
	// 属性：是否上传完成 （shiFouSCWC）
	private java.lang.Boolean shiFouSCWC = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouSCWC(){return this.shiFouSCWC ;}
	public void setShiFouSCWC(java.lang.Boolean shiFouSCWC){this.shiFouSCWC = shiFouSCWC;}
	
	// 对象：上传用户 （shangChuanYH）
	private com.poweruniverse.nim.data.entity.sys.YongHu shangChuanYH;
	public com.poweruniverse.nim.data.entity.sys.YongHu getShangChuanYH(){return this.shangChuanYH ;}
	public void setShangChuanYH(com.poweruniverse.nim.data.entity.sys.YongHu shangChuanYH){this.shangChuanYH = shangChuanYH;}

			
	// 属性：上传日期 （shangChuanRQ）
	private java.util.Date shangChuanRQ = null;
	public java.util.Date getShangChuanRQ(){return this.shangChuanRQ ;}
	public void setShangChuanRQ(java.util.Date shangChuanRQ){this.shangChuanRQ = shangChuanRQ;}
	
			
	// 属性：文件后缀 （wenJianHZ）
	private java.lang.String wenJianHZ = null;
	public java.lang.String getWenJianHZ(){return this.wenJianHZ ;}
	public void setWenJianHZ(java.lang.String wenJianHZ){this.wenJianHZ = wenJianHZ;}
	
			
	// 属性：上传用户名称 （shangChuanYHMC）
	private java.lang.String shangChuanYHMC = null;
	public java.lang.String getShangChuanYHMC(){return this.shangChuanYHMC ;}
	public void setShangChuanYHMC(java.lang.String shangChuanYHMC){this.shangChuanYHMC = shangChuanYHMC;}
	
			
	// 属性：文件长度 （wenJianCD）
	private java.lang.Double wenJianCD = new java.lang.Double(0);
	public java.lang.Double getWenJianCD(){return this.wenJianCD ;}
	public void setWenJianCD(java.lang.Double wenJianCD){this.wenJianCD = wenJianCD;}
	
			
	// 属性：备注 （beiZhu）
	private java.lang.String beiZhu = null;
	public java.lang.String getBeiZhu(){return this.beiZhu ;}
	public void setBeiZhu(java.lang.String beiZhu){this.beiZhu = beiZhu;}
	
	// 主键：fuJianDM
	private java.lang.Integer fuJianDM = null;
	public java.lang.Integer getFuJianDM(){return this.fuJianDM ;}
	public void setFuJianDM(java.lang.Integer fuJianDM){this.fuJianDM = fuJianDM;}

			
	// 属性：存储文件名 （cunChuWJM）
	private java.lang.String cunChuWJM = null;
	public java.lang.String getCunChuWJM(){return this.cunChuWJM ;}
	public void setCunChuWJM(java.lang.String cunChuWJM){this.cunChuWJM = cunChuWJM;}
	
	// 对象：上传部门 （shangChuanBM）
	private com.poweruniverse.nim.data.entity.sys.BuMen shangChuanBM;
	public com.poweruniverse.nim.data.entity.sys.BuMen getShangChuanBM(){return this.shangChuanBM ;}
	public void setShangChuanBM(com.poweruniverse.nim.data.entity.sys.BuMen shangChuanBM){this.shangChuanBM = shangChuanBM;}

			
	// 属性：删除状态 （shanChuZT）
	private java.lang.Boolean shanChuZT = new java.lang.Boolean(false);
	public java.lang.Boolean getShanChuZT(){return this.shanChuZT ;}
	public void setShanChuZT(java.lang.Boolean shanChuZT){this.shanChuZT = shanChuZT;}
	
			
	// 属性：是否传输完成 （shiFouCSWC）
	private java.lang.Boolean shiFouCSWC = new java.lang.Boolean(false);
	public java.lang.Boolean getShiFouCSWC(){return this.shiFouCSWC ;}
	public void setShiFouCSWC(java.lang.Boolean shiFouCSWC){this.shiFouCSWC = shiFouCSWC;}
	
			
	// 属性：传输次数 （chuanShuCS）
	private java.lang.Integer chuanShuCS = new java.lang.Integer(0);
	public java.lang.Integer getChuanShuCS(){return this.chuanShuCS ;}
	public void setChuanShuCS(java.lang.Integer chuanShuCS){this.chuanShuCS = chuanShuCS;}
	
			
	// 属性：传输错误信息 （chuanShuCWXX）
	private java.lang.String chuanShuCWXX = null;
	public java.lang.String getChuanShuCWXX(){return this.chuanShuCWXX ;}
	public void setChuanShuCWXX(java.lang.String chuanShuCWXX){this.chuanShuCWXX = chuanShuCWXX;}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.FuJian)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.FuJian entity = (com.poweruniverse.nim.data.entity.sys.FuJian) obj;
			if (null == this.getFuJianDM() || null == entity.getFuJianDM()) return false;
			else return (this.getFuJianDM().equals(entity.getFuJianDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getFuJianDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getFuJianDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.shangChuanWJM+"";
	}

	public Integer pkValue() {
		return this.fuJianDM;
	}

	public String pkName() {
		return "fuJianDM";
	}

	public void pkNull() {
		this.fuJianDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.FuJian obj = (com.poweruniverse.nim.data.entity.sys.FuJian)o;
		if(this.getFuJianDM()==null){
			return 1;
		}
		return this.getFuJianDM().compareTo(obj.getFuJianDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.FuJian clone(){
		com.poweruniverse.nim.data.entity.sys.FuJian fuJian = new com.poweruniverse.nim.data.entity.sys.FuJian();
		
		fuJian.setShangChuanCD(shangChuanCD);
		fuJian.setJingTaiWJM(jingTaiWJM);
		fuJian.setLingShiWJM(lingShiWJM);
		fuJian.setShangChuanWJM(shangChuanWJM);
		fuJian.setFuJianLX(fuJianLX);
		fuJian.setXiaZaiCS(xiaZaiCS);
		fuJian.setXiuGaiRen(xiuGaiRen);
		fuJian.setShiFouSCWC(shiFouSCWC);
		fuJian.setShangChuanYH(shangChuanYH);
		fuJian.setShangChuanRQ(shangChuanRQ);
		fuJian.setWenJianHZ(wenJianHZ);
		fuJian.setShangChuanYHMC(shangChuanYHMC);
		fuJian.setWenJianCD(wenJianCD);
		fuJian.setBeiZhu(beiZhu);
		fuJian.setCunChuWJM(cunChuWJM);
		fuJian.setShangChuanBM(shangChuanBM);
		fuJian.setShanChuZT(shanChuZT);
		fuJian.setShiFouCSWC(shiFouCSWC);
		fuJian.setChuanShuCS(chuanShuCS);
		fuJian.setChuanShuCWXX(chuanShuCWXX);
		
		return fuJian;
	}
	
	
	
	
	
	
}