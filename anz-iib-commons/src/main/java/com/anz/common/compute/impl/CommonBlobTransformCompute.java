/**
 * 
 */
package com.anz.common.compute.impl;

import com.anz.common.compute.TransformType;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public abstract class CommonBlobTransformCompute extends CommonJavaCompute {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.UNSPECIFIED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.anz.common.compute.impl.CommonJavaCompute#execute(com.ibm.broker.
	 * plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		ITransformer<String, String> stringTransformer = getTransformer();
		if (stringTransformer != null) {

			MbMessage inMessage = inAssembly.getMessage();
			MbMessage outMessage = outAssembly.getMessage();

			String inputString = ComputeUtils.getStringFromBlob(inMessage);

			String outputString = stringTransformer.execute(inputString,
					appLogger, metaData);
			
			if (outputString != null) {
				// Write this outputJson to outMessage
				ComputeUtils.replaceStringAsBlob(outMessage, outputString);
			}
		}

	}

	/**
	 * Get the external transformer class instance
	 * 
	 * @return
	 */
	public abstract ITransformer<String, String> getTransformer();

}
