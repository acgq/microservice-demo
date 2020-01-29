package com.acgq.microservicedemo.service;

import com.acgq.microservicedemo.model.License;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LicenseService {

    public License getLicense(String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(UUID.randomUUID().toString())
                .withProductName("Test product name")
                .withLicenseType("PerSeat");
    }

    public void saveLicense(License license) {

    }

    public void updateLicense(License license) {

    }

    public void deleteLicense(License license) {

    }
}
