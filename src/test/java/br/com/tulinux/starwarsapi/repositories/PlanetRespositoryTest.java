package br.com.tulinux.starwarsapi.repositories;

import br.com.tulinux.starwarsapi.models.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PlanetRespositoryTest {

    @Autowired
    private PlanetRespository planetRespository;

    private Planet mockPlanet = new Planet().builder().id("5af73129db516558cbaede77").name("Tatooine").terrain("desert").climate("arid").build();

    @Test
    public void deveBuscarPorNome() {

        assertEquals(mockPlanet.getName(), planetRespository.findByName(mockPlanet.getName()).getName());
    }
}