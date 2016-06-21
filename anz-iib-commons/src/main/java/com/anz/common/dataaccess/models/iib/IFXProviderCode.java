package com.anz.common.dataaccess.models.iib;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(IFXProviderCodePk.class) 
public class IFXProviderCode {

	@Id
	@ManyToOne
	private Provider provider;
	
	@Id
	private String code;
	
	@ManyToOne
	private IFXCode ifxCode;

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public IFXCode getIfxCode() {
		return ifxCode;
	}

	public void setIfxCode(IFXCode ifxCode) {
		this.ifxCode = ifxCode;
	}
}
