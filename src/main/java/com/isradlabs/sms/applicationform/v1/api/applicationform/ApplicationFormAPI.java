package com.isradlabs.sms.applicationform.v1.api.applicationform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.isradlabs.sms.model.dto.student.StudentDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiMetaDataDTO;
import com.isradlabs.sms.model.filters.SearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Application Form", description = "Appliction Form handling APIs")
public interface ApplicationFormAPI {
  @Operation(summary = "Delete Appliction Form",
      description = "Marks the application form deleted (Soft Delete)")
  void deleteData(Long id);


  Page<StudentDTO> search(SearchRequest request);

  UiMetaDataDTO getApplicaitonForm(Long id);

  String all(Pageable page);

  @Operation(summary = "Submit Appliction Form",
      description = "Stores Application form. If id exist in form, thows Exception.")
  UiMetaDataDTO submitApplicationForm(UiMetaDataDTO uiMetaData);


  UiMetaDataDTO getNewApplicaitonForm();

}
