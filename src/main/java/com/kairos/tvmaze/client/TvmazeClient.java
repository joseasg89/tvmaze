package com.kairos.tvmaze.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
@Component
public class TvmazeClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode searchShows(String query) {
        String url = "http://api.tvmaze.com/search/shows?q=" + query;
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
