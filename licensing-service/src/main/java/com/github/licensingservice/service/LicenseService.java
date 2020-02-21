package com.github.licensingservice.service;

import com.github.licensingservice.clients.OrganizationDiscoveryClient;
import com.github.licensingservice.clients.OrganizationFeignClient;
import com.github.licensingservice.clients.OrganizationRestTemplateClient;
import com.github.licensingservice.config.ServiceConfig;
import com.github.licensingservice.model.License;
import com.github.licensingservice.model.Organization;
import com.github.licensingservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
//        return new License()
//                .withId(licenseId)
//                .withOrganizationId(UUID.randomUUID().toString())
//                .withProductName("Test product name")
//                .withLicenseType("PerSeat");
    }

    public List<License> getLicenseByOrganizationId(String organizationId) {
        List<License> licenses = licenseRepository.findByOrganizationId(organizationId);
        return licenses;
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
