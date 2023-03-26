package com.fobgochod.spring.ioc.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Multiple lifecycle mechanisms configured for the same bean, with different initialization methods, are called as follows:
 *
 * <ol>
 *     <li>Methods annotated with {@link javax.annotation.PostConstruct}</li>
 *     <li><code>afterPropertiesSet()</code> as defined by the {@link InitializingBean} callback interface</li>
 *     <li>A custom configured <code>init()</code> method</li>
 * </ol>
 * <p>
 * Destroy methods are called in the same order:
 *
 * <ol>
 *     <li>Methods annotated with {@link javax.annotation.PreDestroy}</li>
 *     <li><code>destroy()</code> as defined by the {@link DisposableBean} callback interface</li>
 *     <li>A custom configured <code>destroy()</code> method</li>
 * </ol>
 *
 * @author fobgochod
 * @date 2023/3/9 23:15
 */
public class LifecycleApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("config/lifecycle.xml");

        // add a shutdown hook for the above context...
        ctx.registerShutdownHook();

        // app runs here...

        // main method exits, hook is called prior to the app shutting down...
    }
}
