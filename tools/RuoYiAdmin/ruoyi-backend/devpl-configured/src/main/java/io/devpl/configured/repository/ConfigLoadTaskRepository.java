package io.devpl.configured.repository;

import io.devpl.configured.entity.ConfigLoadTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigLoadTaskRepository extends JpaRepository<ConfigLoadTask, Integer> {
    
}