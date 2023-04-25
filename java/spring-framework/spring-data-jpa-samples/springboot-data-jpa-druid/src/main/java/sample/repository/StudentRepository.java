package sample.repository;

import org.springframework.data.repository.CrudRepository;
import sample.entity.Student;

public interface StudentRepository extends CrudRepository<Student, String> {
}
