package com.poweruniverse.nim.data.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.description.Application;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.webservice.AbstractWebservice;
import com.poweruniverse.nim.data.entity.sys.FuJian;
import com.poweruniverse.nim.data.entity.sys.FuJianLX;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

@javax.xml.ws.soap.MTOM
@WebService
public class FileWebserviceImpl extends AbstractWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	public FileWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}
	
	/**
	 * 重新上传新文档
	 */
	public JSONMessageResult reUpload(@WebParam(name="fileId") String fileId,@XmlMimeType("*/*") @WebParam(name="fileHandler") DataHandler fileHandler){
		if(fileId==null || fileId.length()==0){
			return new JSONMessageResult("重新上传附件必须提供id！");
		}
		FuJian fj = null;
		File targetFile  = null;
		try {
			fj = (FuJian)HibernateSessionFactory.getSession().load(FuJian.class, fileId);
			
			//目标文件
			targetFile = new File(Application.getInstance().getContextPath()+ "WEB-INF/upload/"+ fileId + "." + fj.getWenJianHZ());
			// 检查上传目录是否存在
			if (targetFile.exists()) {
				targetFile.delete();
			}else if(!targetFile.getParentFile().exists()){
				targetFile.getParentFile().mkdirs();
			}
		} catch (Exception e) {
			return new JSONMessageResult(e.getMessage());
		}
		return this.doSaveFile(fj, targetFile,fileHandler);
	}
	/**
	 * 上传新文档
	 */
	public JSONMessageResult upload(@WebParam(name="fuJianLXDH") String fuJianLXDH,@WebParam(name="fileName") String fileName,@XmlMimeType("*/*") @WebParam(name="fileHandler") DataHandler fileHandler){
		FuJian fj  = null;
		File targetFile = null;
		try {
			fj = new FuJian();
			Session sess = HibernateSessionFactory.getSession();
			FuJianLX fjlx = (FuJianLX)sess.createCriteria(FuJianLX.class).add(Restrictions.eq("fuJianLXDH", fuJianLXDH)).uniqueResult();
			if(fjlx!=null){
				// 创建并保存附件对象
				if (fileName == null) {
					return new JSONMessageResult("文件名不存在！");
				}
				// 检查文件名
				if (fileName.indexOf("\'") > 0 || fileName.indexOf("\"") > 0) {
					return new JSONMessageResult("文件名包含引号");
				}
				// 去除文件名中可能存在的路径
				String fileName2 = fileName;
				if (fileName2.indexOf("\\") > 0) {
					fileName2 = fileName2.substring(fileName2.lastIndexOf("\\") + 1);
				}
				// 附件名称里 可能有非法字符
				fileName2 = fileName2.replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
				fj.setShangChuanWJM(fileName2);
				
				String wenJianHZ = "";
				if(fileName2.lastIndexOf(".") >=0){
					wenJianHZ = fileName2.substring( fileName2.lastIndexOf(".")+ 1);
				}
				fj.setWenJianHZ(wenJianHZ);
				
				fj.setFuJianLX(fjlx);
				
				//需要授权的功能操作 必须提供用户名、密码用于验证用户身份
				Integer yhdm = this.getYongHuDM(wsContext,false);
				
				YongHu yh = null;
				if(yhdm!=null){
					yh = (YongHu)sess.load(YongHu.class, yhdm);
				}
				//上传人
				if(yh!=null){
					fj.setShangChuanYH(yh);
					fj.setShangChuanYHMC(yh.getYongHuMC());
				}
				//上传日期
				fj.setShangChuanRQ(Calendar.getInstance().getTime());
				//下载次数
				fj.setXiaZaiCS(0);
				sess.save(fj);
				
				targetFile = new File(Application.getInstance().getContextPath()+ "WEB-INF/upload/"+ fj.getFuJianDM() + "." + fj.getWenJianHZ());
				// 检查上传目录是否存在
				if (targetFile.exists()) {
					return new JSONMessageResult("名称为("+targetFile.getPath()+")的附件已存在，不允许覆盖！");
				}else if(!targetFile.getParentFile().exists()){
					targetFile.getParentFile().mkdirs();
				}
			}else{
				return new JSONMessageResult("代号为("+fuJianLXDH+")的附件类型不存在！");
			}
		} catch (Exception e) {
			return new JSONMessageResult(e.getMessage());
		}
		return this.doSaveFile(fj, targetFile,fileHandler);
	}
	
	private JSONMessageResult doSaveFile(FuJian fj,File targetFile, DataHandler fileHandler){

		JSONMessageResult uploadRet = null;
		Session sess = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			sess = HibernateSessionFactory.getSession();
			//是否上传完成
			fj.setShiFouSCWC(false);
			//保存目标文件
			if(fileHandler == null){
				targetFile.createNewFile();
			}else{
				bis = new BufferedInputStream(fileHandler.getInputStream());
				bos = new BufferedOutputStream(new FileOutputStream(targetFile));

			    byte[] data = new byte[10240];
				int ret = bis.read(data);
				while(ret!=-1){
					bos.write(data, 0, ret);
					ret = bis.read(data);
				}
				bos.flush();
				bos.close();
				bos=null;
			}
			//记录实际文件名
			fj.setCunChuWJM("WEB-INF/upload/" +fj.getFuJianDM() + "." + fj.getWenJianHZ());//存储文件名为相对路径
			//文件长度
			fj.setWenJianCD(Double.valueOf(""+targetFile.length()));
			//因为是一次性上传 所以将已上传长度设置为与文件长度一致
			fj.setShangChuanCD(fj.getWenJianCD());
			fj.setShiFouSCWC(true);
			//更新文件记录
			sess.update(fj);
			// / Send a customized message to the client.
			JSONObject fuJianRet = new JSONObject();
			fuJianRet.put("fuJianDM", fj.getFuJianDM());
			fuJianRet.put("shangChuanWJM", fj.getShangChuanWJM());
			fuJianRet.put("wenJianHZ", fj.getWenJianHZ());
			fuJianRet.put("luRuRQ", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fj.getShangChuanRQ()));
			
			uploadRet = new JSONMessageResult();
			uploadRet.put("data", fuJianRet);
			
		} catch (Exception e) {
			e.printStackTrace();
			uploadRet = new JSONMessageResult(e.getMessage());
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
			
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return uploadRet;
	}

	/**
	 * 下载文档
	 */
	@WebResult
	@XmlMimeType("*/*")
	public DataHandler download( @WebParam(name="fileId") Integer fileId) throws Exception{
		if (fileId==null ){
			throw new FileNotFoundException("附件代码为空");
		}
		
		Session	sess = HibernateSessionFactory.getSession();
		FuJian fuJian = (FuJian)sess.load(FuJian.class, fileId);
			
		File file = new File(Application.getInstance().getContextPath() + "/WEB-INF/upload" + "/"+ fileId + "." + fuJian.getWenJianHZ());
		if (!file.exists()){
			throw new FileNotFoundException("附件 "+fileId + " 不存在");
		}
		return new DataHandler(
			new FileDataSource(file){
				public String getContentType() {
					return "application/octet-stream";
				}
			}
		);
	}

}
