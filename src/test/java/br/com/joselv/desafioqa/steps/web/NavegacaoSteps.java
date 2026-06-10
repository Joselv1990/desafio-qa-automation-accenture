package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.pages.HomePage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;

public class NavegacaoSteps {

    private final HomePage homePage = new HomePage(DriverFactory.getDriver());

    @Dado("que eu acesso o site da DemoQA")
    public void acessarSite() {
        homePage.acessar();
    }

    @Quando("escolho a opção {string} na página inicial")
    public void escolherOpcaoNaPaginaInicial(String nomeCard) {
        homePage.escolherCard(nomeCard);
    }

    @Quando("clico no submenu {string}")
    public void clicarNoSubmenu(String nomeSubmenu) {
        homePage.clicarSubmenu(nomeSubmenu);
    }
}
