package com.poweruniverse.nim.data.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.JueSe;
import com.poweruniverse.nim.data.entity.JueSeQXGNCZ;
import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuZT;

public class AuthUtils {
	public static boolean hasGNCZAuth(String gndh,String czdh,Integer yhdm) throws Exception{
		boolean hasGNCZAuth = false;
		try {
			if(yhdm!=null){
				List<?> jsgnczs = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(JueSeQXGNCZ.class)
						.createAlias("gongNengCZ", "jsgncz_gncz")
						.createAlias("jsgncz_gncz.gongNeng", "jsgncz_gncz_gn")
						.add(Restrictions.eq("jsgncz_gncz_gn.gongNengDH",gndh))
						.add(Restrictions.eq("jsgncz_gncz.caoZuoDH", czdh))
						.add(Restrictions.sqlRestriction("jueSeDM in (select yhjs.juesedm from sys_yonghujs yhjs where yhjs.yonghudm ="+yhdm+" )"))
						.list();
				if(jsgnczs.size()>0){
					hasGNCZAuth = true;
				}
			}
		} catch (Exception e) {
			hasGNCZAuth = false;
		}
		return hasGNCZAuth;
	}
	
	/**
	 * 检查用户对特定数据 是否有权限进行某种操作
	 * 功能action中会调用此方法
	 * @param gndh
	 * @param czdh
	 * @param id
	 * @param yh
	 * @return
	 * @throws Exception
	 */
	public static boolean checkAuth(String gndh,String czdh,Integer id,Integer yhdm) throws Exception{
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
				.createAlias("gongNeng", "gncz_gn")
				.add(Restrictions.eq("gncz_gn.gongNengDH",gndh))
				.add(Restrictions.eq("caoZuoDH", czdh)).uniqueResult();
		if(gncz==null){
			System.out.println("功能操作:"+gndh+"."+czdh+"不存在！");
			return false;
//			throw new Exception();
		}
		return checkAuth(gncz,id,yhdm);
	}
	public static boolean checkAuth(GongNengCZ gncz,Integer id,Integer yhdm) throws Exception{
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		YongHu yh = null;
		if(yhdm!=null){
			yh = (YongHu)sess.load(YongHu.class, id);
		}
		//功能实体类
		Class<?> gnStlClass = Class.forName(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName());
		if(gncz.getKeYiSQ() && gncz.getDuiXiangXG()){
			//当前功能操作需授权 且对象相关 开始检查权限 
			if(yh==null){
				throw new Exception("请重新登录！");
			}else if(yh.getYongHuZT().getYongHuZTDM() != YongHuZT.ZhengChang){
				throw new Exception("用户账户状态异常："+yh.getYongHuZT().getYongHuZTMC());
			}
			
			//基础查询语法
			Criteria criteria = sess.createCriteria(gnStlClass);
			//检查是否缓存了用户基本权限 
//			String storedCriterionKey = gncz.getGongNeng().getGongNengDH()+"_"+gncz.getCaoZuoDH()+"_"+yh.getYongHuDM();
//			StoredCriterion sc = getStoredCriterion(storedCriterionKey);
//			if(sc!=null ){
//				criteria.add(sc.getCriterion());
//			}else{
				//取得用户权限 permits=null means all
				List<Permit> permits = QueryUtils.getPermitsByYHAuth(yh,gncz,true);
				Criterion c = QueryUtils.createCriterionByPermits(gncz.getGongNeng().getShiTiLei(),permits,yh);
				if(c!=null){
					criteria.add(c);
					//缓存基础查询语法
//					StoredCriterions.put(storedCriterionKey, new StoredCriterion(c));
				}
//			}
			//按主键过滤
			criteria.add(Restrictions.eq(gncz.getGongNeng().getShiTiLei().getZhuJianLie(), id));
			//添加alia
//			if(existsAlias!=null){
//				Iterator<String> keys = existsAlias.keySet().iterator();
//				String key = null;
//				while(keys.hasNext()){
//					key = keys.next();
//					criteria.createAlias(key, existsAlias.get(key));
//				}
//			}
			//取得符合条件的记录数
			int totalCount = ((Long)criteria.setProjection(Projections.rowCount())
					.uniqueResult())
					.intValue();
			if(totalCount==0){
				if(gncz.getGongNeng().getShiFouLCGN()){
					//根据流程检视 确定用户是否有额外的权限(操作过的记录)
					LiuChengJS currentLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
							.add(Restrictions.eq("gongNengDH",gncz.getGongNeng().getGongNengDH()))//当前功能
							.add(Restrictions.eq("gongNengObjId",id))//当前流程
							.add(Restrictions.eq("shiFouWC", true))//已完成
							.add(Restrictions.eq("shiFouSC", false))//未删除
							.add(Restrictions.eq("shiFouGDJD", false))//非过渡节点
							.add(Restrictions.isNotNull("caoZuoDH"))//非进行退回和收回操作的节点
							.add(Restrictions.sizeGt("xjs", 0))//非被收回的节点（ 已处理完成且未被收回或退回的节点 会存在下级节点） 
							.add(Restrictions.eq("wanChengYH.id", yh.getYongHuDM()))//操作人为当前用户
							.setMaxResults(1)
							.uniqueResult();
					if(currentLCJS!=null ){
						return true;
					}
					//是否有待办
					currentLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
							.add(Restrictions.eq("gongNengDH",gncz.getGongNeng().getGongNengDH()))//当前功能
							.add(Restrictions.eq("gongNengObjId",id))//当前流程
							.add(Restrictions.eq("shiFouWC", false))//未完成
							.add(Restrictions.eq("shiFouSC", false))//未删除
							.add(Restrictions.eq("shiFouGDJD", false))//非过渡节点
							.add(Restrictions.isNotNull("caoZuoDH"))//非进行退回和收回操作的节点
							.add(Restrictions.sizeGt("xjs", 0))//非被收回的节点（ 已处理完成且未被收回或退回的节点 会存在下级节点） 
							.add(Restrictions.like("caoZuoRen", yh.getYongHuMC()+" "))//操作人中包含当前用户
							.setMaxResults(1)
							.uniqueResult();
					if(currentLCJS!=null ){
						return true;
					}
				}
				return false;
			}else if(totalCount >1){
				throw new Exception("符合条件的记录多于一条！");
			}
		}
		return true;
	}
	
