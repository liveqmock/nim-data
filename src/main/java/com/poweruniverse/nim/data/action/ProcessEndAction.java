package com.poweruniverse.nim.data.action;

import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.entity.sys.base.BusinessI;

public abstract class ProcessEndAction  extends Action{
	public abstract JSONMessageResult invoke(YongHu yongHu,GongNengCZ gongNengCZ,BusinessI businessI)  throws Exception;
}
 