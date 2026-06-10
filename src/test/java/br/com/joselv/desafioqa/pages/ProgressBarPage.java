package br.com.joselv.desafioqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgressBarPage extends BasePage {

    private final By botaoStartStop = By.id("startStopButton");
    private final By botaoReset = By.id("resetButton");
    private final By barraDeProgresso = By.cssSelector("#progressBar [role='progressbar']");

    public ProgressBarPage(WebDriver driver) {
        super(driver);
    }

    public void iniciar() {
        removerAnuncios();
        clicar(botaoStartStop);
    }

    public void pararAntesDe(int limitePercentual) {
        WebElement botao = aguardarClicavel(botaoStartStop);
        // folga para o clique no Stop ocorrer antes do limite
        int pontoDeParada = Math.max(limitePercentual - 15, 5);
        while (obterValor() < pontoDeParada) {
            pausar(50);
        }
        botao.click();
    }

    public int obterValor() {
        String valor = aguardarVisivel(barraDeProgresso).getAttribute("aria-valuenow");
        return (valor == null || valor.isBlank()) ? 0 : Integer.parseInt(valor);
    }

    public void aguardarConclusao() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> obterValor() >= 100);
    }

    public void resetar() {
        clicar(botaoReset);
    }
}
