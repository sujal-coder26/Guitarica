package com.web.guitarapp.dao;

import com.web.guitarapp.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  @Query("select u from User u where u.email = :email")
  User getUserByUserName(@Param("email") String email);
}
