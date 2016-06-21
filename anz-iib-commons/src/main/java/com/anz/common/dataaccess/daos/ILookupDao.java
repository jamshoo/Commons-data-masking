package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.LookupRepository;
import com.anz.common.dataaccess.models.iib.Lookup;

public interface ILookupDao extends LookupRepository, IDao<Lookup, String, LookupRepository> {

}
