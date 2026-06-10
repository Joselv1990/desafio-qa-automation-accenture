package br.com.joselv.desafioqa.steps.api;

import br.com.joselv.desafioqa.api.AccountClient;
import br.com.joselv.desafioqa.api.BookStoreClient;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions da Parte 1 do desafio (API BookStore).
 * Os passos compartilham o estado do cenário (usuário, token e livros)
 * através dos atributos desta classe, garantindo a execução contínua do fluxo.
 */
public class BookStoreApiSteps {

    private final AccountClient accountClient = new AccountClient();
    private final BookStoreClient bookStoreClient = new BookStoreClient();

    private String userName;
    private String password;
    private String userId;
    private String token;
    private List<String> livrosDisponiveis = new ArrayList<>();
    private final List<String> isbnsAlugados = new ArrayList<>();

    @Dado("que eu crio um novo usuário com dados válidos")
    public void criarNovoUsuario() {
        userName = "joselv_" + UUID.randomUUID().toString().substring(0, 8);
        password = String.format("P@ssw0rd%02d", ThreadLocalRandom.current().nextInt(100));

        Response resposta = accountClient.criarUsuario(userName, password);

        assertEquals(201, resposta.statusCode(),
                "Falha ao criar usuário: " + resposta.getBody().asString());
        userId = resposta.jsonPath().getString("userID");
        assertNotNull(userId, "O userID não foi retornado na criação do usuário");
        assertEquals(userName, resposta.jsonPath().getString("username"));
        assertTrue(resposta.jsonPath().getList("books").isEmpty(),
                "Um usuário recém-criado não deveria possuir livros");
    }

    @E("gero um token de acesso para o usuário")
    public void gerarTokenDeAcesso() {
        Response resposta = accountClient.gerarToken(userName, password);

        assertEquals(200, resposta.statusCode(),
                "Falha ao gerar token: " + resposta.getBody().asString());
        assertEquals("Success", resposta.jsonPath().getString("status"));
        token = resposta.jsonPath().getString("token");
        assertNotNull(token, "O token não foi retornado");
    }

    @Então("o usuário criado deve estar autorizado")
    public void validarUsuarioAutorizado() {
        Response resposta = accountClient.verificarAutorizacao(userName, password);

        assertEquals(200, resposta.statusCode(),
                "Falha ao verificar autorização: " + resposta.getBody().asString());
        assertEquals("true", resposta.getBody().asString().trim(),
                "O usuário criado deveria estar autorizado");
    }

    @Quando("eu listo os livros disponíveis na BookStore")
    public void listarLivrosDisponiveis() {
        Response resposta = bookStoreClient.listarLivros();

        assertEquals(200, resposta.statusCode(),
                "Falha ao listar livros: " + resposta.getBody().asString());
        livrosDisponiveis = resposta.jsonPath().getList("books.isbn");
        assertFalse(livrosDisponiveis.isEmpty(),
                "A BookStore deveria possuir livros disponíveis");
    }

    @E("alugo {int} livros de livre escolha para o usuário")
    public void alugarLivros(int quantidade) {
        List<String> opcoes = new ArrayList<>(livrosDisponiveis);
        Collections.shuffle(opcoes);
        isbnsAlugados.addAll(opcoes.subList(0, quantidade));

        Response resposta = bookStoreClient.alugarLivros(userId, isbnsAlugados, token);

        assertEquals(201, resposta.statusCode(),
                "Falha ao alugar livros: " + resposta.getBody().asString());
        List<String> isbnsRetornados = resposta.jsonPath().getList("books.isbn");
        assertEquals(isbnsAlugados.size(), isbnsRetornados.size());
        assertTrue(isbnsRetornados.containsAll(isbnsAlugados),
                "A resposta do aluguel deveria conter os ISBNs escolhidos");
    }

    @Então("os detalhes do usuário devem conter os livros alugados")
    public void validarDetalhesDoUsuario() {
        Response resposta = accountClient.buscarDetalhesUsuario(userId, token);

        assertEquals(200, resposta.statusCode(),
                "Falha ao buscar detalhes do usuário: " + resposta.getBody().asString());
        assertEquals(userId, resposta.jsonPath().getString("userId"));
        assertEquals(userName, resposta.jsonPath().getString("username"));

        List<String> livrosDoUsuario = resposta.jsonPath().getList("books.isbn");
        assertEquals(isbnsAlugados.size(), livrosDoUsuario.size(),
                "O usuário deveria possuir exatamente os livros alugados");
        assertTrue(livrosDoUsuario.containsAll(isbnsAlugados),
                "Os detalhes do usuário deveriam conter os ISBNs dos livros alugados");
    }
}
