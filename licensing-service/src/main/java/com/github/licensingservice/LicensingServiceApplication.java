package com.github.licensingservice;

import com.github.licensingservice.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableCircuitBreaker
//@EnableFeignClients
public class LicensingServiceApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        RestTemplate template =  new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (interceptors == null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }else {
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
    }
   public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

}
