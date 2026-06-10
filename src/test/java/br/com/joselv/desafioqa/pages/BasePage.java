package br.com.joselv.desafioqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    protected WebElement aguardarVisivel(By localizador) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    protected WebElement aguardarClicavel(By localizador) {
        return wait.until(ExpectedConditions.elementToBeClickable(localizador));
    }

    protected void aguardarInvisibilidade(By localizador) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(localizador));
    }

    protected boolean desapareceuEm(By localizador, int segundos) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(segundos))
                    .until(ExpectedConditions.invisibilityOfElementLocated(localizador));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clicar(By localizador) {
        WebElement elemento = aguardarClicavel(localizador);
        rolarAteElemento(elemento);
        try {
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            // anúncios do demoqa.com podem sobrepor o elemento
            clicarViaJs(elemento);
        }
    }

    protected void clicarViaJs(WebElement elemento) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
    }

    protected void digitar(By localizador, String texto) {
        WebElement elemento = aguardarVisivel(localizador);
        rolarAteElemento(elemento);
        elemento.clear();
        elemento.sendKeys(texto);
    }

    protected void rolarAteElemento(WebElement elemento) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", elemento);
    }

    protected void removerAnuncios() {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('#fixedban, footer, iframe[id^=\"google_ads\"]')"
                        + ".forEach(e => e.remove());");
    }

    protected void pausar(long milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
