package com.anz.common.dataaccess.models.iib;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.anz.common.dataaccess.ICommonEntity;

@Entity
public class Operation implements ICommonEntity {

	@Id
	private String qualifier;
	
	public static final String ADD = "Add";
	
	
	String imeplementation;
	
	String operation;

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * @return the imeplementation
	 */
	public String getImeplementation() {
		return imeplementation;
	}

	/**
	 * @param imeplementation the imeplementation to set
	 */
	public void setImeplementation(String imeplementation) {
		this.imeplementation = imeplementation;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/* (non-Javadoc)
	 * @see com.anz.common.dataaccess.ICommonEntity#getKey()
	 */
	public String getKey() {
		return qualifier;
	}

	
	
}
