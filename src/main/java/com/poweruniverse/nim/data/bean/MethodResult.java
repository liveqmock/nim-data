package com.poweruniverse.nim.data.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poweruniverse.nim.data.entity.sys.base.EntityI;

public class MethodResult {
	private List<EntityI> currentDataList = null;//本次操作的对象
	private String returnDataGNDH = null;//返回给客户端的对象 一般情况下与上面是一致的
	private List<EntityI> returnDataList = null;//返回给客户端的对象 一般情况下与上面是一致的
	private boolean success = false;
	private boolean relogin = false;
	private String errorMsg = null;
	private String fieldsMeta = null;
	private Map<String,Object> info = new HashMap<String,Object>();
	
	public MethodResult() {
		super();
		this.success = true;
	}
	
	public MethodResult(List<EntityI> dataList) {
		super();
		this.currentDataList = dataList;
		this.success = true;
	}
	
	public MethodResult(String errorMsg) {
		super();
		this.success = false;
		this.errorMsg = errorMsg;
	}
	
	public MethodResult(boolean relogin,String errorMsg) {
		super();
		this.success = false;
		this.relogin = relogin;
		this.errorMsg = errorMsg;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isRelogin() {
		return relogin;
	}
	public void setRelogin(boolean relogin) {
		this.relogin = relogin;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFieldsMeta() {
		return fieldsMeta;
	}

	public void setFieldsMeta(String fieldsMeta) {
		this.fieldsMeta = fieldsMeta;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public List<EntityI> getCurrentDataList() {
		return currentDataList;
	}

	public void setCurrentDataList(List<EntityI> currentDataList) {
		this.currentDataList = currentDataList;
	}

	public List<EntityI> getReturnDataList() {
		return returnDataList;
	}

	public void setReturnDataList(String returnDataGNDH,List<EntityI> returnDataList) {
		this.returnDataGNDH = returnDataGNDH;
		this.returnDataList = returnDataList;
	}

	public String getReturnDataGNDH() {
		return returnDataGNDH;
	}

	

}
