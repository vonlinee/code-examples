package org.lancoo.crm.repository;

import org.lancoo.crm.entity.ResourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceInfoRepository extends JpaRepository<ResourceInfo, String> {

}