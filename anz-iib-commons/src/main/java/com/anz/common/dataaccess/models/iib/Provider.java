package com.anz.common.dataaccess.models.iib;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Provider {

	@Id
	private String id;
	
	private String descr;
	
	@OneToMany
	private List<IFXProviderCode> ifxProviderCodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public List<IFXProviderCode> getIfxProviderCodes() {
		return ifxProviderCodes;
	}
	
	public void setIfxProviderCodes(List<IFXProviderCode> ifxProviderCodes) {
		this.ifxProviderCodes = ifxProviderCodes;
	}
}
