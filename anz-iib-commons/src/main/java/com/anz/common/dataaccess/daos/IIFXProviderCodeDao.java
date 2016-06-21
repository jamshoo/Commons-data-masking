package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.IFXProviderCodeRepository;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCodePk;

public interface IIFXProviderCodeDao extends IFXProviderCodeRepository, IDao<IFXProviderCode, IFXProviderCodePk, IFXProviderCodeRepository> {

}
