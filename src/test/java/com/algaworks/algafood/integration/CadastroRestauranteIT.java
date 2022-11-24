package com.algaworks.algafood.integration;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroRestauranteIT {

    private Long quantiadeRestaurantesCadastrados;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private final Long RESTAURANTE_ID_INEXISTENTE = 999L;

    private Restaurante burgerTopRestaurante = Restaurante.builder().build();
    
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/restaurantes";

        this.databaseCleaner.clearTables();
        this.jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-new-york-barbecue.json");
        this.jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        this.jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");
        this.jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

        prepararDados();
    }

    private void prepararDados() {
        Cozinha cozinhaBrasileira = Cozinha.builder().build();
        cozinhaBrasileira.setNome("Brasileira");
        this.cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = Cozinha.builder().build();
        cozinhaAmericana.setNome("Americana");
        this.cozinhaRepository.save(cozinhaAmericana);

        Restaurante loucuraCariocaRestaurante = Restaurante.builder().build();
        loucuraCariocaRestaurante.setNome("Loucura Carioca");
        loucuraCariocaRestaurante.setTaxaFrete(new BigDecimal(10));
        loucuraCariocaRestaurante.setCozinha(cozinhaBrasileira);
        this.restauranteRepository.save(loucuraCariocaRestaurante);

        burgerTopRestaurante = Restaurante.builder().build();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        this.restauranteRepository.save(burgerTopRestaurante);

        this.quantiadeRestaurantesCadastrados = this.restauranteRepository.count();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterQuantidadeRestaurantesCadastrados_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(this.quantiadeRestaurantesCadastrados.intValue()));
    }

    @Test
    public void deveRetornatStatus201_QuandoCadastrarRestaurante() {
        given()
                .body(this.jsonRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", this.burgerTopRestaurante.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(this.burgerTopRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", this.RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornatStatus400_QuandoCadastrarRestauranteSemFrete() {
        given()
                .body(this.jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornatStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
                .body(this.jsonRestauranteSemCozinha)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornatStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
                .body(this.jsonRestauranteComCozinhaInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


}
