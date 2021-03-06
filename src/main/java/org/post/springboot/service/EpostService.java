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

    public Element getElementOfList(Elements tdList, int index) {
        return index < tdList.size() ? tdList.get(index) : null;
    }

    public LocalDate getParseDate(Elements tdList) {
        Element element = getElementOfList(tdList, 0);
        if (element == null || element.text().isEmpty()) {
            return null;
        }
        return LocalDate.parse(element.text(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public LocalTime getParseTime(Elements tdList) {
        Element element = getElementOfList(tdList, 1);
        if (element == null || element.text().isEmpty()) {
            return null;
        }
        return LocalTime.parse(element.text());
    }

    public String getParsePosition(Elements tdList) {
        Element element = getElementOfList(tdList, 2);
        if (element == null) {
            return null;
        }
        if (element.getElementsByTag("span").isEmpty()) {
            return element.getElementsByTag("a").text();
        }
        return element.getElementsByTag("span").text();
    }

    public String getParseState(Elements tdList) {
        Element element = getElementOfList(tdList, 3);
        if (element == null) {
            return null;
        }
        if (element.text().contains(" ")) {
            return element.text().substring(0, element.text().indexOf(" "));
        }
        return element.text();
    }

    public List<ParcelDetailDto> getProcessList(String body) {
        if (body == null) {
            return null;
        }

        List<ParcelDetailDto> resultList = new ArrayList<>();

        try {
            Document doc = Jsoup.parse(body);
            Element processTable = doc.getElementById("processTable");
            if (processTable == null) {
                return resultList;
            }
            Elements tbody = processTable.getElementsByTag("tbody");
            Elements trList = tbody.get(0).getElementsByTag("tr");

            for (Element tr : trList) {
                Elements tdList = tr.getElementsByTag("td");
                ParcelDetailDto resultMap = new ParcelDetailDto();
                resultMap.setDate(getParseDate(tdList));
                resultMap.setTime(getParseTime(tdList));
                resultMap.setPosition(getParsePosition(tdList));
                resultMap.setState(getParseState(tdList));
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
