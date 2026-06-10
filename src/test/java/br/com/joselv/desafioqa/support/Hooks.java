package br.com.joselv.desafioqa.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {

    @Before("@web")
    public void iniciarNavegador() {
        DriverFactory.getDriver();
    }

    @After("@web")
    public void finalizarNavegador(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                salvarScreenshot(scenario.getName(), screenshot);
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }

    private void salvarScreenshot(String nomeCenario, byte[] bytes) {
        try {
            Path destino = Path.of("target", "screenshots",
                    nomeCenario.replaceAll("[^a-zA-Z0-9-_ ]", "") + ".png");
            Files.createDirectories(destino.getParent());
            Files.write(destino, bytes);
        } catch (IOException e) {
            // o screenshot é apoio ao diagnóstico; falha ao salvá-lo não deve mascarar o erro do cenário
        }
    }
}
