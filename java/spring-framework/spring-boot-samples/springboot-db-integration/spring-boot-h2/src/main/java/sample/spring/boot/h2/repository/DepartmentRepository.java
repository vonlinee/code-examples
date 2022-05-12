package sample.spring.boot.h2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sample.spring.boot.h2.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
	
}
