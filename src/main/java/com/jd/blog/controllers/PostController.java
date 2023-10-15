package com.jd.blog.controllers;

import com.jd.blog.model.Post;
import com.jd.blog.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Post> getPosts() {
        log.info("GET /api/posts - Retrieving all posts");
        return postService.getPosts();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post create(@RequestBody Post post) {
        log.info("POST /api/posts - Creating a new post");
        return postService.create(post);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public Post update(@PathVariable String id, @RequestBody Post post) {
        log.info("PUT /api/posts/{} - Updating post with ID: {}", id, post);
        return postService.update(id, post);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        log.info("DELETE /api/posts/{} - Deleting post with ID: {}", id);
        postService.delete(id);
    }
}
