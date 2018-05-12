package br.com.tulinux.starwarsapi.config;

import br.com.tulinux.starwarsapi.models.Planet;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * PlanetCommandLineRunner Component
 * <p>
 * Classe responsável por inserir dados iniciais no MongoDB da API Star Wars
 */
@Component
public class PlanetCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetCommandLineRunner.class);

     private final MongoTemplate mongoTemplate;

    @Autowired
    public PlanetCommandLineRunner(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... strings) throws Exception {

        Planet planet;

        LOGGER.info("Inserindo 10 planetas no MongoDB");

        for (int i = 1; i <= 10; i++) {

            String planetString = Unirest.get("https://swapi.co/api/planets/{id}")
                    .routeParam("id", String.valueOf(i))
                    .asJson()
                    .getBody()
                    .toString();

            planet = new Gson().fromJson(planetString, Planet.class);

            mongoTemplate.save(planet, "planet");
        }

    }
}


