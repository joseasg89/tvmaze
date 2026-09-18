package com.kairos.tvmaze.controller;

import com.kairos.tvmaze.model.SearchShowDTO;
import com.kairos.tvmaze.service.TvmazeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tvmaze")
public class TvmazeController {
    private final TvmazeService tvmazeService;

    public TvmazeController(TvmazeService tvmazeService) {
        this.tvmazeService = tvmazeService;
    }

    @GetMapping("/search")
    public List<SearchShowDTO> search(@RequestParam("search_query") String query) {
        return tvmazeService.searchShows(query);
    }

    @GetMapping("/shows/{show_id}")
    public Object getShow(@PathVariable("show_id") Long showId) {
        return tvmazeService.getShowById(showId);
    }
}
