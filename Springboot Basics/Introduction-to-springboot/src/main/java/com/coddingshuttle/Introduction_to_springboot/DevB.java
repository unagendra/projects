package com.coddingshuttle.Introduction_to_springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "development")
public class DevB implements DB{
    @Override
    public String getData() {
        return  "Dev DATA";
    }
}
