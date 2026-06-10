package br.com.joselv.desafioqa.runners;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Runner responsável por executar todos os arquivos .feature
 * localizados em src/test/resources/features através do JUnit 5.
 *
 * Execução: mvn test
 * Apenas API: mvn test -Dcucumber.filter.tags="@api"
 * Apenas Web: mvn test -Dcucumber.filter.tags="@web"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunCucumberTest {
}
