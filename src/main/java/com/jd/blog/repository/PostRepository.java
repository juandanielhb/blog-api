package com.jd.blog.repository;

import com.jd.blog.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PostRepository extends MongoRepository<Post, String> {
}
