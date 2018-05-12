package br.com.tulinux.starwarsapi.resources;

import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.repositories.PlanetRespository;
import br.com.tulinux.starwarsapi.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Planet Resource Teste
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetResource.class)
// @SpringBootTest
public class PlanetResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetResource planetResource;

    @MockBean
    private PlanetService planetService;

    @MockBean
    private PlanetRespository planetRespository;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Planet> planetJacksonTester;

    String examplePlanetJson = "{\\\"id\\\": \\\"5af38599499c4c307f07696f\\\",\\\"name\\\": \\\"Kamino\\\",\\\"climate\\\": \\\"temperate\\\",\\\"terrain\\\": \\\"ocean\\\",\\\"films\\\": [\\\"https:\\/\\/swapi.co\\/api\\/films\\/5\\/\\\"]}";

    @Before
    public void setup() {

        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void deveInserirUmPlaneta() throws Exception {

        Planet mockPlanet = new Planet().builder().id("1").name("Terra Plana").terrain("Arido").climate("Quente").build();

        // planetService.savePlanet to respond back with mockPlanet
        Mockito.when(
                planetService.savePlanet(Mockito.any(Planet.class))).thenReturn(mockPlanet);

        // Send planet as body to /planets
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/planets")
                .accept(MediaType.APPLICATION_JSON).content(examplePlanetJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/planets/" + mockPlanet.getId(),
                response.getHeader(HttpHeaders.LOCATION));


        // given(planetService.savePlanet(mockPlanet)).willReturn(mockPlanet);

        // final ObjectMapper mapper = new ObjectMapper();

        // final String json = mapper.writeValueAsString(mockPlanet);

    }

    @Test
    public void deveRetornarUmPlanetaQuandoLocalizadoPorNome() throws Exception {

      /*  Planet planet = new Planet();

        planet.setName("Terra Plana");
        planet.setTerrain("Arido");
        planet.setClimate("Quente");

        // given
        given(planetRespository.findByName("Terra Plana"))
                .willReturn(planet);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/planets/").param("name", "Terra Plana")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                planetJacksonTester.write(planet).getJson());*/
    }

    @Test
    public void deveRetornarUmPlanetaPeloNome() {

    }

    @Test
    public void deveRetornarUmPlanetaPeloId() throws Exception {

        Planet planet = new Planet();

        planet.setId("5af38599499c4c307f07696f");
        planet.setName("Kamino");
        planet.setTerrain("ocean");
        planet.setClimate("temperate");

        given(planetResource.findPlanetById(planet.getId())).willReturn(planet);

        mockMvc.perform(get("/planet/" + planet.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("temperate", is(planet.getClimate())));
    }

    @Test
    public void deveGerarUmErroQuandoNaoEncontrarPorNome() throws Exception {

        String name = "Terra";

        given(planetService.findPlanetByName(name)).willReturn(null);
    }
}