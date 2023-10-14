package com.jd.blog.dtos;

import java.io.Serializable;

public record TokenInfo(String jwt) implements Serializable {}
