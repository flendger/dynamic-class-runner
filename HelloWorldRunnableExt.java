package ru.flendger.dynamic.dynamicrunner.testrunnables;

import lombok.RequiredArgsConstructor;
import ru.flendger.dynamic.dynamicrunner.web.DynamicRunController;

@RequiredArgsConstructor
public class HelloWorldRunnableExt implements Runnable {
    private final DynamicRunController controller;

    @Override
    public void run() {
        System.out.println("Hello, World!!! ");
        System.out.println(controller.getClass());
        System.out.println(getClass());
    }
}
