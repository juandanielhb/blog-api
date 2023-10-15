package com.jd.blog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("posts")
public class Post {
  @Id
  private String id;

  private String title;

  private String content;

  @DocumentReference(lazy=true)
  private User author;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @DocumentReference()
  private List<Comment> comments;
}

