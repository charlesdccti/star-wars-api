package br.com.tulinux.starwarsapi.services;

import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.repositories.PlanetRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Planet Service
 */
@Service
public class PlanetService {

    private PlanetRespository planetRespository;

    @Autowired
    public PlanetService(PlanetRespository planetRespository) {

        this.planetRespository = planetRespository;
    }

    /**
     * Get All Planets
     *
     * @return Retorna a Lista de Planetas
     */
    public List<Planet> getAll() {

        return planetRespository.findAll();
    }

    /**
     * Salva o novo Planeta
     *
     * @param planet
     * @return Retorna o Planeta Salvo
     */
    public Planet savePlanet(Planet planet) {

        return planetRespository.save(planet);
    }

    /**
     * Altera um Planeta
     *
     * @param planet Alterações a efetuar
     * @return Retorna o Planeta alterado
     */
    public Planet updatePlanet(Planet planet) {

        return planetRespository.save(planet);
    }

    /**
     * Busca um planeta pelo Id
     *
     * @param id Id do planeta a ser localizado
     * @return Retorna um Planeta
     */
    public Planet findPlanetById(String id) {

        return planetRespository.findOne(id);
    }


    /**
     * Busca um Planeta pelo nome
     *
     * @param name Nome do Planeta
     * @return Retorna um Planeta
     */
    public Planet findPlanetByName(String name) {

        return planetRespository.findByName(name);
    }

    /**
     * Apaga um Planeta
     *
     * @param id Id do Planeta a ser excluido
     */
    public void deletePlanetById(String id) {

        planetRespository.delete(id);
    }


    /**
     * Quantidade de Planetas
     *
     * @return Retorna um Long
     */
    public Long getCount() {

        return planetRespository.count();
    }


}
