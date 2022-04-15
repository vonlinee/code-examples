package io.spring.boot.common.web.dao;

import java.util.List;

import io.spring.boot.common.web.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUserDao extends CrudRepository<User, Integer> {

    @Query("select t from User t where t.username = :name")
    User findByName(@Param("name") String name);

    @Query("select t from User t ")
    List<User> getList();
}
