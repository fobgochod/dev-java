package com.fobgochod.spring.ioc.annotation.populatebean;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import com.fobgochod.spring.ioc.annotation.populatebean.populate.service.PopulateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AutowiredApplication.java
 * <p>
 * DefaultListableBeanFactory.java:1234<br>
 * doResolveDependency(descriptor, requestingBeanName, autowiredBeanNames, typeConverter)
 * <p>
 * findAutowireCandidates(beanName, type, descriptor)
 * <p>
 * determineAutowireCandidate(matchingBeans, descriptor);
 * <p>
 * <p>
 * 1、优先处理 @Qualifier
 * 如果没有：
 * 2、按类型查找所有符合要求的bean
 * <p>
 * 如果大于1个：
 * 3、@Primary
 * 4、@Priority数字较小的优先级大
 * 5、按注入名称匹配
 *
 * @author fobgochod
 * @date 2022/4/9 13:37
 * @see AutowiredAnnotationBeanPostProcessor
 * @see org.springframework.beans.factory.support.AbstractBeanDefinition
 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
 */
@Configuration
@ComponentScan("com.fobgochod.spring.ioc.annotation.populatebean.populate")
public class AutowiredApplication {

    @Autowired
    @Qualifier("qualifierServiceImpl")
    private PopulateService populateService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AutowiredApplication.class);
        ac.refresh();

        AutowiredApplication app = ac.getBean(AutowiredApplication.class);
        PopulatePojo populatePojo = app.populateService.info(Autowired.class.getSimpleName());
        System.out.println("populatePojo = " + populatePojo);
    }
}
