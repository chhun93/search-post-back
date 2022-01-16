package org.post.springboot.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.post.springboot.dto.CJ.ResponseDto;
import org.post.springboot.dto.CJ.Result;
import org.post.springboot.dto.ParcelDetailDto;
import org.post.springboot.dto.ParcelDto;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CJService implements ApiService {

    private static final String URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking";
    private static final String DETAIL_URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking-detail";

    public String getCSRF(String body) {
        Document doc = Jsoup.parse(body);
        List<Element> list = doc.getElementsByAttributeValue("name", "_csrf");
        String csrf = "";

        if (!list.isEmpty()) {
            csrf = list.get(0).attr("value");
        }
        return csrf;
    }

    public String getSession(List<String> cookies) {
        String result = "";
        for (String cookie : cookies) {
            String[] splitCookie = cookie.split(";");
            for (String var : splitCookie) {
                String key = var.split("=")[0];
                if ("JSESSIONID".equals(key)) {
                    result = var.split("=")[1];
                }
            }
        }
        return result;
    }

    public String doPost(String csrf, String number, String session) {
        if (csrf.isEmpty() || number.isEmpty() || session.isEmpty()) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("_csrf", csrf);
        requestBody.add("paramInvcNo", number);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + session);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(DETAIL_URL, httpEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }

    public List<ParcelDetailDto> getProcessList(String body) {
        List<ParcelDetailDto> resultList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        try {
            ResponseDto responseDto = mapper.readValue(body, ResponseDto.class);
            for (Result processDto : responseDto.getParcelDetailResultMap().getResultList()) {
                ParcelDetailDto parcelDetailDto = new ParcelDetailDto();
                parcelDetailDto.setDate(processDto.getdTime().toLocalDate());
                parcelDetailDto.setTime(processDto.getdTime().toLocalTime());
                parcelDetailDto.setPosition(processDto.getRegBranNm());
                parcelDetailDto.setState(processDto.getScanNm());
                resultList.add(parcelDetailDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    public ParcelDto execute(String number) {
        RestTemplate restTemplate = new RestTemplate();
        ParcelDto result = new ParcelDto();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        result.setCompanyName("cj");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String csrf = getCSRF(responseEntity.getBody());
            String session = getSession(responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE));
            String body = doPost(csrf, number, session);
            result.setResult(getProcessList(body));
        }
        return result;
    }
}
