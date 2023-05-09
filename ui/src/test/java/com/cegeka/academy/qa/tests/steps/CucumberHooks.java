package com.cegeka.academy.qa.tests.steps;


import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CucumberHooks {

    //IntelliJ shows this @Autowired driver highlighted as red because @Autowired doesn't normally work in classes that are not spring beans
    //It DOES work, however because you have a "glue" class annotated with Cucumber's @CucumberContextConfiguration  and Spring's @ContextConfiguration where you point to context configuration class. Take a look at BaseSteps and you find them there.
    @Autowired
    private WebDriver driver;

    @Before
    public void setupBeforeScenario() {
        System.out.println("Perform before scenario cucumber hook");
        System.out.println("Driver instance " +  driver);

    }
    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario, scenario.getName() + " - " + scenario.getStatus());
        }
    }

    private Map<String, byte[]> screenshots = new HashMap<>();
    private void takeScreenshot(Scenario scenarioName, String stepName) {

        // Take screenshot as bytes
        try{
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        // Define destination folder for the screenshot
        String destinationFolder = "target/screenshots/" + scenarioName;
        File folder = new File(destinationFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // Define destination file for the screenshot
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotName = stepName.replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + timestamp + ".png";
        String destinationFile = destinationFolder + "/" + screenshotName;

        // Save the screenshot bytes to the destination file
        FileUtils.writeByteArrayToFile(new File(destinationFile), screenshotBytes);

        // Log the location of the screenshot
        System.out.println("Screenshot saved to: " + destinationFile);

        // Attach the screenshot bytes to the scenario
        scenarioName.attach(screenshotBytes, "image/img", screenshotName);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}
