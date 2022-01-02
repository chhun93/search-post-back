package org.post.springboot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EpostService implements ApiService {

    private static final String URL = "https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1={sid1}";

    public List<Map<String, String>> getProcessList(String body) {
        if (body == null) {
            return null;
        }

        List<Map<String, String>> resultList = new ArrayList<>();

        try {
            Document doc = Jsoup.parse(body);
            Element processTable = doc.getElementById("processTable");
            Elements tbody = processTable.getElementsByTag("tbody");
            Elements trList = tbody.get(0).getElementsByTag("tr");

            for (Element tr : trList) {
                Map<String, String> resultMap = new HashMap<>();
                Elements tdList = tr.getElementsByTag("td");
                resultMap.put("date", tdList.get(0).text());
                resultMap.put("time", tdList.get(1).text());

                if (tdList.get(2).getElementsByTag("span").isEmpty()) {
                    resultMap.put("position", tdList.get(2).getElementsByTag("a").text());
                } else {
                    resultMap.put("position", tdList.get(2).getElementsByTag("span").text());
                }
                if (tdList.get(3).text().contains(" ")) {
                    resultMap.put("state", tdList.get(3).text().substring(0, tdList.get(3).text().indexOf(" ")));
                } else {
                    resultMap.put("state", tdList.get(3).text());
                }
                resultList.add(resultMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Map<String, Object> execute(String number) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        params.put("sid1", number);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class, params);

        result.put("companyName", "epost");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            result.put("result", getProcessList(responseEntity.getBody()));
        } else {
            result.put("result", null);
        }
        return result;
    }
}
