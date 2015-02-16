package com.poweruniverse.nim.data.webservice;


import java.util.Calendar;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.utils.Encrypt;
import com.poweruniverse.nim.base.utils.MD5Utils;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuZT;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

@WebService
public class VerifyWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	protected static Logger logger = null;

	public VerifyWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
		if(logger==null){
			logger = Logger.getLogger("nim-data.verify");
		}
	}

	/**
	 * 根据用户名 密码 验证用户是否允许登录
	 * @param dengLuDH
	 * @param dengLuMM
	 * @return
	 */
	public JSONMessageResult userAuth(
			@WebParam(name="userName") String userName,
			@WebParam(name="userPassword") String userPassword,
			@WebParam(name="clientIP") String clientIP
			){
		//
		JSONMessageResult result = null;
		Session sess = null;
		YongHu yongHu = null;
		try {
//			String userCode = params.getString("dengluDH");
//			String password = params.getString("dengluMM");
			logger.debug("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...");
			// 检查用户名
			if (userName == null || userName.length() == 0) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：登录名为空！");
				return new JSONMessageResult("请输入登录名！");
			}
			// 检查密码
			if (userPassword == null) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：登录密码为空！");
				return new JSONMessageResult("请输入登录密码！");
			}
			sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
			// 去除前后空格
			userName = userName.trim().toUpperCase();
			userPassword = userPassword.trim();
			//
			// 从数据库取对应的用户信息
			yongHu = (YongHu) sess.createCriteria(YongHu.class)
					.add(Restrictions.eq("dengLuDH", userName)).uniqueResult();
			if (yongHu == null) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：无该用户！");
				return new JSONMessageResult("无该用户,请联系管理员！");
			}
			String dengLuDH = yongHu.getDengLuDH();
			// 检查用户名是否有效
			if (!userName.equals(dengLuDH)) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：用户名或口令错！");
				return new JSONMessageResult("用户名或口令错！");
			}
			// 检查用户状态是否有效
			if (yongHu.getYongHuZT().getYongHuZTDM().intValue() == YongHuZT.TingYong) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：此用户帐号已停用！");
				return new JSONMessageResult("此用户帐号已停用！");
			}
			// 检查用户状态是否有效
			if (yongHu.getYongHuZT().getYongHuZTDM().intValue() == YongHuZT.WeiQiYong) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：此用户帐号尚未启用！");
				return new JSONMessageResult("此用户帐号尚未启用！");
			}
			// 用来计算密码的种子
			String checkUserCode1 = "0000" + dengLuDH;
			checkUserCode1 = checkUserCode1.substring(checkUserCode1.length() - 5, checkUserCode1.length());
			// 加密的密码
			String password1 = Encrypt.encrypt(checkUserCode1, userPassword);
			
			// 检查输入的密码(加密前或加密后)与数据库中存储的密码是否一致
			String md5Password = MD5Utils.MD5(yongHu.getDengLuMM());
			if (!password1.equals(yongHu.getDengLuMM()) && !userPassword.equals(yongHu.getDengLuMM()) && !userPassword.equals(md5Password)) {
				logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...失败：用户名或口令错");
				return new JSONMessageResult("用户名或口令错！");
			}
			//保存加密后的密码
			if(userPassword.equals(yongHu.getDengLuMM())){
				yongHu.setDengLuMM(password1);
			}
			//修改最后登录时间
			yongHu.setZuiHouDLRQ(Calendar.getInstance().getTime());
			yongHu.setZuiHouDLIP(clientIP);
			sess.update(yongHu);

			result = new JSONMessageResult();
			result.put("yongHuDM", yongHu.getYongHuDM());
			result.put("yongHuMC", yongHu.getYongHuMC());
			result.put("dengLuDH", yongHu.getDengLuDH());
			result.put("dengLuMM", yongHu.getDengLuMM());
			logger.debug("验证用户："+userName+" 密码:*** ip:"+clientIP+" ...成功");
		} catch (Exception e) {
			logger.error("验证用户："+userName+" 密码:*** ip:"+clientIP+" 失败:"+e.getMessage(),e);
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	
	
	/**
	 * 检查当前用户是否有此功能操作及对象权限
	 * @param dengLuDH
	 * @param dengLuMM
	 * @return
	 */
	public JSONMessageResult hasAuth(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id){
		//
		JSONMessageResult result = null;
		Session sess = null;
		try {
			Integer yhdm = this.getYongHuDM(wsContext, false);
			boolean authRsult = AuthUtils.checkAuth(gongNengDH,caoZuoDH,id,yhdm);
			result = new JSONMessageResult();
			result.put("data", authRsult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	/**
	 * 检查当前用户是否有此功能操作及对象权限
	 * @param dengLuDH
	 * @param dengLuMM
	 * @return
	 */
	public JSONMessageResult hasGNCZAuth(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH){
		//
		JSONMessageResult result = null;
		Session sess = null;
		try {
			Integer yhdm = this.getYongHuDM(wsContext, false);
			boolean authRsult = AuthUtils.hasGNCZAuth(gongNengDH,caoZuoDH,yhdm);
			result = new JSONMessageResult();
			result.put("data", authRsult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	
	
}
