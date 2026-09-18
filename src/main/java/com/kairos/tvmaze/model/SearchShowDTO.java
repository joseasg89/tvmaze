package com.kairos.tvmaze.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchShowDTO {
    private long id;
    private String name;
    private String channel;
    private String summary;
    private List<String> genres;
}
