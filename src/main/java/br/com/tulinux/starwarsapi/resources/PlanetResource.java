package br.com.tulinux.starwarsapi.resources;

import br.com.tulinux.starwarsapi.erros.ResourceNotFoundException;
import br.com.tulinux.starwarsapi.models.Planet;
import br.com.tulinux.starwarsapi.services.PlanetService;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Planet Resource
 */

@Controller
@RequestMapping("/planets")
public class PlanetResource {

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
    @ApiOperation(value = "Visualiza a lista de planetas")
    public ResponseEntity getAllPlanets() {

        return new ResponseEntity<>(planetService.getAll(), HttpStatus.OK);
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
    @ApiOperation(value = "Altera um planeta pelo um ID", response = Planet.class)
    public ResponseEntity updatePlanet(@PathVariable String id, @RequestBody Planet updatedPlanet) {

        Preconditions.checkNotNull(updatedPlanet);

        Planet planet = planetService.findPlanetById(id);

        if (planet == null) {
            throw new ResourceNotFoundException("Planeta não localizado!");
        }

        // updatedPlanet.setId(planet.getId());

        return new ResponseEntity<>(planetService.updatePlanet(updatedPlanet), HttpStatus.OK);
    }

    /**
     * Busca Planeta por Id
     *
     * @param id Id do planeta a ser localizado
     * @return Retorna o Planeta
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Busca uma planeta por um ID", response = Planet.class)
    public ResponseEntity findPlanetById(@PathVariable String id) {

        return new ResponseEntity<>(planetService.findPlanetById(id), HttpStatus.OK);
    }

    /**
     * Busca Planeta por Nome
     *
     * @param name Nome do Planeta a ser localizado
     * @return Retorna um Planeta
     */
    @GetMapping("/{name}")
    @ApiOperation(value = "Busca uma planeta por Nome", response = Planet.class)
    public ResponseEntity findPlanetByName(@PathVariable String name) {

        return new ResponseEntity<>(planetService.findPlanetByName(name), HttpStatus.OK);
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

        planetService.deletePlanetById(id);

        return new ResponseEntity<>("Planeta excluido com sucesso!", HttpStatus.OK);
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
