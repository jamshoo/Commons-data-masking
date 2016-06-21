/**
 * 
 */
package com.anz.common.transform;

import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;


public interface ITransformer<I,O> {
	O execute(I input, Logger appLogger, ComputeInfo metadata) throws Exception;
	
}
