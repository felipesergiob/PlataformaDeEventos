package com.plataforma.dominio.usuario;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/com/plataforma/dominio/usuario/features",
    glue = {"com.plataforma.dominio.usuario", "com.plataforma.compartilhado"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "junit:target/cucumber-reports/CucumberTestReport.xml"
    },
    monochrome = true,
    tags = "not @ignorar"
)
public class TestRunner {
} 