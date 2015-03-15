package com.poweruniverse.nim.data.entity.sys.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
/*
* 实体类：用户角色
*/
@Version("2015-03-08 11:16:00")
public abstract class BaseYongHuJS  implements Serializable,Comparable<Object> ,EntityI {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public BaseYongHuJS () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseYongHuJS (java.lang.Integer id) {
		this.setYongHuJSDM(id);
		initialize();
	}

	protected abstract void initialize ();
	
	// 对象：角色 （jueSe）
	private com.poweruniverse.nim.data.entity.sys.JueSe jueSe;
	public com.poweruniverse.nim.data.entity.sys.JueSe getJueSe(){return this.jueSe ;}
	public void setJueSe(com.poweruniverse.nim.data.entity.sys.JueSe jueSe){this.jueSe = jueSe;}

	// 主键：yongHuJSDM
	private java.lang.Integer yongHuJSDM = null;
	public java.lang.Integer getYongHuJSDM(){return this.yongHuJSDM ;}
	public void setYongHuJSDM(java.lang.Integer yongHuJSDM){this.yongHuJSDM = yongHuJSDM;}

	// 对象：用户 （yongHu）
	private com.poweruniverse.nim.data.entity.sys.YongHu yongHu;
	public com.poweruniverse.nim.data.entity.sys.YongHu getYongHu(){return this.yongHu ;}
	public void setYongHu(com.poweruniverse.nim.data.entity.sys.YongHu yongHu){this.yongHu = yongHu;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.poweruniverse.nim.data.entity.sys.YongHuJS)) return false;
		else {
			com.poweruniverse.nim.data.entity.sys.YongHuJS entity = (com.poweruniverse.nim.data.entity.sys.YongHuJS) obj;
			if (null == this.getYongHuJSDM() || null == entity.getYongHuJSDM()) return false;
			else return (this.getYongHuJSDM().equals(entity.getYongHuJSDM()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getYongHuJSDM()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getYongHuJSDM().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.jueSe+"";
	}

	public Integer pkValue() {
		return this.yongHuJSDM;
	}

	public String pkName() {
		return "yongHuJSDM";
	}

	public void pkNull() {
		this.yongHuJSDM = null;;
	}
	
	public int compareTo(Object o) {
		com.poweruniverse.nim.data.entity.sys.YongHuJS obj = (com.poweruniverse.nim.data.entity.sys.YongHuJS)o;
		if(this.getYongHuJSDM()==null){
			return 1;
		}
		return this.getYongHuJSDM().compareTo(obj.getYongHuJSDM());
	}
	
	public com.poweruniverse.nim.data.entity.sys.YongHuJS clone(){
		com.poweruniverse.nim.data.entity.sys.YongHuJS yongHuJS = new com.poweruniverse.nim.data.entity.sys.YongHuJS();
		
		yongHuJS.setJueSe(jueSe);
		yongHuJS.setYongHu(yongHu);
		
		return yongHuJS;
	}
	
	
	
	
	
	
}