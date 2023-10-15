package com.jd.blog.services;

import com.jd.blog.exceptions.ResourceCreationException;
import com.jd.blog.exceptions.ResourceNotFoundException;
import com.jd.blog.model.Comment;
import com.jd.blog.model.User;
import com.jd.blog.repository.CommentRepository;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    public List<Comment> getComments(String postId) {
        log.info("Fetching all comments for post with ID: {}", postId);
        return commentRepository.findAllByPostId(postId);
    }

    public Comment create(String postId, Comment comment) {
        try {
            log.info("Creating a new comment for post with ID: {}", postId);
            LocalDateTime now = LocalDateTime.now();
            User user = userService.loggedUserInfo();

            comment.setPostId(postId);
            comment.setCreatedAt(now);
            comment.setUpdatedAt(now);
            comment.setAuthor(user);

            Comment commentSaved = commentRepository.save(comment);
            postService.addComment(postId, commentSaved);
            return commentRepository.save(comment);
        } catch (MongoException ex) {
            log.error("Failed to create the comment. Please check the data and try again.", ex);
            throw new ResourceCreationException("Failed to create the comment. Please check the data and try again.", ex);
        }
    }

    public Comment update(String postId, String commentId, Comment comment) {
        log.info("Updating comment with ID: {} for post with ID: {}", commentId, postId);
        Comment commentToUpdate = findCommentById(commentId);

        if (!commentToUpdate.getPostId().equals(postId)) {
            throw new ResourceNotFoundException("Comment not found with ID: " + commentId);
        }

        if(!comment.getText().isEmpty()) commentToUpdate.setText(comment.getText());
        commentToUpdate.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(commentToUpdate);
    }

    public void delete(String postId, String commentId) {
        log.info("Deleting comment with ID: {} for post with ID: {}", commentId, postId);
        Comment comment = findCommentById(commentId);

        if (!comment.getPostId().equals(postId)) {
            throw new ResourceNotFoundException("Comment not found with ID: " + commentId);
        }

        commentRepository.delete(comment);
    }

    private Comment findCommentById(String id) {
        log.info("Finding comment by ID: {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id));
    }
}