package br.com.tulinux.starwarsapi.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Documento do MongoDB referente a cada Planeta
 */

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    @Id
    @ApiModelProperty(notes = "ID gerado pelo banco de dados")
    private String id;

    @ApiModelProperty(notes = "O nome do Planeta")
    private String name;

    @ApiModelProperty(notes = "Descrição do clima do planeta")
    private String climate;

    @ApiModelProperty(notes = "Descrição do terreno do planeta")
    private String terrain;

    @ApiModelProperty(notes = "Lista de filmes em o planeta apareceu")
    private List films;
}
