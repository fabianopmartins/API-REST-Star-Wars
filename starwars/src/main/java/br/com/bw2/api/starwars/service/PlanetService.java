package br.com.bw2.api.starwars.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.bw2.api.starwars.document.Planet;

public interface PlanetService {

	List<Planet> findAll();

	Optional<Planet> findByid(String id);

	Planet save(Planet planet) throws JsonParseException, JsonMappingException, MalformedURLException, IOException;

	Planet findByName(String name);

	void remove(String id);

	String consumerAPI(String name) throws JsonParseException, JsonMappingException, MalformedURLException, IOException;
}