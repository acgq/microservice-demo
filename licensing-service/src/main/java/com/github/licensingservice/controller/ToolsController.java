package com.github.licensingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/tools")
public class ToolsController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/eureka/services")
    public List<String> getEurekaServices() {
        return discoveryClient.getServices();
    }
}
