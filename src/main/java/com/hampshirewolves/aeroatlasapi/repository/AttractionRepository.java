package com.hampshirewolves.aeroatlasapi.repository;

import com.hampshirewolves.aeroatlasapi.model.Attraction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends CrudRepository<Attraction, Long> {}
