package sample.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import sample.spring.jpa.entity.Student;

public interface StudentRepository extends CrudRepository<Student, String> {
}
