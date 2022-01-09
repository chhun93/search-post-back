package org.post.springboot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CJService implements ApiService {

    private static final String URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking";

    public String getCSRF(String body) {
        Document doc = Jsoup.parse(body);
        List<Element> list = doc.getElementsByAttributeValue("name", "_csrf");
        String csrf = "";

        if (!list.isEmpty()) {
            csrf = list.get(0).attr("value");
        }
        return csrf;
    }

    @Override
    public Map<String, Object> execute(String number) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> result = new HashMap<>();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String csrf = getCSRF(responseEntity.getBody());
        } else {
            result.put("result", null);
        }
        return result;
    }
}
