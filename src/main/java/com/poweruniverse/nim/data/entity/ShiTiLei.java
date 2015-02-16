package com.poweruniverse.nim.data.entity;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.data.entity.base.BaseShiTiLei;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

/*
* 实体类：实体类
*/
public class ShiTiLei  extends BaseShiTiLei  {
	private static final long serialVersionUID = 1L;

	// constructors
	public ShiTiLei () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ShiTiLei (java.lang.Integer id) {
		super(id);
	}

	protected void initialize () {}
	
	
	//根据当前实体类定义  以及a.b.c类型的字段描述字符串 取得最终字段c的字段类型
	public ZiDuanLX getZiDuanLX(String property) throws Exception{
		//字段描述字符串以.id结尾的话 说明最终字段为主键字段 即int(系统所使用的主键数据类型全部为int)
		if(property.endsWith(".id")){
			return new ZiDuanLX(ZiDuanLX.ZiDuanLX_INT,ZiDuanLX.ZiDuanLX_INT_DH);
		}
		//
		ZiDuan ziDuan = getZiDuan(property);
		return ziDuan.getZiDuanLX();
	}

	
	//根据当前实体类定义  以及a.b.c类型的字段描述字符串 取得最终字段c的字段定义
	public ZiDuan getZiDuan(String fieldName) throws Exception{
		ZiDuan ret = null;
		if(fieldName.indexOf(".")>=0){
			String[] ziDuanArray = fieldName.split("\\.");
			ShiTiLei guanLianSTL = this;
			ZiDuan ziDuan = null;
			for (int i=0;i<ziDuanArray.length -1;i++){
				ziDuan = guanLianSTL.getZiDuan(ziDuanArray[i]);
				if(ziDuan.getGuanLianSTL()==null){
					throw new Exception("实体类("+guanLianSTL.getShiTiLeiMC()+")的字段("+ziDuanArray[i]+")未定义关联实体类");
				}else{
					guanLianSTL = ziDuan.getGuanLianSTL();
				}
			}
			ret = guanLianSTL.getZiDuan(ziDuanArray[ziDuanArray.length-1]);
		}else{
			if (!getZds().isEmpty()){
				if("id".equals(fieldName)){
					ret = getZiDuan(getZhuJianLie());
				}else{
					Iterator<ZiDuan> zds = getZds().iterator();
					ZiDuan zd = null;
					while(zds.hasNext()){
						zd = zds.next();
						if(zd.getZiDuanDH().equals(fieldName)){
							ret = zd;
							break;
						}
					}
				}
				
			}else{
				throw new Exception("实体类("+getShiTiLeiMC()+")中未定义字段！");
			}
		}
		if(ret==null){
			throw new Exception("实体类("+getShiTiLeiMC()+")中未定义此字段("+fieldName+")！");
		}else{
//			System.out.println("影响性能，以后需要删除的代码（暂时用于确定字段是否被使用），ShitiLei.getZiDuan() line 77-79");
//			Session sess = HibernateSessionFactory.getSession();
//			ret.setShiFouSY(true);
//			sess.update(ret);
		}
		return ret;
	}
	
	//根据当前实体类定义  以及a.b.c类型的字段描述字符串 取得最终字段c的字段定义
	public boolean hasZiDuan(String fieldName) throws Exception{
		
		boolean ret = false;
		if(fieldName.indexOf(".")>=0){
			String firstField = fieldName.substring(0,fieldName.indexOf("."));
			String nextField = fieldName.substring(fieldName.indexOf(".")+1);
			if(this.hasZiDuan(firstField)){
				ZiDuan zd = this.getZiDuan(firstField);
				ShiTiLei guanLianSTL = zd.getGuanLianSTL();
				return guanLianSTL.hasZiDuan(nextField);
			}
			ret = false;
		}else{
			if (!getZds().isEmpty()){
				if("id".equals(fieldName)){
					ret = true;
				}else{
					Iterator<ZiDuan> zds = getZds().iterator();
					ZiDuan zd = null;
					while(zds.hasNext()){
						zd = zds.next();
						if(zd.getZiDuanDH().equals(fieldName)){
							ret = true;
							break;
						}
					}
				}
			}
		}
		return ret;
	}
	
	public static ShiTiLei getShiTiLeiByDH(String stldh){
		ShiTiLei stl = null;
		if(stldh!=null){
			Criteria criteria = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(ShiTiLei.class)
	 	  			.add(Restrictions.eq("shiTiLeiDH",stldh));
			List<?> stls = criteria.setMaxResults(1).list();
			if(stls.size()>0){
				stl= (ShiTiLei)stls.get(0);
			}
		}
		return stl;
	}

}