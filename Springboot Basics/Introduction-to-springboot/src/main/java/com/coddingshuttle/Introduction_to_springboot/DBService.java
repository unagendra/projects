package com.coddingshuttle.Introduction_to_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBService {

   final private DB db;

    public DBService(DB db) {
        this.db=db;
    }

    String getData(){
      return db.getData();
    }
}
