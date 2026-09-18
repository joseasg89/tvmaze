package com.kairos.tvmaze.repository;

import com.kairos.tvmaze.model.CacheShow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheShowRepository extends MongoRepository<CacheShow, Long> {
}
