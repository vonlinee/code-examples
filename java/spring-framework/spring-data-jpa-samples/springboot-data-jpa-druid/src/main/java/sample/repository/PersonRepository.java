package sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}