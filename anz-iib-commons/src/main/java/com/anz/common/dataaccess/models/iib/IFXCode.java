package com.anz.common.dataaccess.models.iib;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.anz.common.dataaccess.ICommonEntity;

@Entity
public class IFXCode implements ICommonEntity {
	
	public static final String SEV_CRITICAL = "Critical";
	public static final String SEV_NORMAL = "Normal";
	public static final String SEV_MINOR = "Minor";
	
	public static final String STATUS_FAILURE = "Failure";
	public static final String STATUS_WARNING = "Warning";
	public static final String STATUS_IGNORE = "Ignore";

	@Id
	private String code;
	
	private String severity;
	
	private String status;
	
	private String descr;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}


	/* (non-Javadoc)
	 * @see com.anz.common.dataaccess.ICommonEntity#getKey()
	 */
	public String getKey() {
		return code;
	}
}
