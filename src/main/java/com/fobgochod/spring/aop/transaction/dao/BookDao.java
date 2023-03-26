package com.fobgochod.spring.aop.transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 减库存，减去某本书的库存
     *
     * @param id 图书ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateStock(int id) {
        String sql = "update book set stock = stock-1 where id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 按照图书的ID来获取图书的价格
     *
     * @param id 图书ID
     * @return 图书价格
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int getPrice(int id) {
        String sql = "select price from book where id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    /**
     * 减去某个用户的余额
     *
     * @param username 用户名
     * @param price    书本价格
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void updateBalance(String username, int price) {
        String sql = "update user set balance = balance-? where name = ?";
        jdbcTemplate.update(sql, price, username);
    }
}
