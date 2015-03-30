package com.poweruniverse.nim.data.action;

import net.sf.json.JSONObject;

import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;

public abstract class AfterAction extends Action{
	public abstract JSONMessageResult invoke(YongHu yongHu,GongNengCZ gongNengCZ,EntityI entity,JSONObject jsonObj)  throws Exception;
}
