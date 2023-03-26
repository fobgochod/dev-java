package com.fobgochod.spring.ioc.annotation.populatebean;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import com.fobgochod.spring.ioc.annotation.populatebean.populate.service.PopulateService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * ResourceApplication.java
 * <p>
 * 1、如指定name属性指定名称，则按按名称匹配
 * <p>
 * 2、如果没有指定name属性
 * 2.1 先按名称匹配
 * 2.2、如果没有：按照@Autowired流程处理
 * <p>
 * <p>
 * autowireResource(this.resourceFactory, element, requestingBeanName)
 *
 * @author fobgochod
 * @date 2022/4/9 13:37
 * @see CommonAnnotationBeanPostProcessor
 */
@Configuration
@ComponentScan("com.fobgochod.spring.ioc.annotation.populatebean.populate")
public class ResourceApplication {

    @Resource(name = "byNameServiceImpl")
    @Qualifier("qualifierServiceImpl")
    private PopulateService populateService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(ResourceApplication.class);
        ac.refresh();

        ResourceApplication app = ac.getBean(ResourceApplication.class);
        PopulatePojo populatePojo = app.populateService.info(Resource.class.getSimpleName());
        System.out.println("populatePojo = " + populatePojo);
    }
}
