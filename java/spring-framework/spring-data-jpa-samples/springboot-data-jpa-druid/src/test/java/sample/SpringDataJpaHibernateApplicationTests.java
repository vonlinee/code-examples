package sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.repository.PersonRepository;
import sample.entity.Person;

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
