package com.isradlabs.sms.applicationform.service.applicationform;

import java.util.List;
import org.springframework.data.domain.Page;
import com.isradlabs.sms.applicationform.service.BaseService;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormStudent;
import com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO;
import com.isradlabs.sms.model.dto.student.StudentDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiMetaDataDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiSelectItemDTO;
import com.isradlabs.sms.model.filters.SearchRequest;

public interface ApplicationFormService extends BaseService {

  Page<StudentDTO> listApplicationForms(SearchRequest request);

  void updateData(StudentDTO applicationFormDTO);

  void deleteData(Long recordId);

  ApplicationFormStudent insertData(StudentDTO applicationFormDTO);

  UiMetaDataDTO getApplicationForm(Long recordId);
  public void confirmStudentAdmission(Long applicationFormId);

  UiMetaDataDTO<ApplicationFormStudentDTO> submitApplicationForm(UiMetaDataDTO<ApplicationFormStudentDTO> uiMetaData);

  List<UiSelectItemDTO> getUniqueAcademicYearsSortedByAcademicYear();

  UiMetaDataDTO getNewApplicaitonForm();
  

}
