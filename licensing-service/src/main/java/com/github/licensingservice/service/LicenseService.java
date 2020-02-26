package com.github.licensingservice.service;

import com.github.licensingservice.clients.OrganizationDiscoveryClient;
import com.github.licensingservice.clients.OrganizationFeignClient;
import com.github.licensingservice.clients.OrganizationRestTemplateClient;
import com.github.licensingservice.config.ServiceConfig;
import com.github.licensingservice.model.License;
import com.github.licensingservice.model.Organization;
import com.github.licensingservice.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {
    private LicenseRepository licenseRepository;
    private ServiceConfig serviceConfig;
    private OrganizationRestTemplateClient restTemplateClients;
    private OrganizationDiscoveryClient discoveryClient;

    @Autowired
    public LicenseService(LicenseRepository licenseRepository, ServiceConfig serviceConfig, OrganizationRestTemplateClient restTemplateClients, OrganizationDiscoveryClient discoveryClient) {
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;
        this.restTemplateClients = restTemplateClients;
        this.discoveryClient = discoveryClient;
    }

    //    @Autowired
    private OrganizationFeignClient feignClient;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    public License getLicense(String organizationId, String licenseId) {
        randomlyRunLong();
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
//        return new License()
//                .withId(licenseId)
//                .withOrganizationId(UUID.randomUUID().toString())
//                .withProductName("Test product name")
//                .withLicenseType("PerSeat");
    }

    private void randomlyRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicenseByOrganizationId(String organizationId) {
        randomlyRunLong();
        List<License> licenses = licenseRepository.findByOrganizationId(organizationId);
        return licenses;
    }

    private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList=new ArrayList<>();
        License license = new License()
                .withId("0000-0000-000")
                .withOrganizationId(organizationId)
                .withProductName("no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    public void saveLicense(License license) {
        License licenseWithId = license.withId(UUID.randomUUID().toString());
        licenseRepository.save(licenseWithId);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license);
    }

    public Organization retrieveOrganization(String organizationId, String clientType) {
        Organization organization = null;
        switch (clientType) {
            case "feign":
                System.out.println("正在使用feign客户端");
                organization = feignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("正在使用rest客户端");
                organization = restTemplateClients.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("正在使用discovery客户端");
                organization = discoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = restTemplateClients.getOrganization(organizationId);
        }
        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        Organization organization = retrieveOrganization(organizationId, clientType);
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(serviceConfig.getExampleProperty());
    }
}
