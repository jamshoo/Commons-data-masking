package com.anz.common.dataaccess.daos.iib.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anz.common.dataaccess.models.iib.ErrorStatusCode;

public interface ErrorStatusCodeRepository extends JpaRepository<ErrorStatusCode, String> {

}