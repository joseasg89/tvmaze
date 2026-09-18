package com.kairos.tvmaze.service;

import com.kairos.tvmaze.client.TvmazeClient;
import com.kairos.tvmaze.model.SearchShowDTO;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TvmazeService {
    private final TvmazeClient tvmazeClient;

    public TvmazeService(TvmazeClient tvmazeClient) {
        this.tvmazeClient = tvmazeClient;
    }

    public List<SearchShowDTO> searchShows(String query) {
        JsonNode responseNode = tvmazeClient.searchShows(query);
        List<SearchShowDTO> results = new ArrayList<>();

        if (responseNode != null && responseNode.isArray()) {
            for (JsonNode item : responseNode) {
                JsonNode showNode = item.get("show");
                SearchShowDTO dto = parseShowNode(showNode);
                results.add(dto);
            }
        }
        return results;
    }

    public SearchShowDTO parseShowNode(JsonNode showNode) {
        SearchShowDTO dto = new SearchShowDTO();
        dto.setId(showNode.hasNonNull("id") ? showNode.get("id").asLong() : null);
        dto.setName(showNode.hasNonNull("name") ? showNode.get("name").asText() : null);
        dto.setSummary(showNode.hasNonNull("summary") ? showNode.get("summary").asText() : null);

        List<String> genres = new ArrayList<>();
        if (showNode.has("genres")) {
            for (JsonNode genero : showNode.get("genres")) {
                genres.add(genero.asText());
            }
        }
        dto.setGenres(genres);

        String channel = null;
        if (showNode.hasNonNull("network")) {
            channel = showNode.get("network").get("name").asText();
        } else if (showNode.hasNonNull("webChannel")) {
            channel = showNode.get("webChannel").get("name").asText();
        }
        dto.setChannel(channel);

        return dto;
    }

    public Map<String, Object> getShowById(Long showId) {
        JsonNode showNode = tvmazeClient.get_ShowById(showId);
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return mapper.convertValue(showNode, Map.class);
    }
}
