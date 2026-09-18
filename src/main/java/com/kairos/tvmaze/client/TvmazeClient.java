package com.kairos.tvmaze.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

@Component
public class TvmazeClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode searchShows(String query) {
        String url = "http://api.tvmaze.com/search/shows?q=" + query;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode get_ShowById(Long showId) {
        String url = "https://api.tvmaze.com/shows/" + showId;
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
