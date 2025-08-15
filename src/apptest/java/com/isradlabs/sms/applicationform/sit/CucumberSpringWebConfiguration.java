package com.isradlabs.sms.applicationform.sit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import io.cucumber.spring.CucumberContextConfiguration;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, //SpringBootTest.WebEnvironment.NONE, 
    classes = CucumberSpringContextConfig.class
)
public class CucumberSpringWebConfiguration {

    @Autowired
    protected TestRestTemplate testRestTemplate;

}