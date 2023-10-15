package com.jd.blog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document("comments")
public class Comment {
  @Id
  private String id;

  private String text;

  @DocumentReference(lazy=true)
  private User author;

  private String postId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}

