# STAR WARS API

Nossos associados são aficionados por Star Wars e com isso, queremos criar um jogo com algumas informações da franquia.
 
Para possibilitar a equipe de front criar essa aplicação, queremos desenvolver uma API que contenha os dados dos planetas. 
Requisitos:

- A API deve ser REST
- Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, sendo inserido manualmente:

    - Nome
    - Clima
    - Terreno

- Para cada planeta também devemos ter a quantidade de aparições em filmes, que podem ser obtidas pela API pública do Star Wars:  https://swapi.co/

Funcionalidades desejadas: 

- [x] Adicionar um planeta (com nome, clima e terreno)
- [x] Listar planetas
- [x] Buscar por nome
- [x] Buscar por ID
- [x] Remover planeta

May the force be with you!

## Arquivos do Projeto

```text
├── docker-compose.yml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br
│   │   │       └── com
│   │   │           └── tulinux
│   │   │               └── starwarsapi
│   │   │                   ├── config
│   │   │                   │   ├── PlanetCommandLineRunner.java
│   │   │                   │   └── SwaggerConfig.java
│   │   │                   ├── errors
│   │   │                   │   ├── BadRequestException.java
│   │   │                   │   └── ResourceNotFoundException.java
│   │   │                   ├── models
│   │   │                   │   └── Planet.java
│   │   │                   ├── repositories
│   │   │                   │   └── PlanetRespository.java
│   │   │                   ├── resources
│   │   │                   │   └── PlanetResource.java
│   │   │                   ├── services
│   │   │                   │   └── PlanetService.java
│   │   │                   └── StarWarsApiApplication.java
│   │   └── resources
│   │       ├── application-prod.properties
│   │       ├── application.properties
│   │       └── application-test.properties
│   └── test
│       └── java
│           └── br
│               └── com
│                   └── tulinux
│                       └── starwarsapi
│                           ├── repositories
│                           │   └── PlanetRespositoryTest.java
│                           ├── resources
│                           │   └── PlanetResourceTest.java
│                           ├── services
│                           │   └── PlanetServiceTest.java
│                           └── StarWarsApiApplicationTests.java
└── star-wars-api.iml

23 directories, 23 files

```

## Uso

```bash
$ mvn spring-boot:run
```

Endereço local:  `http://localhost:8080/`

Documentação Swagger : `http://localhost:8080/swagger-ui.html`

## Desenvolvimento

### Pré-requisitos

* [Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org)
* [Docker CE](https://www.docker.com/community-edition)
* [Docker Compose](https://docs.docker.com/compose/)

### Clone do respositório

```bash
$ git clone https://github.com/deyvedvm/star-wars-api.git
```

### Executando pelo Docker Compose

```bash
$ cd star-wars-api/

$ mvn package

$ docker-compose build

$ docker-compose up

```

A aplicação ira responder em `http://localhost:8182/`


# Autor

Deyve Vieira 

[Twitter](https://twitter.com/deyvedvm)

[Github](https://github.com/deyvedvm)