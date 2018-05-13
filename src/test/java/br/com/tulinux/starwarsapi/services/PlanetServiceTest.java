package br.com.tulinux.starwarsapi.services;

import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.repositories.PlanetRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ActiveProfiles("test")
public class PlanetServiceTest {

    private Planet mockPlanet;

    private ObjectMapper mapper;

    List<Planet> mockPlanetList = new ArrayList<>();

    @Before
    public void setup() {

        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());

        mockPlanet = new Planet().builder().id("5af73129db516558cbaede77").name("Tatooine").terrain("desert").climate("arid").build();

        mockPlanetList = Arrays.asList(mockPlanet, mockPlanet);

        mapper = new ObjectMapper();
    }

    @Test
    public void deveBuscarListaDePlanetas() {

        PlanetRespository mockPlanetRespository = mock(PlanetRespository.class);

        when(mockPlanetRespository.findAll()).thenReturn(mockPlanetList);

        PlanetService planetService = new PlanetService(mockPlanetRespository);

        List<Planet> planetList = planetService.getAll();

        assertEquals(planetList.size(), mockPlanetList.size());
    }

    @Test
    public void deveInserirUmPlaneta() {

        PlanetRespository mockPlanetRespository = mock(PlanetRespository.class);

        when(mockPlanetRespository.save(mockPlanet)).thenReturn(mockPlanet);

        PlanetService planetService = new PlanetService(mockPlanetRespository);

        planetService.savePlanet(mockPlanet);

        verify(mockPlanetRespository, times(1)).save(mockPlanet);
    }

    @Test
    public void deveAlterarUmPlaneta() {

        PlanetRespository mockPlanetRespository = mock(PlanetRespository.class);

        when(mockPlanetRespository.save(mockPlanet)).thenReturn(mockPlanet);

        PlanetService planetService = new PlanetService(mockPlanetRespository);

        planetService.updatePlanet(mockPlanet);

        verify(mockPlanetRespository, times(1)).save(mockPlanet);
    }

    @Test
    public void deveBuscarUmPlanetaPeloId() {

        PlanetRespository mockPlanetRespository = mock(PlanetRespository.class);

        when(mockPlanetRespository.findOne(mockPlanet.getId())).thenReturn(mockPlanet);

        PlanetService planetService = new PlanetService(mockPlanetRespository);

        Planet planet = planetService.findPlanetById(mockPlanet.getId());

        assertEquals(planet.getName(), mockPlanet.getName());
    }

    @Test
    public void deveBuscarUmPlanetaPeloNome() {

        PlanetRespository mockPlanetRespository = mock(PlanetRespository.class);

        when(mockPlanetRespository.findByName(mockPlanet.getName())).thenReturn(mockPlanet);

        PlanetService planetService = new PlanetService(mockPlanetRespository);

        Planet planet = planetService.findPlanetByName(mockPlanet.getName());

        assertEquals(planet.getTerrain(), mockPlanet.getTerrain());
    }
}