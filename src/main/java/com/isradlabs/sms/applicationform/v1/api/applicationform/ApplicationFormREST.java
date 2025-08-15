package com.isradlabs.sms.applicationform.v1.api.applicationform;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.isradlabs.sms.applicationform.service.applicationform.ApplicationFormService;
import com.isradlabs.sms.model.dto.student.StudentDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiMetaDataDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiSelectItemDTO;
import com.isradlabs.sms.model.filters.SearchRequest;

@RestController
@RequestMapping("/v1/applicationForm")

class ApplicationFormREST implements ApplicationFormAPI {
  @Autowired
  ApplicationFormService applicationFormService;

  @Override
  @GetMapping("/")
  public String all(Pageable page) {
    return "Refer API Documentation to use API Calls";
  }

  @Override
  @GetMapping("/{id}")
  public UiMetaDataDTO getApplicaitonForm(@PathVariable("id") final Long id) {

    return applicationFormService.getApplicationForm(id);
  }
  @Override
  @GetMapping("/new")
  public UiMetaDataDTO getNewApplicaitonForm() {

    return applicationFormService.getNewApplicaitonForm();
  }

  @Override
  @PostMapping(value = "/search")
  public Page<StudentDTO> search(@RequestBody SearchRequest request) {

    return applicationFormService.listApplicationForms(request);
  }

  @Override
  @PostMapping(value = "/submitApplicationForm")
  public UiMetaDataDTO submitApplicationForm(@RequestBody UiMetaDataDTO uiMetaData) {
    System.out.println("applicationFormDTO:" + uiMetaData);

    return applicationFormService.submitApplicationForm(uiMetaData);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteData(@PathVariable("id") final Long id) {
    applicationFormService.deleteData(id);
  }

  @GetMapping("/uniqueAcademicYears")
  public List<UiSelectItemDTO> getUniqueCourseNames() {
    return applicationFormService.getUniqueAcademicYearsSortedByAcademicYear();
  }


}
