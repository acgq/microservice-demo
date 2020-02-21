package com.github.organization.repository;

import com.github.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    Organization findOrganizationById(String organizationId);
}
