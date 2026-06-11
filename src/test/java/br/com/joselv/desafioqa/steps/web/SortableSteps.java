package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.pages.SortablePage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortableSteps {

    private final SortablePage sortablePage = new SortablePage(DriverFactory.getDriver());

    @E("embaralho os elementos da lista caso já estejam ordenados")
    public void embaralharSeJaOrdenados() {
        sortablePage.embaralharSeJaOrdenada();
    }

    @Quando("ordeno os elementos da lista em ordem crescente utilizando drag and drop")
    public void ordenarElementosComDragAndDrop() {
        sortablePage.ordenarCrescente();
    }

    @Então("os elementos da lista devem estar em ordem crescente")
    public void validarOrdemCrescente() {
        assertTrue(sortablePage.estaEmOrdemCrescente(),
                "A lista deveria estar em ordem crescente, mas está: " + sortablePage.obterOrdemAtual());
    }
}
