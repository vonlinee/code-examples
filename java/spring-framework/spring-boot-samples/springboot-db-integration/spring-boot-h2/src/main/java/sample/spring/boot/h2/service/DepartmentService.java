// Java Program to Demonstrate DepartmentService File

package sample.spring.boot.h2.service;
// Importing required classes
import java.util.List;

import sample.spring.boot.h2.entity.Department;

// Interface
public interface DepartmentService {

	// Save operation
	Department saveDepartment(Department department);

	// Read operation
	List<Department> fetchDepartmentList();

	// Update operation
	Department updateDepartment(Department department,
								Long departmentId);

	// Delete operation
	void deleteDepartmentById(Long departmentId);
}
