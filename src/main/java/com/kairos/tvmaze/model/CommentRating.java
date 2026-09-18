package com.kairos.tvmaze.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CommentRating {
    @Id
    private String id;
    private Long showId;
    private String comment;
    private Integer rating;

    public CommentRating() {}

    public CommentRating(Long showId, String comment, Integer rating) {
        this.showId = showId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getId() { return id; }
    public Long getShowId() { return showId; }
    public String getComment() { return comment; }
    public Integer getRating() { return rating; }
}
