package org.post.springboot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.post.springboot.dto.ParcelDetailDto;
import org.post.springboot.dto.ParcelDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EpostService implements ApiService {

    private static final String URL = "https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1={sid1}";

    public List<ParcelDetailDto> getProcessList(String body) {
        if (body == null) {
            return null;
        }

        List<ParcelDetailDto> resultList = new ArrayList<>();

        try {
            Document doc = Jsoup.parse(body);
            Element processTable = doc.getElementById("processTable");
            Elements tbody = processTable.getElementsByTag("tbody");
            Elements trList = tbody.get(0).getElementsByTag("tr");

            for (Element tr : trList) {
                ParcelDetailDto resultMap = new ParcelDetailDto();
                Elements tdList = tr.getElementsByTag("td");

                if (!tdList.get(0).text().isEmpty()) {
                    resultMap.setDate(LocalDate.parse(tdList.get(0).text(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
                }
                if (!tdList.get(1).text().isEmpty()) {
                    resultMap.setTime(LocalTime.parse(tdList.get(1).text()));
                }

                if (tdList.get(2).getElementsByTag("span").isEmpty()) {
                    resultMap.setPosition(tdList.get(2).getElementsByTag("a").text());
                } else {
                    resultMap.setPosition(tdList.get(2).getElementsByTag("span").text());
                }
                if (tdList.get(3).text().contains(" ")) {
                    resultMap.setState(tdList.get(3).text().substring(0, tdList.get(3).text().indexOf(" ")));
                } else {
                    resultMap.setState(tdList.get(3).text());
                }
                resultList.add(resultMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    public ParcelDto execute(String number) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ParcelDto result = new ParcelDto();

        params.put("sid1", number);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class, params);

        result.setCompanyName("epost");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            result.setResult(getProcessList(responseEntity.getBody()));
        }
        return result;
    }
}
