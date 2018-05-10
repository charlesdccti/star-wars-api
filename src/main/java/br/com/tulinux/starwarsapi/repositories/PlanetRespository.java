package br.com.tulinux.starwarsapi.repositories;

import br.com.tulinux.starwarsapi.models.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

/**
 * Respositorio dos Planetas
 */
@RestController
public interface PlanetRespository extends MongoRepository<Planet, String> {

    Planet findByName(String name);
}
