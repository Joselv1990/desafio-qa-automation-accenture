package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.pages.BrowserWindowsPage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowserWindowsSteps {

    private final BrowserWindowsPage browserWindowsPage = new BrowserWindowsPage(DriverFactory.getDriver());

    @Quando("clico no botão New Window")
    public void clicarNoBotaoNewWindow() {
        browserWindowsPage.clicarNewWindow();
    }

    @Então("uma nova janela deve ser aberta com a mensagem {string}")
    public void validarMensagemDaNovaJanela(String mensagemEsperada) {
        assertEquals(mensagemEsperada, browserWindowsPage.obterMensagemDaNovaJanela());
    }

    @E("eu fecho a nova janela aberta")
    public void fecharNovaJanela() {
        browserWindowsPage.fecharNovaJanela();
        assertEquals(1, browserWindowsPage.quantidadeDeJanelasAbertas());
    }
}
