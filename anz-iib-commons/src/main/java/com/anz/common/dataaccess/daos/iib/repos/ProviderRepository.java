package com.anz.common.dataaccess.daos.iib.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anz.common.dataaccess.models.iib.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {

}
