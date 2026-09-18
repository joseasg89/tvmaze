package com.kairos.tvmaze.controller;

import com.kairos.tvmaze.model.CommentRating;
import com.kairos.tvmaze.model.CommentRatingDTO;
import com.kairos.tvmaze.model.SearchShowDTO;
import com.kairos.tvmaze.repository.CommentRatingRepository;
import com.kairos.tvmaze.service.TvmazeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tvmaze")
public class TvmazeController {
    private final TvmazeService tvmazeService;
    private final CommentRatingRepository commentRatingRepository;

    public TvmazeController(TvmazeService tvmazeService, CommentRatingRepository commentRatingRepository) {
        this.tvmazeService = tvmazeService;
        this.commentRatingRepository = commentRatingRepository;
    }

    @GetMapping("/search")
    public List<SearchShowDTO> search(@RequestParam("search_query") String query) {
        return tvmazeService.searchShows(query);
    }

    @GetMapping("/shows/{show_id}")
    public Object getShow(@PathVariable("show_id") Long showId) {
        return tvmazeService.getShowById(showId);
    }

    @PostMapping("/comments-ratings")
    public ResponseEntity<Map<String, String>> addComment(@RequestBody CommentRatingDTO request) {
        if (request.getRating() < 0 || request.getRating() > 5) {
            return ResponseEntity.badRequest().body(Map.of("status", "Rating debe estar entre 0 y 5"));
        }

        CommentRating comment = new CommentRating(
                request.getShow_id(),
                request.getComment(),
                request.getRating()
        );
        commentRatingRepository.save(comment);

        return ResponseEntity.ok(Map.of("status", "SUCCESS"));
    }
}
