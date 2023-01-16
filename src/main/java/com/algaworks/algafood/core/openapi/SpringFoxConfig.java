package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.*;
import com.algaworks.algafood.api.v1.openapi.model.*;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public Docket apiDocketV1() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        Resource.class, File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModel.class),  CozinhasModelOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoModel.class),  PedidoResumoModelOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModel.class), CidadesModelOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                        UsuariosModelOpenApi.class))
//                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(authenticationScheme()))
                .securityContexts(List.of(securityContext()))
                .apiInfo(apiInfoV1())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões"),
                        new Tag("Root Entry Point", "Ponto de Entrada da Aplicação"));
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("AlgaFood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private List<GrantType> grantTypes() {
        return List.of(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes() {
        return List.of(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de leitura"));
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaFood", "https://algaworks.com.br", "contato@algaworks.com"))
                .build();
    }

    @Bean
    public Docket apiDocketV2() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
                        CozinhasModelV2OpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
                        CidadesModelV2OpenApi.class))

                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(authenticationScheme()))
                .securityContexts(List.of(securityContext()))

                .apiInfo(apiInfoV2())

                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"));
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(new Contact("AlgaFood", "https://algaworks.com.br", "contato@algaworks.com"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    private HttpAuthenticationScheme authenticationScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }

}