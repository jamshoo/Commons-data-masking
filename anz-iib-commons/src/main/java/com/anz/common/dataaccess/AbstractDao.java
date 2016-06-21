package com.anz.common.dataaccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractDao<A, B extends Serializable, C extends JpaRepository<A, B> > implements IDao<A, B, C> {

	// Default logger
	private static Logger logger = LogManager.getLogger(); 
	
	@Autowired
	private C repository;
	
	@Autowired
	private EntityManager entityManager;					
	
	public C getRepository() {
		return repository;
	}
	
	public void setRepository(C repository) {
		this.repository = repository;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public List<A> findAll() {
		
		List<A> results = new ArrayList<A>();
		
		for(A entity : getRepository().findAll()) {
			results.add(entity);
		}
		return results;
	}

	public List<A> findAll(Iterable<B> arg0) {
		return getRepository().findAll(arg0);
	}
	
	public <S extends A> S save(S entity) {
		logger.info("event=save, entity_type=" + entity.getClass().getSimpleName() + ", entity=" + entity.toString());
		return getRepository().save(entity);
	}

	public A findOne(B id) {
		return getRepository().findOne(id);
	}

	public boolean exists(B id) {
		return getRepository().exists(id);
	}

	public long count() {
		return getRepository().count();
	}

	public void delete(B id) {
		getRepository().delete(id);
	}

	public void delete(A entity) {
		getRepository().delete(entity);
	}

	public void delete(Iterable<? extends A> entities) {
		getRepository().delete(entities);
	}

	public void deleteAll() {
		getRepository().deleteAll();
	}
	
	public <S extends A> List<S> save(Iterable<S> arg0) {
		return getRepository().save(arg0);
	}

	public void deleteAllInBatch() {
		getRepository().deleteAllInBatch();
		
	}

	public void deleteInBatch(Iterable<A> arg0) {
		getRepository().deleteInBatch(arg0);
		
	}

	public List<A> findAll(Sort arg0) {
		return getRepository().findAll(arg0);
	}

	public void flush() {
		getRepository().flush();		
	}

	public A getOne(B arg0) {
		return getRepository().getOne(arg0);
	}

	public <S extends A> S saveAndFlush(S arg0) {
		return getRepository().saveAndFlush(arg0);
	}

	public Page<A> findAll(Pageable arg0) {
		return getRepository().findAll(arg0);
	}

	public <S extends A> List<S> findAll(Example<S> arg0, Sort arg1) {
		return findAll(arg0, arg1);
	}

	public <S extends A> long count(Example<S> arg0) {
		return count(arg0);
	}
	
	public <S extends A> List<S> findAll(Example<S> paramExample) {
		return getRepository().findAll(paramExample);
	}
	
	public <S extends A> Page<S> findAll(Example<S> paramExample,
			Pageable paramPageable) {
		return getRepository().findAll(paramExample,paramPageable);
	}
	
	public <S extends A> S findOne(Example<S> paramExample) {
		return getRepository().findOne(paramExample);
	}
	
	public <S extends A> boolean exists(Example<S> paramExample) {
		return getRepository().exists(paramExample);
	}
}

