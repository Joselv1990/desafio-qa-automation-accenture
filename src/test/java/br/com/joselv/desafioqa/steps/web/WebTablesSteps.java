package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.model.RegistroTabela;
import br.com.joselv.desafioqa.pages.WebTablesPage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTablesSteps {

    private final WebTablesPage webTablesPage = new WebTablesPage(DriverFactory.getDriver());

    private RegistroTabela registroAtual;
    private final List<RegistroTabela> registrosCriados = new ArrayList<>();

    @Quando("crio um novo registro na tabela")
    public void criarNovoRegistro() {
        registroAtual = RegistroTabela.aleatorio();
        webTablesPage.criarRegistro(registroAtual);
    }

    @Então("o registro criado deve ser exibido na tabela")
    public void validarRegistroCriado() {
        validarDadosDoRegistroNaTabela();
    }

    @Quando("edito o registro criado")
    public void editarRegistroCriado() {
        RegistroTabela novosDados = registroAtual.comNovosDados();
        webTablesPage.editarRegistro(registroAtual.email(), novosDados);
        registroAtual = novosDados;
    }

    @Então("o registro editado deve ser exibido na tabela")
    public void validarRegistroEditado() {
        validarDadosDoRegistroNaTabela();
    }

    @Quando("deleto o registro criado")
    public void deletarRegistroCriado() {
        webTablesPage.deletarRegistro(registroAtual.email());
    }

    @Então("o registro não deve mais ser exibido na tabela")
    public void validarRegistroDeletado() {
        assertFalse(webTablesPage.registroExibido(registroAtual.email()),
                "O registro deletado não deveria mais ser exibido na tabela");
    }

    @Quando("crio {int} novos registros de forma dinâmica")
    public void criarRegistrosDinamicamente(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            RegistroTabela registro = RegistroTabela.aleatorio();
            webTablesPage.criarRegistro(registro);
            registrosCriados.add(registro);
        }
    }

    @Então("a tabela deve conter os {int} registros criados")
    public void validarRegistrosCriados(int quantidadeEsperada) {
        assertTrue(registrosCriados.size() == quantidadeEsperada,
                "Deveriam ter sido criados " + quantidadeEsperada + " registros");
        for (RegistroTabela registro : registrosCriados) {
            assertTrue(webTablesPage.registroExibido(registro.email()),
                    "O registro " + registro.email() + " deveria ser exibido na tabela");
        }
    }

    @Quando("deleto todos os registros criados")
    public void deletarTodosOsRegistrosCriados() {
        // o site reaproveita o id interno dos registros criados na sessão e o delete remove por id,
        // então excluir um registro pode remover os demais; deleta apenas os que ainda estão na tabela
        registrosCriados.stream()
                .filter(registro -> webTablesPage.registroExibido(registro.email()))
                .forEach(registro -> webTablesPage.deletarRegistro(registro.email()));
    }

    @Então("nenhum dos registros criados deve ser exibido na tabela")
    public void validarRegistrosDeletados() {
        for (RegistroTabela registro : registrosCriados) {
            assertFalse(webTablesPage.registroExibido(registro.email()),
                    "O registro " + registro.email() + " não deveria mais ser exibido na tabela");
        }
    }

    private void validarDadosDoRegistroNaTabela() {
        String linha = webTablesPage.textoDaLinha(registroAtual.email());
        assertTrue(linha.contains(registroAtual.primeiroNome()), "Primeiro nome não encontrado na linha");
        assertTrue(linha.contains(registroAtual.sobrenome()), "Sobrenome não encontrado na linha");
        assertTrue(linha.contains(registroAtual.idade()), "Idade não encontrada na linha");
        assertTrue(linha.contains(registroAtual.salario()), "Salário não encontrado na linha");
        assertTrue(linha.contains(registroAtual.departamento()), "Departamento não encontrado na linha");
    }
}
