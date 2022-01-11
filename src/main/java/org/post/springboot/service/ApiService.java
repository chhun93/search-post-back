package org.post.springboot.service;

import org.post.springboot.dto.ParcelDto;

public interface ApiService {
    ParcelDto execute(String number);
}
