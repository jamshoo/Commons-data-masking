package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.ErrorStatusCodeRepository;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;

public interface IErrorStatusCodeDao extends ErrorStatusCodeRepository, IDao<ErrorStatusCode, String, ErrorStatusCodeRepository> {

}
