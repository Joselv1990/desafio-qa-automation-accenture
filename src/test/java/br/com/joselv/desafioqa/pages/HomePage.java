package br.com.joselv.desafioqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String URL = "https://demoqa.com/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void acessar() {
        driver.get(URL);
        removerAnuncios();
    }

    public void escolherCard(String nomeCard) {
        By card = By.xpath("//div[contains(@class,'card')]//h5[text()='" + nomeCard + "']");
        clicar(card);
        removerAnuncios();
    }

    public void clicarSubmenu(String nomeSubmenu) {
        By submenu = By.xpath("//span[@class='text' and text()='" + nomeSubmenu + "']");
        clicar(submenu);
        removerAnuncios();
    }
}
