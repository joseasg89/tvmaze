package com.kairos.tvmaze.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchShowDTO {
    private Long id;
    private String name;
    private String channel;
    private String summary;
    private List<String> genres;
    private List<CommentRatingDTO> comments;
}
