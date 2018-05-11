package br.com.tulinux.starwarsapi.resources;

import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Planet Resource Teste
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetResource.class)
public class PlanetResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService planetService;

    @Before
    public void setup() {

    }

    @Test
    public void deveInserirUmPlaneta() throws Exception {

        Planet planet = new Planet();

        planet.setName("Terra Plana");
        planet.setTerrain("Arido");
        planet.setClimate("Quente");

        given(planetService.savePlanet(planet)).willReturn(planet);

        final ObjectMapper mapper = new ObjectMapper();

        final String json = mapper.writeValueAsString(planet);

        mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.climate", equalTo("Quente")));
    }

    @Test
    public void deveRetornarUmPlanetaPeloNome() {

    }


    @Test
    public void deveGerarUmErroQuandoNaoEncontrarPorNome() throws Exception {

        String name = "Terra";

        given(planetService.findPlanetByName(name)).willReturn(null);

        mockMvc.perform(
                get("/planets/").param("name", name))
                .andExpect(status().isNotFound());
    }
}