package com.github.organization.services;

import com.github.organization.model.Organization;
import com.github.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrganizationById(String organizationId) {
        return organizationRepository.findOrganizationById(organizationId);
    }

    public void saveOrganization(Organization organization) {
        organizationRepository.save(organization);
    }

    public void deleteOrganization(Organization organization) {
        organizationRepository.delete(organization);
    }
}
