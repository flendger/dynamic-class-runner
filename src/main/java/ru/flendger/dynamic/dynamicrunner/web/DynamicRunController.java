package ru.flendger.dynamic.dynamicrunner.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.dynamic.dynamicrunner.config.ApplicationDynamicBeanLoader;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class DynamicRunController {
    private final BeanFactory beanFactory;
    private final ApplicationDynamicBeanLoader beanLoader;

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
                                Path.of("./HelloWorldRunnableExt.java")));

        beanLoader.load(sourceRunnable);
    }
}
