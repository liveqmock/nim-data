package com.poweruniverse.oim.activiti;

import java.io.InputStream;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.util.ReflectUtil;


public class MyProcessEngineConfigurationImpl extends StandaloneProcessEngineConfiguration {

	protected InputStream getMyBatisXmlConfigurationSteam() {
		return ReflectUtil.getResourceAsStream("com/poweruniverse/oim/activiti/mappings.xml");
	}

	
	protected void initExpressionManager() {
		if (expressionManager==null) {
			expressionManager = new MyExpressionManager();
		}
	}

}
