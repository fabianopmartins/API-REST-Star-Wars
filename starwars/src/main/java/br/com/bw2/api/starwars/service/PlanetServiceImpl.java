package br.com.bw2.api.starwars.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.bw2.api.starwars.document.Planet;
import br.com.bw2.api.starwars.repository.PlanetRepository;

@Service
public class PlanetServiceImpl implements PlanetService {
	
	@Autowired
	private PlanetRepository planetRepository;

	@Autowired
	private PlanetService planetService;
	
	public PlanetServiceImpl(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
	}

	@Override
	public List<Planet> findAll() {

		return planetRepository.findAll();
	}

	@Override
	public Optional<Planet> findByid(String id) {

		return planetRepository.findById(id);
	}

	@Override
	public Planet save(Planet planet)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		Planet planetFilms = new Planet();
		
		planetFilms = planet;

		String nameVerify = planetFilms.getName();
		
		if (findByName(nameVerify) != null) {

			return null;
		} else {

			planetFilms.setFilms(planetService.consumerAPI(planetFilms.getName()));
			return planetRepository.save(planet);
		}
	}

	@Override
	public Planet findByName(String name) {
		
		return planetRepository.findByThePlanetsName(name);
	}

	@Override
	public void remove(String id) {
		try {
			
			planetRepository.deleteById(id);
		} catch(NullPointerException e) {
			new NullPointerException("Planeta não existe");
		}
	}

	@Override
	public String consumerAPI(String name)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		@SuppressWarnings("unused")
		String films = null, json = null, jsonString, nameJson = null, url = "https://swapi.co/api/planets/";

		JSONArray filmsJson = null, results;

		JSONObject planets, res;

		do {

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			jsonString = response.getBody();

			planets = new JSONObject(jsonString);

			results = planets.getJSONArray("results");

			for (int i = 0; i < results.length(); i++) {

				res = results.getJSONObject(i);

				nameJson = (String) res.get("name");

				if (nameJson.equals(name)) {

					filmsJson = res.getJSONArray("films");

					films = Integer.toString(filmsJson.length());

					return films;
				}
			}

			if (!JSONObject.NULL.equals(planets.get("next"))) {
				url = (String) planets.get("next");
			} else {
				url = null;
				films = "0";
			}

		} while (url != null);

		return films;
	}
}