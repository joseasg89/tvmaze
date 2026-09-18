package com.kairos.tvmaze.service;

import com.kairos.tvmaze.client.TvmazeClient;
import com.kairos.tvmaze.model.CacheShow;
import com.kairos.tvmaze.model.CommentRating;
import com.kairos.tvmaze.model.CommentRatingDTO;
import com.kairos.tvmaze.model.SearchShowDTO;
import com.kairos.tvmaze.repository.CacheShowRepository;
import com.kairos.tvmaze.repository.CommentRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TvmazeService {
    private final TvmazeClient tvmazeClient;
    @Autowired
    private CommentRatingRepository commentRatingRepository;

    public TvmazeService(TvmazeClient tvmazeClient) {
        this.tvmazeClient = tvmazeClient;
    }

    @Autowired
    private CacheShowRepository cachedShowRepository;

    public List<SearchShowDTO> searchShows(String query) {
        JsonNode responseNode = tvmazeClient.searchShows(query);
        List<SearchShowDTO> results = new ArrayList<>();

        if (responseNode != null && responseNode.isArray()) {
            for (JsonNode item : responseNode) {
                JsonNode showNode = item.get("show");
                SearchShowDTO dto = parseShowNode(showNode);

                if (dto.getId() != null) {
                    List<CommentRating> comments = commentRatingRepository.findByShowId(dto.getId());
                    List<CommentRatingDTO> commentDTOs = comments.stream().map(c -> {
                        CommentRatingDTO crdto = new CommentRatingDTO();
                        crdto.setShow_id(dto.getId());
                        crdto.setComment(c.getComment());
                        crdto.setRating(c.getRating());
                        return crdto;
                    }).toList();

                    dto.setComments(commentDTOs);
                }

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
        Map<String, Object> showData;

        Optional<CacheShow> cached = cachedShowRepository.findById(showId);
        if (cached.isPresent()) {
            showData = cached.get().getData();
        }
        else {
            JsonNode showNode = tvmazeClient.get_ShowById(showId);
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            showData = mapper.convertValue(showNode, Map.class);
            cachedShowRepository.save(new CacheShow(showId, showData));
        }

        Map<String, Object> mutableShowData = new HashMap<>(showData);

        List<CommentRating> commentsRatings = commentRatingRepository.findByShowId(showId);
        List<Map<String, Object>> commentList = commentsRatings.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("comment", c.getComment());
            map.put("rating", c.getRating());
            return map;
        }).toList();

        mutableShowData.put("comments", commentList);

        return mutableShowData;
    }
}
