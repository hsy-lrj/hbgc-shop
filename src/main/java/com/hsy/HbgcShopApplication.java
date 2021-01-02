package com.hsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//扫描servlet
@ServletComponentScan("com.hsy.filter")
public class HbgcShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbgcShopApplication.class, args);
    }


}
