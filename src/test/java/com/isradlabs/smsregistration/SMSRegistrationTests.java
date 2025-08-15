package com.isradlabs.smsregistration;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@SpringBootTest
class SMSRegistrationTests {
  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;
  ApplicationFormStudentDTO  applicationFormDTO=null;
  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {

    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation)).build();

    applicationFormDTO =  getApplicationFormDTO();
        /*Stream.of(
        getApplicationFormDTO())
        .collect(Collectors.toList());
        */
  }
  // @Ignore Junit 4 
  @Disabled ("Need more Time to fix") //Junit 5
  @Test

  public void testSubmitApplicationForm() throws Exception {
    applicationFormDTO.setAgreement("YES");
      String applicationFormDTOJson=new ObjectMapper().writeValueAsString(applicationFormDTO);
      System.out.println("applicationFormDTOJson:"+applicationFormDTOJson.toString());
      mockMvc.perform(post("/v1/applicationForm/submitApplicationForm")
              .content(applicationFormDTOJson)
              .contentType("application/json")).andDo(print())
              .andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.content().json(applicationFormDTOJson))
              .andDo(document("{methodName}",
                      preprocessRequest(prettyPrint()),
                      preprocessResponse(prettyPrint())));
  }
  //@Ignore -Junit 4
  @Disabled ("Need more Time to fix")
  @Test

  public void testSubmitApplicationFormNoAgreement() throws Exception {
    applicationFormDTO.setAgreement("YES");
      String applicationFormDTOJson=new ObjectMapper().writeValueAsString(applicationFormDTO);
      mockMvc.perform(post("/v1/applicationForm/submitApplicationForm")
              .content(applicationFormDTOJson)
              .contentType("application/json")).andDo(print())
              .andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.content().json(applicationFormDTOJson))
              .andDo(document("{methodName}",
                      preprocessRequest(prettyPrint()),
                      preprocessResponse(prettyPrint())));
  }
  
  @Test
  void contextLoads() {}

  private ApplicationFormStudentDTO getApplicationFormDTO() {
	  ApplicationFormStudentDTO applicationFormDTO=new ApplicationFormStudentDTO();
    applicationFormDTO.setAgreement("YES"); 
    return applicationFormDTO;
  }
}
