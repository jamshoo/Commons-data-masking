package com.anz.common.dataaccess.daos;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.anz.common.ioc.spring.TestSpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringConfig.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractDaoTest {

}
