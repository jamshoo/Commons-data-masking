package com.anz.common.dataaccess.daos.iib;

import java.util.List;

import com.anz.common.dataaccess.AbstractDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.iib.repos.IFXProviderCodeRepository;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCodePk;

public class IFXProviderCodeDao extends AbstractDao<IFXProviderCode, IFXProviderCodePk, IFXProviderCodeRepository> implements IIFXProviderCodeDao {

	public List<IFXProviderCode> findByProviderIdAndCode(String provider,
			String ifxCode) {
		return getRepository().findByProviderIdAndCode(provider, ifxCode);
	}

}
