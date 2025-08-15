package com.isradlabs.sms.applicationform.sit;



import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import io.cucumber.java.Before;
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.atc")

@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,  value="pretty,html:target/cucumber, json:target/cucumber-execution.json")//"${plugin_property_name}"): tried this not working
//@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @Skip")


public class CucumberSuiteIT {

  @Before
  public void after() {
    //System.out.println("testContext:"+testContext);
    //testContext.
    System.out.println("Before ");
  }
}
