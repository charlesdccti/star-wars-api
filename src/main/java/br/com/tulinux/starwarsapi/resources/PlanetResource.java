package br.com.tulinux.starwarsapi.resources;

import br.com.tulinux.starwarsapi.errors.ResourceNotFoundException;
import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.services.PlanetService;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Planet Resource
 */

@RestController
@RequestMapping("/planets")
public class PlanetResource {

    private static final String PLANETA_NAO_LOCALIZADO = "Planeta não localizado!";

    private PlanetService planetService;

    @Autowired
    public PlanetResource(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * Get All Planets
     *
     * @return Retorna a lista de Planetas e o Status 200
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna a lista planetas")
    public List<Planet> getAllPlanets() {

        return planetService.getAll();
    }

    /**
     * New Planet
     *
     * @param newPlanet Novo platena no corpo da requisição
     * @return Retorna o Planeta criado com sua URI e o Status 201
     */
    @PostMapping
    public ResponseEntity newPlanet(@RequestBody Planet newPlanet) {

        Planet planet = planetService.savePlanet(newPlanet);

        if (planet == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(planet.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * @param id            Id do Planeta a ser aletrado
     * @param updatedPlanet Planeta com novas informações
     * @return Retorna o Planeta com a alteração
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Altera um planeta pelo um ID", response = Planet.class)
    public Planet updatePlanet(@PathVariable String id, @RequestBody Planet updatedPlanet) {

        Preconditions.checkNotNull(updatedPlanet);

        Planet planet = planetService.findPlanetById(id);

        if (planet == null) {
            throw new ResourceNotFoundException(PLANETA_NAO_LOCALIZADO);
        }

        updatedPlanet.setId(planet.getId());

        return planetService.updatePlanet(updatedPlanet);
    }

    /**
     * Busca Planeta por Id
     *
     * @param id Id do planeta a ser localizado
     * @return Retorna o Planeta
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Busca uma planeta por um ID", response = Planet.class)
    @ResponseStatus(HttpStatus.OK)
    public Planet findPlanetById(@PathVariable String id) {

        Planet planetById = planetService.findPlanetById(id);

        if (planetById == null) {
            throw new ResourceNotFoundException(PLANETA_NAO_LOCALIZADO);
        }

        return planetById;
    }

    /**
     * Busca Planeta por Nome
     *
     * @param name Nome do Planeta a ser localizado
     * @return Retorna um Planeta
     */
    @GetMapping(params = "name")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Busca uma planeta por Nome", response = Planet.class)
    public Planet findPlanetByName(@Param("name") String name) {

        Planet planetByName = planetService.findPlanetByName(name);

        if (planetByName == null) {
            throw new ResourceNotFoundException(PLANETA_NAO_LOCALIZADO);
        }

        return planetByName;
    }

    /**
     * Apaga um Planeta
     *
     * @param id Id do Planeta a ser excluido
     * @return Retorna o Status 200
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Apaga um planeta")
    public ResponseEntity deletePlanet(@PathVariable String id) {

        Planet planetById = planetService.findPlanetById(id);

        if (planetById == null) {

            throw new ResourceNotFoundException(PLANETA_NAO_LOCALIZADO);
        } else {

            planetService.deletePlanetById(id);

            return new ResponseEntity<>("Planeta excluido com sucesso!", HttpStatus.OK);
        }
    }

    /**
     * Quantidade de Planetas
     *
     * @return Retorna um Long e o Status 200
     */
    @GetMapping("/count")
    @ApiOperation(value = "Quantidade de planetas")
    public ResponseEntity<Long> count() {

        return new ResponseEntity<>(planetService.getCount(), HttpStatus.OK);
    }
}
