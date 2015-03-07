package com.poweruniverse.nim.data.entity.system;
import com.poweruniverse.nim.data.entity.system.base.BaseJueSe;

/*
* 实体类：角色
*/
public class JueSe extends BaseJueSe {
	private static final long serialVersionUID = 1L;

	// constructors
	public JueSe () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public JueSe (java.lang.Integer id) {
		super(id);
	}
	/**
	 * Constructor for primary key
	 */
	public JueSe (java.lang.Integer id,String jueSeMC) {
		super(id);
		this.setJueSeMC(jueSeMC);
	}

	protected void initialize () {}
	
	
}