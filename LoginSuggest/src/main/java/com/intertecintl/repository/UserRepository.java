package com.intertecintl.repository;

import org.springframework.data.repository.CrudRepository;

import com.intertecintl.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
