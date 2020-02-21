package com.github.organization.controllers;

import com.github.organization.model.Organization;
import com.github.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController {
    private OrganizationService organizationService;

    @Autowired
    public OrganizationServiceController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(value = "/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return organizationService.getOrganizationById(organizationId);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        organizationService.saveOrganization(organization);
    }

    @DeleteMapping(value = "{organizationId}")
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        organizationService.deleteOrganization(organization);
    }
}
