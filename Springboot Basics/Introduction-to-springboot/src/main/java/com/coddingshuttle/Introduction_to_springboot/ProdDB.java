package com.coddingshuttle.Introduction_to_springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;



@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "production")
public class ProdDB implements DB {
    @Override
    public String getData() {
      return "PROD DATA";
    }
}
