package br.com.joselv.desafioqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class SortablePage extends BasePage {

    private static final List<String> ORDEM_CRESCENTE =
            List.of("One", "Two", "Three", "Four", "Five", "Six");

    private final By itensDaLista = By.cssSelector("#demo-tabpane-list .list-group-item");

    public SortablePage(WebDriver driver) {
        super(driver);
    }

    public List<String> obterOrdemAtual() {
        return aguardarVisivel(itensDaLista) == null ? List.of()
                : driver.findElements(itensDaLista).stream().map(WebElement::getText).toList();
    }

    public void embaralharSeJaOrdenada() {
        removerAnuncios();
        if (ORDEM_CRESCENTE.equals(obterOrdemAtual())) {
            List<WebElement> itens = driver.findElements(itensDaLista);
            arrastar(itens.get(0), itens.get(itens.size() - 1));
        }
    }

    public void ordenarCrescente() {
        for (int posicao = 0; posicao < ORDEM_CRESCENTE.size(); posicao++) {
            List<String> ordemAtual = obterOrdemAtual();
            String esperado = ORDEM_CRESCENTE.get(posicao);
            int posicaoAtual = ordemAtual.indexOf(esperado);
            if (posicaoAtual != posicao) {
                List<WebElement> itens = driver.findElements(itensDaLista);
                arrastar(itens.get(posicaoAtual), itens.get(posicao));
            }
        }
    }

    public boolean estaEmOrdemCrescente() {
        return ORDEM_CRESCENTE.equals(obterOrdemAtual());
    }

    private void arrastar(WebElement origem, WebElement destino) {
        rolarAteElemento(origem);
        actions.clickAndHold(origem)
                .pause(Duration.ofMillis(200))
                .moveToElement(destino)
                .pause(Duration.ofMillis(200))
                .release()
                .pause(Duration.ofMillis(300))
                .perform();
    }
}
