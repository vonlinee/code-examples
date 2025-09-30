package org.lancoo.crm.repository;

import org.lancoo.crm.entity.CronTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronTaskRepository extends JpaRepository<CronTask, String> {

}