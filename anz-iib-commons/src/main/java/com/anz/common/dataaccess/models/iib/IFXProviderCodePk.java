package com.anz.common.dataaccess.models.iib;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class IFXProviderCodePk implements Serializable {

	private static final long serialVersionUID = -812220252024925930L;

	private String provider;

	private String code;

	public IFXProviderCodePk() {
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		IFXProviderCodePk rhs = (IFXProviderCodePk) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(provider, rhs.provider).append(code, rhs.code)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
