package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.ProviderRepository;
import com.anz.common.dataaccess.models.iib.Provider;

public interface IProviderDao extends ProviderRepository, IDao<Provider, String, ProviderRepository> {

}
