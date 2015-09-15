package com.nms.workflow.activiti;

import org.activiti.engine.ProcessEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creates and make available single instance of the Activiti Process Engine.
 * 
 * @author nasiruddin
 * 
 */


public class ActivitiProcessEngineInstance {

	private static final String contextPath = "SpringActivitiContext.xml";
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			contextPath);

	public static ProcessEngine getInstance(){
		return (ProcessEngine) applicationContext.getBean("processEngine");
	}
}
