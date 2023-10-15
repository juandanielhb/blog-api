package com.jd.blog.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("users")
public class User   {
  @Id
  private String id;

  private String name;

  private String surname;

  private String username;

  @Email
  private String email;

  @Size(min = 8, max = 30, message = "The password must be between 8 and 30 characters.")
  private String password;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}

