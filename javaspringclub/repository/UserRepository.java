package com.javaspringclub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javaspringclub.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
}