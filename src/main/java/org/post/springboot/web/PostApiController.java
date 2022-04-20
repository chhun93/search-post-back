package org.post.springboot.web;

import org.post.springboot.service.ApiService;
import org.post.springboot.service.CJService;
import org.post.springboot.service.EpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PostApiController {

    private final Map<String, ApiService> serviceMap = new HashMap<>();

    @Autowired
    public PostApiController(EpostService epostService, CJService cjService) {
        serviceMap.put("epost", epostService);
        serviceMap.put("cj", cjService);
    }

    @GetMapping(path = "/api/v1/post-man")
    public Map<String, List<Object>> getList(@RequestParam(name = "tracking-number") String trackingNumber) {
        Map<String, List<Object>> result = new HashMap<>();
        result.put("response", new ArrayList<>());
        for (ApiService apiService : serviceMap.values()) {
            result.get("response").add(apiService.execute(trackingNumber));
        }
        return result;
    }
}
