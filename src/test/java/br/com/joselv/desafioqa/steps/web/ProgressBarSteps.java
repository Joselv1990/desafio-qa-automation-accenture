package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.pages.ProgressBarPage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgressBarSteps {

    private final ProgressBarPage progressBarPage = new ProgressBarPage(DriverFactory.getDriver());

    @Quando("clico no botão Start da progress bar")
    public void clicarNoBotaoStart() {
        progressBarPage.iniciar();
    }

    @E("paro a progress bar antes de atingir {int}%")
    public void pararProgressBarAntesDe(int limite) {
        progressBarPage.pararAntesDe(limite);
    }

    @Então("o valor da progress bar deve ser menor ou igual a {int}%")
    public void validarValorMenorOuIgualA(int limite) {
        int valor = progressBarPage.obterValor();
        assertTrue(valor <= limite,
                "A progress bar deveria estar em no máximo " + limite + "%, mas está em " + valor + "%");
    }

    @Quando("clico no botão Start novamente e aguardo a progress bar atingir 100%")
    public void reiniciarEAguardarConclusao() {
        progressBarPage.iniciar();
        progressBarPage.aguardarConclusao();
    }

    @E("reseto a progress bar")
    public void resetarProgressBar() {
        progressBarPage.resetar();
    }

    @Então("o valor da progress bar deve ser {int}%")
    public void validarValorExato(int valorEsperado) {
        assertEquals(valorEsperado, progressBarPage.obterValor());
    }
}
