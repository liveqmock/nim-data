package com.poweruniverse.nim.data.entity;
import com.poweruniverse.nim.data.entity.base.BaseLiuChengJSXJ;

/*
* 实体类：流程检视下级
*/
public class LiuChengJSXJ  extends BaseLiuChengJSXJ {
	private static final long serialVersionUID = 1L;

	// constructors
	public LiuChengJSXJ () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public LiuChengJSXJ (java.lang.Integer id) {
		super(id);
	}

	public LiuChengJSXJ (LiuChengJS lxjsxj,String transactionId) {
		super();
		this.setXiaJiLCJS(lxjsxj);
		this.setTransitionId(transactionId);
	}
	
	protected void initialize () {}
	
	public String toString() {
		return ""+this.getXiaJiLCJS();
	}
}