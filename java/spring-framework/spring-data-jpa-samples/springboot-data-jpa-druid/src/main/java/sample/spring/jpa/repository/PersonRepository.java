package sample.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.spring.jpa.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}