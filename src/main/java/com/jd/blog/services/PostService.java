package com.jd.blog.services;

import com.jd.blog.exceptions.ResourceCreationException;
import com.jd.blog.exceptions.ResourceNotFoundException;
import com.jd.blog.model.Comment;
import com.jd.blog.model.Post;
import com.jd.blog.model.User;
import com.jd.blog.repository.PostRepository;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    public List<Post> getPosts() {
        log.info("Fetching all posts.");
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        return postRepository.findAll(sort);
    }

    public Page<Post> getPostsPage(Pageable pageable) {
        log.info("Fetching all posts.");
        return postRepository.findAll(pageable);
    }




    public Post create(Post post) {
        try {
            log.info("Creating a new post: {}", post.getTitle());
            User user = userService.loggedUserInfo();
            LocalDateTime now = LocalDateTime.now();

            post.setCreatedAt(now);
            post.setUpdatedAt(now);
            post.setAuthor(user);

            return postRepository.save(post);
        } catch (MongoException ex) {
            log.error("Failed to create the post in MongoDB. Please check the data and try again.", ex);
            throw new ResourceCreationException("Failed to create the post in MongoDB. Please check the data and try again.", ex);
        }
    }

    public Post update(String id, Post post) {
        log.info("Updating post with ID: {}", id);
        Post postToUpdate = findPostById(id);

        if(!post.getTitle().isEmpty()) postToUpdate.setTitle(post.getTitle());
        if(!post.getContent().isEmpty()) postToUpdate.setContent(post.getContent());
        postToUpdate.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(postToUpdate);
    }

    public void delete(String id) {
        log.info("Deleting post with ID: {}", id);
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    Post findPostById(String id) {
        log.info("Finding post by ID: {}", id);
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
    }

    Post addComment(String postId, Comment comment) {
        log.info("Add comment to the comments of the post...");
        Post post = findPostById(postId);
        post.getComments().add(comment);
        return postRepository.save(post);
    }
}
