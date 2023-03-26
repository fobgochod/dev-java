package com.fobgochod.spring.manipulation.propertyeditor;

import com.fobgochod.spring.manipulation.propertyeditor.editor.AddressPropertyEditorRegistrar;
import com.fobgochod.spring.manipulation.propertyeditor.editor.Customer;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.fobgochod.spring.manipulation.propertyeditor.editor")
public class PropertyEditorApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PropertyEditorApplication.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(Customer.class));
    }

    @Bean
    public static CustomEditorConfigurer editorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new AddressPropertyEditorRegistrar()});
        return customEditorConfigurer;
    }
}
