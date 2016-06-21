/**
 * 
 */
package com.anz.common.ioc.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.broker.plugin.MbNode;

/**
 * @author sanketsw
 * 
 */
public class MbNodefactory {

	private static final Logger logger = LogManager.getLogger();

	private MbNode mbNode;

	private static MbNodefactory _inst = null;

	private MbNodefactory() {
	}

	public static MbNodefactory getInstance() {
		if (_inst == null) {
			_inst = new MbNodefactory();
		}
		return _inst;
	}

	/**
	 * @return the mbNode
	 */
	public MbNode getMbNode() {
		logger.debug("Get mbnode={}", mbNode);
		return mbNode;
	}

	/**
	 * @param mbNode  the mbNode to set
	 */
	public void setMbNode(MbNode mbNode) {
		if (this.mbNode == null && mbNode != null) {
			logger.debug("Set mbnode={}", mbNode != null ? mbNode.getName() : null);
			this.mbNode = mbNode;
		}
	}

}
