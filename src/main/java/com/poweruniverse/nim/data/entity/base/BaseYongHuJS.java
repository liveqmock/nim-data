package com.poweruniverse.nim.data.entity.base;
import java.io.Serializable;

import com.poweruniverse.nim.data.entity.JueSe;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuJS;
/*
* 实体类：用户角色
*/
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
	private JueSe jueSe;
	public JueSe getJueSe(){return this.jueSe ;}
	public void setJueSe(JueSe jueSe){this.jueSe = jueSe;}

	// 主键：yongHuJSDM
	private java.lang.Integer yongHuJSDM = null;
	public java.lang.Integer getYongHuJSDM(){return this.yongHuJSDM ;}
	public void setYongHuJSDM(java.lang.Integer yongHuJSDM){this.yongHuJSDM = yongHuJSDM;}

	// 对象：用户 （yongHu）
	private YongHu yongHu;
	public YongHu getYongHu(){return this.yongHu ;}
	public void setYongHu(YongHu yongHu){this.yongHu = yongHu;}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof YongHuJS)) return false;
		else {
			YongHuJS entity = (YongHuJS) obj;
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
		YongHuJS obj = (YongHuJS)o;
		if(this.getYongHuJSDM()==null){
			return 1;
		}
		return this.getYongHuJSDM().compareTo(obj.getYongHuJSDM());
	}
	
	public YongHuJS clone(){
		YongHuJS yongHuJS = new YongHuJS();
		
		yongHuJS.setJueSe(jueSe);
		yongHuJS.setYongHu(yongHu);
		
		return yongHuJS;
	}
	
	
	
	
	
	
}