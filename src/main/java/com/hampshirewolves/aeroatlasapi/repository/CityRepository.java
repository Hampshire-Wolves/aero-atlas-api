package com.hampshirewolves.aeroatlasapi.repository;

import com.hampshirewolves.aeroatlasapi.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {}
