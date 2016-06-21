package com.anz.common.ioc.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.anz.common.ioc.IIoCFactory;
import com.ibm.broker.plugin.MbNode;

public class AnzSpringIoCFactory implements IIoCFactory {
	
	private static AnzSpringIoCFactory instance;
	
	private AnnotationConfigApplicationContext context;
	
	public AnzSpringIoCFactory(MbNode mbNode) {
		
		MbNodefactory.getInstance().setMbNode(mbNode);
		
		// Load the Spring Context
		context = new AnnotationConfigApplicationContext();
		
		if(mbNode == null) {
			// This is running in Junit Test MODE
			context.register(TestSpringConfig.class);
		} else {
			// register the Spring configuration
			context.register(SpringConfig.class);
		}
		
		context.refresh();		
    }
	
	public <T> T getBean(String name, Class<T> clazz) {
		return context.getBean(name, clazz);
	}

	public <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	

	public static IIoCFactory getInstance(MbNode myCompute) {
		
		if(instance == null) {
			instance = new AnzSpringIoCFactory(myCompute);
		}
		
		return instance;
	}
	
	/**
	 * To be used only after MbNodefactory.getInstance().setMbNode() is performed
	 * Otherwise use AnzSpringIoCFactory.getInstance(MbNode myCompute)
	 * @return AnzSpringIoCFactory with data source
	 * @throws Exception
	 */
	public static IIoCFactory getInstance() throws Exception {
		
		MbNode computeNode = MbNodefactory.getInstance().getMbNode();
		/*if(computeNode == null) {
			throw new Exception("Could not instantiate the data source. Compute Node is null. Try setting the computeNode in MbNodefactory");
		}*/
		
		if(instance == null) {
			instance = new AnzSpringIoCFactory(computeNode);
		}
		
		return instance;
	}

}
