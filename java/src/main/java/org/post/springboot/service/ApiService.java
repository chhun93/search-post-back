package org.post.springboot.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ApiService {

    Map<String, Object> execute(String number);
}
