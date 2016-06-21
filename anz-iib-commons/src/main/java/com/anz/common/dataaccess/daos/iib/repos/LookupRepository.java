package com.anz.common.dataaccess.daos.iib.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anz.common.dataaccess.models.iib.Lookup;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, String> {

}
