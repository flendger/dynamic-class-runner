package ru.flendger.dynamic.dynamicrunner.testrunnables;

import lombok.RequiredArgsConstructor;
import ru.flendger.dynamic.dynamicrunner.web.DynamicRunController;

@RequiredArgsConstructor
public class HelloWorldRunnable implements Runnable {
    private final DynamicRunController controller;

    @Override
    public void run() {
        System.out.println("Hello, World!!! 26563465465");
        System.out.println(controller.getClass());
    }
}
