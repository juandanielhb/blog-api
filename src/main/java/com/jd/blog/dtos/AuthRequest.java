package com.jd.blog.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequest implements Serializable {

  @Email
  private String email;
  private String password;
}
