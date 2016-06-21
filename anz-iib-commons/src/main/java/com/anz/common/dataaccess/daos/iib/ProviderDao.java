package com.anz.common.dataaccess.daos.iib;

import com.anz.common.dataaccess.AbstractDao;
import com.anz.common.dataaccess.daos.IProviderDao;
import com.anz.common.dataaccess.daos.iib.repos.ProviderRepository;
import com.anz.common.dataaccess.models.iib.Provider;

public class ProviderDao extends AbstractDao<Provider, String, ProviderRepository> implements IProviderDao {

}
