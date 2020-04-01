package br.com.bw2.api.starwars.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.bw2.api.starwars.document.Planet;
import br.com.bw2.api.starwars.service.PlanetServiceImpl;

@RestController
@RequestMapping("api/starWars")
public class PlanetRestController {

	@Autowired
	private PlanetServiceImpl planetServiceImpl;
	
	@GetMapping(value = "/planet")
	@ResponseBody
	public ResponseEntity<List<Planet>> getAllPlanets() {

		return ResponseEntity.ok(planetServiceImpl.findAll());
	}

	@GetMapping(value = "/planet/{id}")
	@ResponseBody
	public ResponseEntity<Optional<Planet>> getPlanetById(@PathVariable("id") String id) {

		return ResponseEntity.ok(planetServiceImpl.findByid(id));
	}

	@GetMapping(value = "/planetName/{name}")
	@ResponseBody
	public ResponseEntity<Planet> getPlanetByName(@PathVariable("name") String name) {

		return ResponseEntity.ok(planetServiceImpl.findByName(name));
	}

	@PostMapping(value = "/planet")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Planet> savePlanet(@Valid @RequestBody Planet planet)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		return ResponseEntity.ok(planetServiceImpl.save(planet));
	}

	@DeleteMapping(value = "/planet/{id}")
	public void deletePlanet(@PathVariable("id") String id) {

		planetServiceImpl.remove(id);
	}
}