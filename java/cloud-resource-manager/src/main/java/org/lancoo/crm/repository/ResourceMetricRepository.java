package org.lancoo.crm.repository;

import org.lancoo.crm.entity.ResourceMetricInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceMetricRepository extends JpaRepository<ResourceMetricInfo, String> {
    Page<ResourceMetricInfo> findByStatus(String status, Pageable pageable);

    Page<ResourceMetricInfo> findByRegion(String region, Pageable pageable);

    Page<ResourceMetricInfo> findByCpuUsageLessThan(double cpuUsage, Pageable pageable);
}