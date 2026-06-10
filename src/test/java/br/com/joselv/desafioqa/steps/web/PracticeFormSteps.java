package br.com.joselv.desafioqa.steps.web;

import br.com.joselv.desafioqa.pages.PracticeFormPage;
import br.com.joselv.desafioqa.support.DriverFactory;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PracticeFormSteps {

    private static final String ARQUIVO_UPLOAD = "src/test/resources/upload/arquivo-upload.txt";

    private final PracticeFormPage practiceFormPage = new PracticeFormPage(DriverFactory.getDriver());

    @Quando("preencho todo o formulário com valores aleatórios")
    public void preencherFormulario() {
        practiceFormPage.preencherFormularioComDadosAleatorios();
    }

    @E("anexo um arquivo .txt no formulário")
    public void anexarArquivoTxt() {
        practiceFormPage.anexarArquivo(Paths.get(ARQUIVO_UPLOAD).toAbsolutePath().toString());
    }

    @E("submeto o formulário")
    public void submeterFormulario() {
        practiceFormPage.submeter();
    }

    @Então("um popup de confirmação deve ser exibido")
    public void validarPopupExibido() {
        assertEquals("Thanks for submitting the form", practiceFormPage.obterTituloPopup());
    }

    @E("eu fecho o popup de confirmação")
    public void fecharPopup() {
        practiceFormPage.fecharPopup();
    }
}
