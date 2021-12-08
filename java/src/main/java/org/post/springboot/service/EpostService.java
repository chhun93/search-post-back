package org.post.springboot.service;

import java.util.Map;

public class EpostService implements ApiService {

    private final String URL = "https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?";

    @Override
    public Map<String, Object> execute(String number) {
        return null;
    }
}
