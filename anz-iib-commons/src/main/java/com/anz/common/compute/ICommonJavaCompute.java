/**
 * 
 */
package com.anz.common.compute;

/**
 * @author root
 *
 */
public interface ICommonJavaCompute {
	
	/**
	 * define the type of transformation this compute node is doing
	 * @return One of the constants from 
	 */
	public TransformType getTransformationType();
	

}
