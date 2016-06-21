package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.OperationRepository;
import com.anz.common.dataaccess.models.iib.Operation;

public interface IOperationDao extends OperationRepository, IDao<Operation, String, OperationRepository> {

}
