package br.com.bw2.api.starwars.dto;

import br.com.bw2.api.starwars.document.Planet;

public class PlanetDto {

	private String id;
	private String name;
	private String climate;
	private String terrain;
	private String films;

	public PlanetDto() {

	}

	public PlanetDto(String id, String name, String climate, String terrain, String films) {

		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public String getFilms() {
		return films;
	}

	public void setFilms(String films) {
		this.films = films;
	}

	public PlanetDto toDto(Planet planet) {

		return new PlanetDto(planet.getId(), planet.getName(), planet.getClimate(), planet.getTerrain(),
				planet.getFilms());
	}
}