package com.kairos.tvmaze.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "cache_shows")
public class CacheShow {
    @Id
    private Long id;
    private Map<String, Object> data;

    public CacheShow() {}

    public CacheShow(Long id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}
