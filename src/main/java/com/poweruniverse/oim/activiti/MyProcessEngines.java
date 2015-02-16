package com.poweruniverse.oim.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineInfoImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.util.ReflectUtil;


public abstract class MyProcessEngines extends ProcessEngines{
	
	public static ProcessEngine getProcessEngine(String processEngineName) {
		if (!isInitialized) {
			init();
		}
		return processEngines.get(processEngineName);
	}

	public static ProcessEngine getDefaultProcessEngine() {
		return getProcessEngine(NAME_DEFAULT);
	}

	
	public synchronized static void init() {
		if (!isInitialized) {
			if(processEngines == null) {
				// Create new map to store process-engines if current map is null
				processEngines = new HashMap<String, ProcessEngine>();        
			}
			ClassLoader classLoader = ReflectUtil.getClassLoader();
			Enumeration<URL> resources = null;
			try {
				resources = classLoader.getResources("activiti.cfg.xml");
			} catch (IOException e) {
				throw new ActivitiException("problem retrieving activiti.cfg.xml resources on the classpath: "+System.getProperty("java.class.path"), e);
			}
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				initProcessEnginFromResource(resource);
			}
		
			try {
				resources = classLoader.getResources("activiti-context.xml");
			} catch (IOException e) {
				throw new ActivitiException("problem retrieving activiti-context.xml resources on the classpath: "+System.getProperty("java.class.path"), e);
			}
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				initProcessEngineFromSpringResource(resource);
			}

			isInitialized = true;
		}
	}
	
	public static ProcessEngineInfo retry(String resourceUrl) {
		try {
			return initProcessEnginFromResource(new URL(resourceUrl));
		} catch (MalformedURLException e) {
			throw new ActivitiException("invalid url: "+resourceUrl, e);
		}
	}

	
	
	private static ProcessEngineInfo initProcessEnginFromResource(URL resourceUrl) {
		ProcessEngineInfo processEngineInfo = processEngineInfosByResourceUrl.get(resourceUrl);
		// if there is an existing process engine info
		if (processEngineInfo!=null) {
			// remove that process engine from the member fields
			processEngineInfos.remove(processEngineInfo);
			if (processEngineInfo.getException()==null) {
				String processEngineName = processEngineInfo.getName();
				processEngines.remove(processEngineName);
				processEngineInfosByName.remove(processEngineName);
			}
			processEngineInfosByResourceUrl.remove(processEngineInfo.getResourceUrl());
		}

		String resourceUrlString = resourceUrl.toString();
		try {
			ProcessEngine processEngine = buildProcessEngine(resourceUrl);
			String processEngineName = processEngine.getName();
			processEngineInfo = new ProcessEngineInfoImpl(processEngineName, resourceUrlString, null);
			processEngines.put(processEngineName, processEngine);
			processEngineInfosByName.put(processEngineName, processEngineInfo);
		} catch (Throwable e) {
			processEngineInfo = new ProcessEngineInfoImpl(null, resourceUrlString, "");
		}
		processEngineInfosByResourceUrl.put(resourceUrlString, processEngineInfo);
		processEngineInfos.add(processEngineInfo);
		return processEngineInfo;
	}
	
	
	private static  ProcessEngine buildProcessEngine(URL resource) {
		InputStream inputStream = null;
		try {
			inputStream = resource.openStream();
			ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(inputStream);
			
			ProcessEngineConfigurationImpl aa = (ProcessEngineConfigurationImpl)processEngineConfiguration;
			aa.setExpressionManager(new MyExpressionManager());
			return processEngineConfiguration.buildProcessEngine();
		
		} catch (IOException e) {
			throw new ActivitiException("couldn't open resource stream: "+e.getMessage(), e);
		} finally {
			IoUtil.closeSilently(inputStream);
		}
	}

  
}
