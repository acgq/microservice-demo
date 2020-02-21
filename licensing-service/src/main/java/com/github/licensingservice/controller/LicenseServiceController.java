package com.github.licensingservice.controller;

import com.github.licensingservice.config.ServiceConfig;
import com.github.licensingservice.model.License;
import com.github.licensingservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @Autowired
    private LicenseService licenseService;
    @Autowired
    ServiceConfig config;


    @GetMapping(value = "/")
    public List<License> getLicense(@PathVariable("organizationId") String organizationId) {
        List<License> licensesGetByOrganizationId = licenseService.getLicenseByOrganizationId(organizationId);
        return licensesGetByOrganizationId;
    }

    @GetMapping(value = "{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(organizationId, licenseId);
        return license;
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId,
                                         @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId,licenseId,clientType);
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
    public String deleteLicense(@PathVariable("licenseId") String licenseId) {
        return String.format("this is delete");
    }
}
