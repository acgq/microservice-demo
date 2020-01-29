package com.acgq.microservicedemo.controller;

import com.acgq.microservicedemo.model.License;
import com.acgq.microservicedemo.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {


    private LicenseService licenseService;

    @GetMapping(value = "{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Test product")
                .withLicenseType("Seat");
    }

    @PutMapping(value = "{licenseId}")
    public String updateLicense(@PathVariable("licenseId") String licenseId) {
        return String.format("this is the put");
    }

    @PostMapping(value = "{licenseId}")
    public String saveLicense(@PathVariable("licenseId") String licenseId) {
        return String.format("this is the post");
    }

    @DeleteMapping(value = "{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicense(@PathVariable("licenseId") String licenseId){
        return String.format("this is delete");
    }
}
