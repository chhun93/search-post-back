package org.post.springboot.web;

import org.post.springboot.service.ApiService;
import org.post.springboot.service.CJService;
import org.post.springboot.service.EpostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PostApiController {

    private Map<String, ApiService> serviceMap;

    public PostApiController() {
        serviceMap = new HashMap<>();
        serviceMap.put("epost", new EpostService());
        serviceMap.put("cj", new CJService());
    }

    @GetMapping(path = "/api/v1/post-man")
    public Map<String, List<Object>> getList(@RequestParam(name = "tracking-number") String trackingNumber) {
        Map<String, List<Object>> result = new HashMap<>();
        result.put("response", new ArrayList<>());
        result.get("response").add(serviceMap.get("epost").execute(trackingNumber));
        result.get("response").add(serviceMap.get("cj").execute(trackingNumber));
        return result;
    }

    @GetMapping(path = "/api/v1/post-man/{id}")
    public Map<String, Object> getState(@PathVariable int id, @RequestParam(name = "tracking-number") String trackingNumber) {
        return null;
    }
}
