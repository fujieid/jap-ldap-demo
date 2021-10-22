package com.fujieid.jap.demo.ldap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.annotation.Resource;

@SpringBootApplication
public class JapLdapDemoApplication implements ApplicationRunner {
    @Resource
    private ServerProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(JapLdapDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("http://localhost:" + properties.getPort());
    }
}
