package com.poweruniverse.nim.data.entity.system;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.poweruniverse.nim.data.entity.system.base.BaseLiuChengJS;

/*
* 实体类：流程检视
*/
public class LiuChengJS  extends BaseLiuChengJS {
	private static final long serialVersionUID = 1L;

	// constructors
	public LiuChengJS () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public LiuChengJS (java.lang.Integer id) {
		super(id);
	}

	protected void initialize () {}
	
	public LiuChengJS(LiuChengJS parentLCJS){
		this.setCaoZuoDH(parentLCJS.getCaoZuoDH());
		this.setCaoZuoMC(parentLCJS.getCaoZuoMC());
		this.setCaoZuoRen(parentLCJS.getCaoZuoRen());
		this.setChuangJianRQ(Calendar.getInstance().getTime());
		this.setGongNengDH(parentLCJS.getGongNengDH());
		this.setGongNengObjId(parentLCJS.getGongNengObjId());
		this.setHistoryId(parentLCJS.getHistoryId());
		//使用原上级
		for(LiuChengJSSJ sjlc:parentLCJS.getSjs()){
			LiuChengJSSJ sj = new LiuChengJSSJ();
			sj.setShangJiLCJS(sjlc.getShangJiLCJS());
			sj.setTransitionId(sjlc.getTransitionId());
			this.addTosjs(this, sj);
		}
	}
	
	
	public List<LiuChengJS> getParentLCJS(){
		List<LiuChengJS> parentLCJSs = new ArrayList<LiuChengJS>();
		for(LiuChengJSSJ sj:getSjs()){
			if(sj.getShangJiLCJS().getShiFouGDJD()){
				//直接上级是过渡节点
				parentLCJSs.addAll(sj.getShangJiLCJS().getParentLCJS());
			}else{
				//直接上级是任务节点
				parentLCJSs.add(sj.getShangJiLCJS());
			}
		}
		return parentLCJSs;
	}
	
	public List<LiuChengJS> getChildLCJS(){
		List<LiuChengJS> childLCJSs = new ArrayList<LiuChengJS>();
		for(LiuChengJSXJ xj:getXjs()){
			if(xj.getXiaJiLCJS().getShiFouGDJD()){
				//直接下级是过渡节点
				childLCJSs.addAll(xj.getXiaJiLCJS().getChildLCJS());
			}else{
				//直接下级是任务节点
				childLCJSs.add(xj.getXiaJiLCJS());
			}
		}
		return childLCJSs;
	}
	
	public String toString() {
		return "id:"+this.getLiuChengJSDM()+" "+this.getCaoZuoMC()+":"+this.getCaoZuoDH()+" "
				+(this.getShiFouWC()?"已完成":"未完成")+" "+(this.getShiFouSC()?"删除":"");
	}

}