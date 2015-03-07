package com.poweruniverse.nim.data.entity.system;
import com.poweruniverse.nim.data.entity.system.base.BaseLiuChengJSSJ;

/*
* 实体类：流程检视上级
*/
public class LiuChengJSSJ  extends BaseLiuChengJSSJ {
	private static final long serialVersionUID = 1L;

	// constructors
	public LiuChengJSSJ () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public LiuChengJSSJ (java.lang.Integer id) {
		super(id);
	}

	public LiuChengJSSJ (LiuChengJS lxjssj,String transactionId) {
		super();
		this.setShangJiLCJS(lxjssj);
		this.setTransitionId(transactionId);
	}

	protected void initialize () {}
	
	public String toString() {
		return ""+this.getShangJiLCJS();
	}
}