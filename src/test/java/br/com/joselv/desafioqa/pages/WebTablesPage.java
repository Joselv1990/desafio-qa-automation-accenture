package br.com.joselv.desafioqa.pages;

import br.com.joselv.desafioqa.model.RegistroTabela;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebTablesPage extends BasePage {

    private final By botaoNovoRegistro = By.id("addNewRecordButton");
    private final By caixaPesquisa = By.id("searchBox");
    private final By modalRegistro = By.cssSelector(".modal-content");
    private final By campoPrimeiroNome = By.id("firstName");
    private final By campoSobrenome = By.id("lastName");
    private final By campoEmail = By.id("userEmail");
    private final By campoIdade = By.id("age");
    private final By campoSalario = By.id("salary");
    private final By campoDepartamento = By.id("department");
    private final By botaoSalvar = By.id("submit");
    private final By linhasDaTabela = By.cssSelector(".web-tables-wrapper tbody tr");

    public WebTablesPage(WebDriver driver) {
        super(driver);
    }

    public void criarRegistro(RegistroTabela registro) {
        removerAnuncios();
        clicar(botaoNovoRegistro);
        preencherModal(registro);
    }

    public void editarRegistro(String email, RegistroTabela novosDados) {
        pesquisar(email);
        clicar(By.xpath(xpathLinha(email) + "//span[@title='Edit']"));
        preencherModal(novosDados);
    }

    public void deletarRegistro(String email) {
        pesquisar(email);
        By linha = By.xpath(xpathLinha(email));
        clicar(By.xpath(xpathLinha(email) + "//span[@title='Delete']"));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElements(linha).isEmpty());
    }

    public boolean registroExibido(String email) {
        pesquisar(email);
        By linha = By.xpath(xpathLinha(email));
        try {
            // aguarda o filtro estabilizar: a linha aparece ou a tabela fica vazia
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
                    !d.findElements(linha).isEmpty() || d.findElements(linhasDaTabela).isEmpty());
        } catch (TimeoutException e) {
            // o estado final é avaliado abaixo
        }
        return !driver.findElements(By.xpath(xpathLinha(email))).isEmpty();
    }

    public String textoDaLinha(String email) {
        pesquisar(email);
        return aguardarVisivel(By.xpath(xpathLinha(email))).getText();
    }

    private void preencherModal(RegistroTabela registro) {
        aguardarVisivel(modalRegistro);
        digitar(campoPrimeiroNome, registro.primeiroNome());
        digitar(campoSobrenome, registro.sobrenome());
        digitar(campoEmail, registro.email());
        digitar(campoIdade, registro.idade());
        digitar(campoSalario, registro.salario());
        digitar(campoDepartamento, registro.departamento());
        clicar(botaoSalvar);
        aguardarInvisibilidade(modalRegistro);
    }

    private void pesquisar(String termo) {
        WebElement campo = aguardarVisivel(caixaPesquisa);
        campo.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        campo.sendKeys(termo);
    }

    private String xpathLinha(String texto) {
        return "//div[contains(@class,'web-tables-wrapper')]"
                + "//tbody/tr[td[normalize-space()='" + texto + "']]";
    }
}
