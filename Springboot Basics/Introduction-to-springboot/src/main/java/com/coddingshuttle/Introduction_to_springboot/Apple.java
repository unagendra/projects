package com.coddingshuttle.Introduction_to_springboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")//bean
public class Apple {

    void eatApple(){
        System.out.println("This is an apple");
    }

    @PostConstruct
    void callBeanIntialized(){
        System.out.println("Bean is intialized");
    }

    @PreDestroy
    void callBeforeBeanDestroyed(){
        System.out.println("Bean is destroyed");
    }



}
