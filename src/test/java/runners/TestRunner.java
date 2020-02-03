package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Creates cucumber reports
        plugin = {
                "pretty:target/pretty/cucumber-pretty.txt",
                "html:target/html-reports",
                "json:target/html-reports/cucumber.json",
                "junit:target/junit/junit-report.xml"
        },
        //Path to feature file
        features = {
                "src/test/resources/features"
        },
        // Tags to be executed
        tags = {
                "@web"
        },
        //Step definition package name (Note: make sure to have this package on current directory)
        glue = {
                "stepdefinitions"
        },
        //Creates auto method name in camelCase
        snippets = cucumber.api.SnippetType.CAMELCASE,
        monochrome = true,
        dryRun = false
)

public class TestRunner {

}