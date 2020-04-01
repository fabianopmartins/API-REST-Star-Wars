package br.com.bw2.api.starwars.document;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

@Document
public class Planet {

	@Id
	private String id;

	@NotBlank(message = "O campo nome é obrigatório")
	@NonNull
	@Field(name = "name")
	private String name;

	@NotBlank(message = "O campo clima é obrigatório")
	@Field(name = "climate")
	private String climate;

	@NotBlank(message = "O campo terreno é obrigatório")
	@Field(name = "terrain")
	private String terrain;

	@Field(name = "films")
	private String films;

	public Planet() {

	}

	public Planet(String id, String name, String climate, String terrain, String films) {

		this.id = id;
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

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + ", films="
				+ films + "]";
	}
}