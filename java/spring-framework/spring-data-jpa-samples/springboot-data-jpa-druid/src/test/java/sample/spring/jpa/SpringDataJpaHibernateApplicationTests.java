package sample.spring.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.spring.jpa.entity.Person;
import sample.spring.jpa.repository.PersonRepository;

@SpringBootTest
class SpringDataJpaHibernateApplicationTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    void contextLoads() {
        Person person = new Person();
        person.setId(1L);
        person.setAge(30);
        person.setName("zs");
        final Person p = personRepository.save(person);
        System.out.println(p == person);
        System.out.println(p);
    }

}
