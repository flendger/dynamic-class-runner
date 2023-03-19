package ru.flendger.dynamic.dynamicrunner.config;

import com.javax0.sourcebuddy.Compiler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationDynamicBeanLoader {
    private final DefaultListableBeanFactory configurableBeanFactory;

    public void load(String source) throws Exception {
        Class<?> loadedClass = Compiler
                .java()
                .from(source)
                .compile()
                .load(Compiler.LoaderOption.REVERSE)
                .get();

        String beanName = loadedClass.getName();
        if (configurableBeanFactory.containsBean(beanName)) {
            configurableBeanFactory.destroyBean(beanName);
        }

        if (configurableBeanFactory.containsBeanDefinition(beanName)) {
            configurableBeanFactory.removeBeanDefinition(beanName);
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(loadedClass)
                .getBeanDefinition();

        configurableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
