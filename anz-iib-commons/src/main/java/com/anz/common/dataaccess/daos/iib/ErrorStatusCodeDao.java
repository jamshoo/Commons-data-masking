package com.anz.common.dataaccess.daos.iib;

import com.anz.common.dataaccess.AbstractDao;
import com.anz.common.dataaccess.daos.IErrorStatusCodeDao;
import com.anz.common.dataaccess.daos.iib.repos.ErrorStatusCodeRepository;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;

public class ErrorStatusCodeDao extends AbstractDao<ErrorStatusCode, String, ErrorStatusCodeRepository> implements IErrorStatusCodeDao {


}
