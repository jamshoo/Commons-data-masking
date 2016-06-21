package com.anz.common.dataaccess;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IDao<A, B extends Serializable, C extends JpaRepository<A, B>> extends JpaRepository<A, B> {
	C getRepository();
	EntityManager getEntityManager();
}
