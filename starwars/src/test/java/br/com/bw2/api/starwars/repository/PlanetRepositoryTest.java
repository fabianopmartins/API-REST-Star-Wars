package br.com.bw2.api.starwars.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.bw2.api.starwars.document.Planet;
import br.com.bw2.api.starwars.service.PlanetServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetRepositoryTest {

	private static final String ID = "123", ID2 = "456", ID3 = "789";
	private static final String NAME = "Hoth", NAME2 = "Coruscant", NAME3 = "Alderaan";
	private static final String CLIMATE = "Arid", CLIMATE2 = "Frozen, ", CLIMATE3 = "Murky";
	private static final String TERRAIN = "Jungle", TERRAIN2 = "Desert", TERRAIN3 = "Ice";
	private static final String FILMS = "2", FILMS2 = "4", FILMS3 = "0";

	@MockBean
	private PlanetRepository planetRepository;

	@Autowired
	private PlanetServiceImpl planetServiceImpl;

	private Planet planet;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		planetServiceImpl = new PlanetServiceImpl(planetRepository);

	}

	@Test
	public void whenFindAllPlanetsTest() {

		when(planetRepository.findAll()).thenReturn(Stream.of(new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS),
				new Planet(ID2, NAME2, CLIMATE2, TERRAIN2, FILMS2), new Planet(ID3, NAME3, CLIMATE3, TERRAIN3, FILMS3))
				.collect(Collectors.toList()));
		assertEquals(3, planetServiceImpl.findAll().size());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void whenFindAllPlanetsReturnNullTest() {

		List<Planet> planets = new ArrayList<>();
		when(planetRepository.findAll()).thenReturn(planets);
		List result = planetServiceImpl.findAll();
		verify(planetRepository).findAll();
		assertThat(result.size()).isEqualTo(0);
	}

	@Test
	public void whenFindPlanetByIdTest() {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.findById(ID)).thenReturn(Optional.of(planet));
		assertThat(planetServiceImpl.findByid(ID)).isEqualTo(Optional.of(planet));
	}

	@Test
	public void whenFindPlanetByInvalidIdTest() {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.findById("789542")).thenReturn(Optional.of(planet));
		assertThat(planetServiceImpl.findByid(ID)).isEmpty();
	}

	@Test
	public void whenFindPlanetForValidNameTest() {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.findByThePlanetsName(NAME)).thenReturn(planet);
		assertThat(planetServiceImpl.findByName(NAME)).isIn(planet);
	}

	@Test
	public void whenFindPlanetForInvalidNameTest() {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.findByThePlanetsName(NAME)).thenReturn(null);
		assertThat(planetServiceImpl.findByName(NAME)).isEqualTo(null);

	}

	@Test
	public void savePlanetTest() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.save(planet)).thenReturn(planet);
		assertThat(planetServiceImpl.save(planet)).isEqualTo(planet);
		verify(planetRepository, times(1)).save(planet);
	}

	@Test
	public void dontSavePlanetWhenNameIsEmptyTest()
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		planet = new Planet(ID, "", CLIMATE, TERRAIN, FILMS);
		when(planetRepository.save(planet)).thenReturn(planet);
		assertThat(planetServiceImpl.save(planet).getName()).isEqualTo("");
	}

	@Test
	public void dontSaveDuplicatesPlanetsTest()
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		when(planetRepository.save(planet)).thenReturn(planet);
		when(planetRepository.findByThePlanetsName(planet.getName())).thenReturn(planet);
		verify(planetRepository, times(1)).save(planet);
	}

	@Test
	public void deletePlanetTest() {

		planet = new Planet(ID, NAME, CLIMATE, TERRAIN, FILMS);
		planetRepository.deleteById(ID);
		verify(planetRepository, times(1)).deleteById(ID);
	}
}