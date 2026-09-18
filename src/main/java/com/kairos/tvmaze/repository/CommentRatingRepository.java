package com.kairos.tvmaze.repository;

import com.kairos.tvmaze.model.CommentRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CommentRatingRepository extends MongoRepository<CommentRating, String> {
    List<CommentRating> findByShowId(Long showId);
}
