package com.poweruniverse.nim.data.webservice;


import java.util.Calendar;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.utils.Encrypt;
import com.poweruniverse.nim.base.utils.MD5Utils;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.YongHuZT;
import com.poweruniverse.nim.data.service.utils.SystemSessionFactory;

@WebService
public class VerifyWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	/**
	 * 根据用户名 密码 验证用户是否允许登录
	 * @param dengLuDH
	 * @param dengLuMM
	 * @return
	 */
	public JSONMessageResult checkLogin(
			@WebParam(name="user") String userCode,
			@WebParam(name="password") String password){
		//
		JSONMessageResult result = null;
		Session sess = null;
		YongHu yongHu = null;
		try {
//			String userCode = params.getString("dengluDH");
//			String password = params.getString("dengluMM");
			
			// 检查用户名
			if (userCode == null || userCode.length() == 0) {
				return new JSONMessageResult("请输入登录名！");
			}
			// 检查密码
			if (password == null) {
				return new JSONMessageResult("请输入登录密码！");
			}
			sess = SystemSessionFactory.getSession();
			// 去除前后空格
			userCode = userCode.trim().toUpperCase();
			password = password.trim();
			//
			// 从数据库取对应的用户信息
			yongHu = (YongHu) sess.createCriteria(YongHu.class)
					.add(Restrictions.eq("dengLuDH", userCode)).uniqueResult();
			if (yongHu == null) {
				return new JSONMessageResult("无该用户,请联系管理员！");
			}
			String dengLuDH = yongHu.getDengLuDH();
			// 检查用户名是否有效
			if (!userCode.equals(dengLuDH)) {
				return new JSONMessageResult("用户名或口令错！");
			}
			// 检查用户状态是否有效
			if (yongHu.getYongHuZT().getYongHuZTDM().intValue() == YongHuZT.TingYong) {
				return new JSONMessageResult("此用户帐号已停用！");
			}
			// 检查用户状态是否有效
			if (yongHu.getYongHuZT().getYongHuZTDM().intValue() == YongHuZT.WeiQiYong) {
				return new JSONMessageResult("此用户帐号尚未启用！");
			}
			// 用来计算密码的种子
			String checkUserCode1 = "0000" + dengLuDH;
			checkUserCode1 = checkUserCode1.substring(checkUserCode1.length() - 5, checkUserCode1.length());
			// 加密的密码
			String password1 = Encrypt.encrypt(checkUserCode1, password);
			
			// 检查输入的密码(加密前或加密后)与数据库中存储的密码是否一致
			String md5Password = MD5Utils.MD5(yongHu.getDengLuMM());
			if (!password1.equals(yongHu.getDengLuMM()) && !password.equals(yongHu.getDengLuMM()) && !password.equals(md5Password)) {
				return new JSONMessageResult("用户名或口令错！");
			}
			//保存加密后的密码
			if(password.equals(yongHu.getDengLuMM())){
				yongHu.setDengLuMM(password1);
			}
			//修改最后登录时间
			yongHu.setZuiHouDLRQ(Calendar.getInstance().getTime());
			yongHu.setZuiHouDLIP( getClientIP(wsContext));
			sess.update(yongHu);
			
			result = new JSONMessageResult();
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				SystemSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				SystemSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	
}
