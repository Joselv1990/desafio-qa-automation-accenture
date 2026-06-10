package br.com.joselv.desafioqa.pages;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PracticeFormPage extends BasePage {

    private final Faker faker = new Faker();

    private final By campoPrimeiroNome = By.id("firstName");
    private final By campoSobrenome = By.id("lastName");
    private final By campoEmail = By.id("userEmail");
    private final By campoCelular = By.id("userNumber");
    private final By campoDataNascimento = By.id("dateOfBirthInput");
    private final By seletorMes = By.cssSelector(".react-datepicker__month-select");
    private final By seletorAno = By.cssSelector(".react-datepicker__year-select");
    private final By campoMaterias = By.id("subjectsInput");
    private final By campoUpload = By.id("uploadPicture");
    private final By campoEndereco = By.id("currentAddress");
    private final By comboEstado = By.id("state");
    private final By comboCidade = By.id("city");
    private final By opcoesCombo = By.cssSelector("div[id*='-option-']");
    private final By botaoEnviar = By.id("submit");
    private final By tituloPopup = By.id("example-modal-sizes-title-lg");
    private final By botaoFecharPopup = By.id("closeLargeModal");

    private static final List<String> MATERIAS = List.of(
            "Maths", "English", "Physics", "Chemistry", "Computer Science",
            "Economics", "Arts", "Biology", "History");

    public PracticeFormPage(WebDriver driver) {
        super(driver);
    }

    public void preencherFormularioComDadosAleatorios() {
        removerAnuncios();
        digitar(campoPrimeiroNome, faker.name().firstName());
        digitar(campoSobrenome, faker.name().lastName());
        digitar(campoEmail, faker.internet().emailAddress());
        selecionarGeneroAleatorio();
        digitar(campoCelular, faker.number().digits(10));
        selecionarDataNascimentoAleatoria();
        selecionarMateriaAleatoria();
        selecionarHobbyAleatorio();
        digitar(campoEndereco, faker.address().streetAddress());
        selecionarEstadoECidadeAleatorios();
    }

    public void anexarArquivo(String caminhoAbsoluto) {
        aguardarVisivel(campoUpload).sendKeys(caminhoAbsoluto);
    }

    public void submeter() {
        removerAnuncios();
        clicar(botaoEnviar);
    }

    public String obterTituloPopup() {
        return aguardarVisivel(tituloPopup).getText();
    }

    public void fecharPopup() {
        clicar(botaoFecharPopup);
        if (!desapareceuEm(tituloPopup, 3)) {
            // o botão Close do modal não responde ao clique na versão atual do site; ESC fecha o modal
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            aguardarInvisibilidade(tituloPopup);
        }
    }

    private void selecionarGeneroAleatorio() {
        int opcao = ThreadLocalRandom.current().nextInt(1, 4);
        clicar(By.cssSelector("label[for='gender-radio-" + opcao + "']"));
    }

    private void selecionarDataNascimentoAleatoria() {
        clicar(campoDataNascimento);
        new Select(aguardarVisivel(seletorMes))
                .selectByIndex(ThreadLocalRandom.current().nextInt(12));
        new Select(aguardarVisivel(seletorAno))
                .selectByValue(String.valueOf(ThreadLocalRandom.current().nextInt(1950, 2006)));
        int dia = ThreadLocalRandom.current().nextInt(1, 29);
        clicar(By.cssSelector(String.format(
                ".react-datepicker__day--%03d:not(.react-datepicker__day--outside-month)", dia)));
    }

    private void selecionarMateriaAleatoria() {
        String materia = MATERIAS.get(ThreadLocalRandom.current().nextInt(MATERIAS.size()));
        WebElement campo = aguardarVisivel(campoMaterias);
        rolarAteElemento(campo);
        campo.sendKeys(materia);
        campo.sendKeys(Keys.ENTER);
    }

    private void selecionarHobbyAleatorio() {
        int hobby = ThreadLocalRandom.current().nextInt(1, 4);
        clicar(By.cssSelector("label[for='hobbies-checkbox-" + hobby + "']"));
    }

    private void selecionarEstadoECidadeAleatorios() {
        selecionarOpcaoAleatoria(comboEstado);
        selecionarOpcaoAleatoria(comboCidade);
    }

    private void selecionarOpcaoAleatoria(By combo) {
        clicar(combo);
        List<WebElement> opcoes = wait.until(d -> {
            List<WebElement> encontradas = d.findElements(opcoesCombo);
            return encontradas.isEmpty() ? null : encontradas;
        });
        opcoes.get(ThreadLocalRandom.current().nextInt(opcoes.size())).click();
    }
}
