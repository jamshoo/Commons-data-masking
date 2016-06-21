/**
 * 
 */
package com.anz.common.compute.impl;

import java.util.Calendar;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.LogMessage;
import com.anz.common.compute.LoggingTerminal;
import com.anz.common.compute.TransformType;
import com.anz.common.error.ExceptionMessage;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author root
 *
 */
public abstract class CommonLoggingCompute extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonJavaCompute#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.UNSPECIFIED;
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJavaCompute#execute(com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {		

		LogMessage logMessage = new LogMessage();
		String message = null;
		try {
			message = ComputeUtils.getStringFromBlob(outAssembly.getMessage());
		} catch(Exception e) {
			// Never throw an exception from logging compute
			appLogger.throwing(e);
		}
		if(message != null) {
			try {
				ExceptionMessage em = TransformUtils.fromJSON(message, ExceptionMessage.class);
				if(em != null && em.getStatus() != null && em.getStatus().getCode() != null) {
					appLogger.error(TransformUtils.toJSON(em));
					return;
				}
			} catch(Exception e) {
				// Nothing to do here.
				// This is the normal path when there is no exception object in the message
			}
		} else {
			message = "null";
		}
		
		try {
			logMessage.setId(ComputeUtils.getTransactionId(outAssembly));
			logMessage.setTerminal(getLogTerminal().getValue());
			logMessage.setIncidentArea(getMetaData().getUserDefinedProperties().get("IncidentArea"));
			logMessage.setTimestamp(Calendar.getInstance().getTime());
			logMessage.setMessage(message);
			logMessage.setBrokerAndServiceDetails(getMetaData());
			logMessage.setStaticProperties();			
			appLogger.info(TransformUtils.toJSON(logMessage));
		} catch(Exception e) {
			// Never throw an exception from logging compute
			appLogger.throwing(e);
		}
	}
	
	
	/**
	 * Terminal Input or Output or intermediate
	 * @return terminal of logging
	 */
	public abstract LoggingTerminal getLogTerminal();
	
	
	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJavaCompute#prepareForTransformation(com.anz.common.compute.ComputeInfo, com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@Override
	public void prepareForTransformation(ComputeInfo metadata,
		MbMessageAssembly inAssembly, MbMessageAssembly outAssembly) throws Exception {
		super.prepareForTransformation(metadata, inAssembly, outAssembly);		
		
		try {			
			metadata.addUserDefinedProperty("IncidentArea", (String) getUserDefinedAttribute("INCIDENT_AREA"));
		} catch(Exception e) {
			// Never throw an exception from logging compute
			appLogger.throwing(e);
		}
	}

}
