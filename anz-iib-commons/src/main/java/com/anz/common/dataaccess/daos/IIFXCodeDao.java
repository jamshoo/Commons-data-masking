package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.IFXCodeRepository;
import com.anz.common.dataaccess.models.iib.IFXCode;

public interface IIFXCodeDao extends IFXCodeRepository, IDao<IFXCode, String, IFXCodeRepository> {

}
