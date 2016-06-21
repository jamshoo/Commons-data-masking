package com.anz.common.compute;

import java.util.Date;
import java.util.Properties;

import com.anz.common.compute.impl.ComputeUtils;

/**
 * @author sanketsw
 *
 */
public class LogMessage {
	
	String id;
	
	String terminal;
	
	ServiceInfo service;
	
	BrokerInfo broker;
	
	Date timestamp;
	
	String message;
	
	String incidentArea;
	

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public ServiceInfo getService() {
		return service;
	}
	
	

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	/**
	 * @param service
	 */
	public void setService(ServiceInfo service) {
		this.service = service;
	}

	/**
	 * @return
	 */
	public BrokerInfo getBroker() {
		return broker;
	}

	/**
	 * @param broker
	 */
	public void setBroker(BrokerInfo broker) {
		this.broker = broker;
	}

	/**
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public String getIncidentArea() {
		return incidentArea;
	}

	/**
	 * @param incidentArea
	 */
	public void setIncidentArea(String incidentArea) {
		this.incidentArea = incidentArea;
	}
	

	public void setBrokerAndServiceDetails(ComputeInfo metadata) {
		BrokerInfo broker = new BrokerInfo();
		broker.setBrokerName(metadata.getBroker().getName());
		broker.setQueueManagerName(metadata.getBroker().getQueueManagerName());
		setBroker(broker);
		
		ServiceInfo service = new ServiceInfo();
		service.setMessageFlowName(metadata.getMessageFlow().getName());
		service.setServiceName(metadata.getMessageFlow().getApplicationName());
		setService(service);
		
	}

	public void setStaticProperties() throws Exception {

		Properties props = ComputeUtils.getGlobalVariables();
		if(props != null) {
			broker.setRegion(props.getProperty("Region"));
			broker.setClassValue(props.getProperty("Class"));
			broker.setForwarder(props.getProperty("Forwarder"));
			broker.setHostName(props.getProperty("HostName"));
		}
		

	}	

}
