package com.kairos.tvmaze.model;

import lombok.Data;

@Data
public class CommentRatingDTO {
    private Long show_id;
    private String comment;
    private Integer rating;
}
