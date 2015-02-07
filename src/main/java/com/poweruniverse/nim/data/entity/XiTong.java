package com.poweruniverse.nim.data.entity;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.data.entity.base.BaseXiTong;
import com.poweruniverse.nim.data.service.utils.SystemSessionFactory;

/*
* 实体类：系统
*/
public class XiTong  extends BaseXiTong {
	private static final long serialVersionUID = 1L;

	// constructors
	public XiTong () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public XiTong (java.lang.Integer id) {
		super(id);
	}

	protected void initialize () {}
	
	public static XiTong getXiTongByDH(String xtdh){
		XiTong xt = null;
		if(xtdh!=null){
			Criteria criteria = SystemSessionFactory.getSession().createCriteria(XiTong.class)
	 	  			.add(Restrictions.eq("xiTongDH",xtdh));
			List<?> xts = criteria.setMaxResults(1).list();
			if(xts.size()>0){
				xt= (XiTong)xts.get(0);
			}
		}
		return xt;
	}

}