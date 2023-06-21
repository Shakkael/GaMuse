package com.gamuse.gamuse.repository;

import org.springframework.data.repository.CrudRepository;

import com.gamuse.gamuse.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

}
