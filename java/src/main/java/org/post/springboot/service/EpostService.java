package org.post.springboot.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class EpostService implements ApiService {

    private static final String URL = "https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1={sid1}";

    public List<Map<String, String>> getProcessList(String body) {
        if (body == null) {
            return null;
        }

        List<Map<String, String>> resultList = new ArrayList<>();

        int processTablePosition = body.indexOf("processTable");
        int tbodyStartPosition = body.indexOf("tbody", processTablePosition);
        int tbodyEndPosition = body.indexOf("/tbody", tbodyStartPosition);

        String[] stateList = body.substring(tbodyStartPosition, tbodyEndPosition)
                .replaceAll("</tr>", " ")
                .split("<tr>");

        stateList = Arrays.copyOfRange(stateList, 1, stateList.length);

        for (String word : stateList) {
            Map<String, String> resultMap = new HashMap<>();
            int start = word.indexOf("<td>");
            int end = word.indexOf("</td>", start);
            String ret = word.substring(start + 4, end);
            resultMap.put("date", ret);

            start = word.indexOf("<td>", end);
            end = word.indexOf("</td>", start);
            ret = word.substring(start + 4, end);
            resultMap.put("time", ret);

            end = word.indexOf("</td>", end + 1);
            start = word.indexOf("<td>", end);
            end = word.indexOf(" ", start);
            ret = word.substring(start + 4, end).trim();
            resultMap.put("state", ret);

            resultList.add(resultMap);
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

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            result.put("result", getProcessList(responseEntity.getBody()));
        } else {
            result.put("result", null);
        }
        return result;
    }
}
