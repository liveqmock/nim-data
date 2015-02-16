package com.poweruniverse.nim.data.entity;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.data.entity.base.BaseGongNeng;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

/*
* 实体类：功能
*/
public class GongNeng  extends BaseGongNeng  { 
	private static final long serialVersionUID = 1L;

	// constructors
	public GongNeng () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GongNeng (java.lang.Integer id) {
		super(id);
	}

	public GongNeng (java.lang.Integer id,String gnmc,String gndh) {
		super(id);
		this.setGongNengMC(gnmc);
		this.setGongNengDH(gndh);
	}

	protected void initialize () {}
	
	public static GongNeng getGongNengByDH(String gndh){
		GongNeng gn = null;
		if(gndh!=null){
			Criteria criteria = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(GongNeng.class)
	 	  			.add(Restrictions.eq("gongNengDH",gndh));
			List<?> gns = criteria.setMaxResults(1).list();
			if(gns.size()>0){
				gn= (GongNeng)gns.get(0);
			}
		}
		return gn;
	}
	
	/*
	 * 哪个功能提供这个实体类的选择权限
	 */
	public static GongNeng getOperationGNBySTL(ShiTiLei stl,String caoZuoDH){
		Criteria criteria = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(GongNengCZ.class)
				.createAlias("gongNeng", "gncz_gn")
 	  			.add(Restrictions.eq("gncz_gn.shiTiLei.id",stl.getShiTiLeiDM()))
 	  			.add(Restrictions.eq("caoZuoDH",caoZuoDH));
		List<?> gnczs = criteria.list();
		GongNeng gn = null;
		GongNengCZ gncz = null;
		if(gnczs.size()>0){
			for(int i=0;i<gnczs.size();i++){
				gncz = (GongNengCZ)gnczs.get(0);
				gn= gncz.getGongNeng();
				//如果有多个功能操作 以不需要授权的为主
				//否则 取最后一个
				if(!gncz.getKeYiSQ().booleanValue()){
					break;
				}
			}
//			if(gnczs.size()>1){
//				System.out.println("警告:实体类("+stl.getShiTiLeiMC()+")有多于一个提供选择操作的功能！");
//			}
		}
		return gn;
	}

	//取得此实体类对应的选择功能
	public static GongNeng getGongNengBySTLDM(Integer stldm){
		Criteria criteria = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(GongNeng.class)
 	  			.add(Restrictions.eq("shiTiLei.id",stldm))
 	  			.add(Restrictions.sqlRestriction("gongNengDM in ( select gncz.gongNengDM from sys_gongNengCZ gncz where gncz.caoZuoDH = 'select')"));
		List<?> gns = criteria.setMaxResults(1).list();
		GongNeng gn = null;
		if(gns.size()>0){
			gn= (GongNeng)gns.get(0);
		}
		return gn;
	}
	
	public GongNengCZ getCaoZuoByDH(String caoZuoDH){
		GongNengCZ gncz = null;
		if(this.getCzs()!=null && this.getCzs().size()>0){
			for(GongNengCZ  cz:this.getCzs()){
				if(cz.getCaoZuoDH().equals(caoZuoDH)){
					gncz = cz;
					break;
				}
			}
		}
		return gncz;
	}

	
}