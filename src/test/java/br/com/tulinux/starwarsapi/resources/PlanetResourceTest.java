package br.com.tulinux.starwarsapi.resources;

import br.com.tulinux.starwarsapi.models.Planet;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Java6Assertions.assertThat;
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
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetResource.class)
public class PlanetResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService planetService;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Planet> planetJacksonTester;

    private Planet mockPlanet;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());

        mockPlanet = new Planet().builder().id("5af73129db516558cbaede77").name("Tatooine").terrain("desert").climate("arid").build();

        mapper = new ObjectMapper();
    }

    @Test
    public void deveInserirUmPlaneta() throws Exception {

        Mockito.when(
                planetService.savePlanet(Mockito.any(Planet.class))).thenReturn(mockPlanet);

        final String stringPlanet = mapper.writeValueAsString(mockPlanet);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/planets")
                .accept(MediaType.APPLICATION_JSON)
                .content(stringPlanet)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/planets/5af73129db516558cbaede77",
                response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void deveAlterarUmPlaneta() throws Exception {

        String mockId = "5af73129db516558cbaede77";

        Planet mockPlanet = new Planet().builder().name("Tatooine").terrain("desert").climate("arid").build();

        Planet mockPlanetWithId = new Planet().builder().id(mockId).name("Tatooine").terrain("desert").climate("arid").build();

        given(planetService.findPlanetById(mockId)).willReturn(mockPlanetWithId);

        final String stringPlanet = mapper.writeValueAsString(mockPlanet);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/planets/" + mockId)
                .accept(MediaType.APPLICATION_JSON)
                .content(stringPlanet)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andReturn();

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void deveRetornarUmPlanetaQuandoLocalizadoPorNome() throws Exception {

        // given
        given(planetService.findPlanetByName("Tatooine"))
                .willReturn(mockPlanet);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/planets").param("name", "Tatooine")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                planetJacksonTester.write(mockPlanet).getJson());
    }

    @Test
    public void deveRetornarUmPlanetaQuandoLocalizadoPeloId() throws Exception {

        given(planetService.findPlanetById(mockPlanet.getId())).willReturn(mockPlanet);

        mockMvc.perform(get("/planets/" + mockPlanet.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("climate", is(mockPlanet.getClimate())));
    }

    @Test
    public void deveGerarUmErroQuandoNaoEncontradoPorNome() throws Exception {

        given(planetService.findPlanetByName(mockPlanet.getName())).willReturn(null);

        mockMvc.perform(get("/planets" + mockPlanet.getId())).andExpect(status().isNotFound());
    }
}