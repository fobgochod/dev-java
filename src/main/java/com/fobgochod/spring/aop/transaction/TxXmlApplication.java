package com.fobgochod.spring.aop.transaction;

import com.fobgochod.spring.aop.transaction.service.BookService;
import com.fobgochod.spring.CGlibUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  XmlTransactionApplication.java
 *
 * @author fobgochod
 * @date 2022/4/13 0:20
 */
public class TxXmlApplication {

    public static void main(String[] args) throws Exception {
        CGlibUtils.generatedCGlibProxyFiles("tx");
        ApplicationContext context = new ClassPathXmlApplicationContext("config/transaction.xml");
        BookService bookService = context.getBean(BookService.class);
        bookService.checkout("zhangsan", 1);
    }
}
