package com.github.organization;

import com.github.organization.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
@RefreshScope
//@EnableResourceServer
public class OrganizationApplication {
    //    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }
}
