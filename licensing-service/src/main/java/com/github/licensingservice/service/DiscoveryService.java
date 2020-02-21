package com.github.licensingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    @Autowired
    public DiscoveryService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public List getEurekaServices() {
        List<String> services = new ArrayList<>();

        discoveryClient.getServices().forEach(
                serviceName -> discoveryClient.getInstances(serviceName).forEach(
                        serviceInstance -> services.add(String.format("%s:%s", serviceName, serviceInstance.getUri()))
                )
        );
        return services;
    }
}
