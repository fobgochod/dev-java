package com.fobgochod.spring.aop.transaction.service;

import com.fobgochod.spring.aop.transaction.dao.PropagationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * PropagationService.java
 * <p>
 * {@link Propagation}               | 有              | 无
 * {@link Propagation#REQUIRED}      | 有              | 有
 * {@link Propagation#SUPPORTS}      | 有              | 无
 * {@link Propagation#MANDATORY}     | 有              | 报错
 * {@link Propagation#REQUIRES_NEW}  | 挂起外层，新开事物 | 新开事物
 * {@link Propagation#NOT_SUPPORTED} | 无              | 无
 * {@link Propagation#NEVER}         | 报错            | 无
 * {@link Propagation#NESTED}        | 嵌套事物         | 有
 * <p>
 * <p>
 *
 *
 * <ol>
 *      <h3>总结下NESTED的回滚特性</h3>
 *      <li>主事务和嵌套事务属于同一个事务</li>
 *      <li>嵌套事务出错回滚不会影响到主事务</li>
 *      <li>主事务回滚会将嵌套事务一起回滚了</li>
 * </ol>
 *
 * @author fobgochod
 * @date 2022/10/10 23:35
 */
@Service
public class PropagationService {

    @Autowired
    private PropagationDao propagationDao;

    /**
     * 外层：没有事物，
     * 内层：有事物
     */
    public void required() {
        propagationDao.required();
    }

    /**
     * 外层：没有事物，
     * 内层：没有事物
     */
    public void supports() {
        propagationDao.supports();
    }

    /**
     * 外层：有事物，
     * 内层：有事物
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void supports2() {
        propagationDao.supports();
    }

    /**
     * 外层：没有事物
     * 内层：报错
     */
    public void mandatory() {
        propagationDao.mandatory();
    }

    /**
     * 外层：有事物
     * 内层：有事物
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void mandatory2() {
        propagationDao.mandatory();
    }

    /**
     * 外层：有事物
     * 内层：新开事物
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void requiresNew() {
        propagationDao.required();
        try {
            propagationDao.requiresNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 外层：有事物
     * 内层：没有事物
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void notSupported() {
        propagationDao.notSupported();
    }

    /**
     * 外层：有事物
     * 内层：报错
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void never() {
        propagationDao.never();
    }

    /**
     * 嵌套事务出错回滚不会影响到主事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void nested() {
        propagationDao.required();
        try {
            propagationDao.nested();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 主事务回滚会将嵌套事务一起回滚了
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void nested2() {
        propagationDao.required();
        propagationDao.nested2();

        int i = 1 / 0;
    }
}