	public static List<YongHu> getAuthUsers(GongNengCZ gncz,Integer id,JueSe js) throws Exception{
		List<YongHu> authUsers = new ArrayList<YongHu>();
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		Criterion neExpreesion = Restrictions.sqlRestriction("1<>1");
		
		//功能实体类
		Class<?> gnStlClass = Class.forName(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName());
		if(gncz.getKeYiSQ() && gncz.getDuiXiangXG()){
			//取得用户权限 permits=null means all
			List<Permit> permits = QueryUtils.getPermitsByJSAuth(js, gncz);
			//为提高效率 检查是否为简单的与当前用户有关的权限 判断是否可以不用执行sql 
			List<Integer> preAuthUsers = new ArrayList<Integer>();
			List<Permit> removedPermits = new ArrayList<Permit>();
			if(permits.size()>0){
				//
				Object dataObj = sess.load(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName(), id);
				//
				for(Permit permit:permits){
					for(Query query:permit.getQueries()){
						if(query instanceof PropertyQuery){
							PropertyQuery pq = (PropertyQuery)query;
							if((pq.getOperator().equals("=") || pq.getOperator().equals("eq")) && pq.getValue().equals("{yongHuDM}")){
								//此授权 只有此用户会有权限
								try {
									Integer dataUserId = (Integer)ValueUtils.getValueByProperties(dataObj,pq.getProperty());
									preAuthUsers.add(dataUserId);
									//待删除的授权
									removedPermits.add(permit);
								} catch (Exception e) {
									System.err.println("取对象字段值失败 希望得到一个Integer 但不是  >>>  功能:"+gncz.getGongNeng().getGongNengDH()+" 操作:"+gncz.getCaoZuoDH()+" 主键:"+id+" 字段:"+pq.getProperty()+" 值："+ValueUtils.getValueByProperties(dataObj,pq.getProperty()));
								}
								break;
							}
						}
					}
				}
				
				//取得有当前角色的用户
				Criteria yhCriteria = sess.createCriteria(YongHu.class)
						.add(Restrictions.eq("yongHuZT.id", YongHuZT.ZhengChang))
						.add(Restrictions.sqlRestriction("yongHuDM in (select yhjs.yongHuDM from sys_yonghujs yhjs where yhjs.jueSeDM ="+js.getJueSeDM()+")"));
				if(removedPermits.size()==permits.size()){
					//只对预授权的用户 进行检查
					yhCriteria.add(Restrictions.in("id", preAuthUsers));
				}
				@SuppressWarnings("unchecked")
				List<YongHu> yhs = (List<YongHu>)yhCriteria.list(); 
				for(Object yhObj:yhs){
					//基础查询语法
					Criteria criteria = sess.createCriteria(gnStlClass);
					permits = QueryUtils.getPermitsByJSAuth(js, gncz);
					//按主键过滤
					criteria.add(Restrictions.eq(gncz.getGongNeng().getShiTiLei().getZhuJianLie(), id));
					Criterion c = QueryUtils.createCriterionByPermits(gncz.getGongNeng().getShiTiLei(),permits,(YongHu)yhObj);
					//检验是否需要执行sql
					if(c==null){
						authUsers.add((YongHu)yhObj);
					}else if(!c.equals(neExpreesion)){
						//取得符合条件的记录数
						int totalCount = 0;
						try {
							totalCount = ((Long)criteria.add(c).setProjection(Projections.rowCount())
									.uniqueResult())
									.intValue();
						} catch (Exception e) {
							//查询可能报错 是当前用户的相关数据不满足查询条件 忽略即可
//							e.printStackTrace();
						}
						if(totalCount==1){
							authUsers.add((YongHu)yhObj);
						}
					}
				}
			}
		}else{
			@SuppressWarnings("unchecked")
			List<YongHu> yhs = (List<YongHu>)sess.createCriteria(YongHu.class)
					.add(Restrictions.eq("yongHuZT.id", YongHuZT.ZhengChang))
					.add(Restrictions.sqlRestriction("yongHuDM in (select yhjs.yongHuDM from sys_yonghujs yhjs where yhjs.jueSeDM ="+js.getJueSeDM()+")"))
					.list();
			authUsers.addAll(yhs);
		}
		return authUsers;
	}
	
	//检查当前功能对应的数据对象 是否满足后面的条件要求
	public static boolean meetCondition(ShiTiLei stl,Integer id,YongHu yh,List<Permit> permits) throws Exception{
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		//功能实体类
		Class<?> gnStlClass = Class.forName(stl.getShiTiLeiClassName());
		//基础查询语法
		Criteria criteria = sess.createCriteria(gnStlClass);
		//取得用户权限 permits=null means all
		Criterion c = QueryUtils.createCriterionByPermits(stl,permits,yh);
		//按主键过滤
		if(id==null && c==null){
			return true; //id 为null 且 无过滤条件 
		}else if(id==null && c!=null){
			criteria.add(c);//id 为 null 且 有过滤条件
		}else{
			if(c!=null){
				criteria.add(c);
			}
			criteria.add(Restrictions.eq(stl.getZhuJianLie(), id));
		}
		//取得符合条件的记录数
		int totalCount = ((Long)criteria.setProjection(Projections.rowCount())
				.uniqueResult())
				.intValue();
		if(totalCount==0){
			return false;
//		}else if(totalCount >1){
//			throw new Exception("符合条件的记录多于一条！");
		}
		return true;
	}

}
