package com.fobgochod.spring.aop.transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事物传播特性厕所
 *
 * @author fobgochod
 * @date 2022/10/10 23:00
 * @see Propagation
 */
@Repository
public class PropagationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        String sql = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(sql, 101);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 102);
        jdbcTemplate.update(insert, 102);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 103);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 104);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 105);
        jdbcTemplate.update(insert, 105);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 106);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 107);
        jdbcTemplate.update(insert, 107);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested2() {
        String insert = "INSERT INTO user (id, name, balance) VALUES (?, 'lucy', 3000)";
        jdbcTemplate.update(insert, 107);
    }
}
