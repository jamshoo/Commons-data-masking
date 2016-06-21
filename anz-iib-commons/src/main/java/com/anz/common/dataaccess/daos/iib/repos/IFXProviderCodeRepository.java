package com.anz.common.dataaccess.daos.iib.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anz.common.dataaccess.models.iib.IFXProviderCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCodePk;

public interface IFXProviderCodeRepository extends JpaRepository<IFXProviderCode, IFXProviderCodePk> {
	
	public List<IFXProviderCode> findByProviderIdAndCode(String provider, String code);

}
