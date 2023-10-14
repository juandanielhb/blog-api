package com.jd.blog.controllers;

import com.jd.blog.model.Comment;
import com.jd.blog.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Comment> getComments(@PathVariable String postId) {
        log.info("GET /api/posts/{}/comments - Retrieving all comments for post with ID: {}", postId, postId);
        return commentService.getComments(postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment create(@PathVariable String postId, @RequestBody Comment comment) {
        log.info("POST /api/posts/{}/comments - Creating a new comment", postId);
        return commentService.create(postId, comment);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{commentId}")
    public Comment update(@PathVariable String postId, @PathVariable String commentId, @RequestBody Comment comment) {
        log.info("PUT /api/posts/{}/comments/{} - Updating comment with ID: {}", postId, commentId, comment);
        return commentService.update(postId, commentId, comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{commentId}")
    public void delete(@PathVariable String postId, @PathVariable String commentId) {
        log.info("DELETE /api/posts/{}/comments/{} - Deleting comment with ID: {}", postId, commentId, commentId);
        commentService.delete(postId, commentId);
    }
}