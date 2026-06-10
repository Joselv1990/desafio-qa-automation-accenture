package br.com.joselv.desafioqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BrowserWindowsPage extends BasePage {

    private final By botaoNewWindow = By.id("windowButton");
    private final By mensagemNovaJanela = By.id("sampleHeading");

    private String janelaOriginal;

    public BrowserWindowsPage(WebDriver driver) {
        super(driver);
    }

    public void clicarNewWindow() {
        janelaOriginal = driver.getWindowHandle();
        clicar(botaoNewWindow);
    }

    public String obterMensagemDaNovaJanela() {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(janelaOriginal))
                .findFirst()
                .ifPresent(handle -> driver.switchTo().window(handle));
        return aguardarVisivel(mensagemNovaJanela).getText();
    }

    public void fecharNovaJanela() {
        driver.close();
        driver.switchTo().window(janelaOriginal);
    }

    public int quantidadeDeJanelasAbertas() {
        return driver.getWindowHandles().size();
    }
}
