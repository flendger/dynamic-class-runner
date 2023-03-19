package ru.flendger.dynamic.dynamicrunner.web;

import com.javax0.sourcebuddy.Compiler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class DynamicRunController {
    private final BeanFactory beanFactory;
    private final DefaultListableBeanFactory configurableBeanFactory;

    @GetMapping("/run")
    public void run() {
        Runnable runnable = beanFactory.getBean(Runnable.class);
        runnable.run();
    }

    @GetMapping("/load")
    public void load() throws Exception {
        String sourceRunnable =
                new String(Files
                        .readAllBytes(
                                Path.of("/home/flendger/IdeaProjects/dynamic-class-runner/src/main/java/ru/flendger/dynamic/dynamicrunner/testrunnables/HelloWorldRunnable.java")));

        Class<?> loadedClass = Compiler
                .java()
                .from(sourceRunnable)
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
