package com.poweruniverse.nim.data;

import java.io.File;

import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

public class TestSessionFactory {

	public static void main(String[] args) {
		File cfgFile = new File(TestSessionFactory.class.getResource("/hibernate.system.xml").getFile());
		
		HibernateSessionFactory.createSessionFactory("system", cfgFile);
	}

}
