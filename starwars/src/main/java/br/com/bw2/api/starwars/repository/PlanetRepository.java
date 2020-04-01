package br.com.bw2.api.starwars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.bw2.api.starwars.document.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	@Query(" { name: { $regex : '(?i)?0'}  } }")
	Planet findByThePlanetsName(String name);
}