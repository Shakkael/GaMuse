package com.gamuse.gamuse.repository;

import org.springframework.data.repository.CrudRepository;

import com.gamuse.gamuse.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}