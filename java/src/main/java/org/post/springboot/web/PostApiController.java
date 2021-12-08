package org.post.springboot.web;

import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.post.springboot.service.ApiService;
import org.post.springboot.service.EpostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PostApiController {

    private Map<String, ApiService> serviceMap;

    public PostApiController() {
        serviceMap = new HashMap<>();
        serviceMap.put("epost", new EpostService());
    }

    @GetMapping(path = "/api/v1/post-man")
    public Map<String, Object> getList(@RequestParam(name = "tracking-number") String trackingNumber) {
        return serviceMap.get("epost").execute(trackingNumber);
    }

    @GetMapping(path = "/api/v1/post-man/{id}")
    public Map<String, Object> getState(@PathVariable int id, @RequestParam(name = "tracking-number") String trackingNumber) {
        return null;
    }
}
