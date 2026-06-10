package br.com.joselv.desafioqa.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

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
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
