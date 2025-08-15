package com.isradlabs.sms.applicationform.service.applicationform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isradlabs.sms.applicationform.repository.applicationform.ApplicationFormParentRepo;
import com.isradlabs.sms.applicationform.repository.applicationform.ApplicationFormRepo;
import com.isradlabs.sms.applicationform.repository.applicationform.StudentRepo;
import com.isradlabs.sms.model.A2BConverter;
import com.isradlabs.sms.model.constants.AddressTypeEnum;
import com.isradlabs.sms.model.constants.ApplicationModeEnum;
import com.isradlabs.sms.model.constants.ApplicationStatusEnum;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormAddress;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormParent;
import com.isradlabs.sms.model.domain.applicationform.ApplicationFormStudent;
import com.isradlabs.sms.model.domain.student.Student;
import com.isradlabs.sms.model.domain.student.StudentAddress;
import com.isradlabs.sms.model.domain.student.StudentParent;
import com.isradlabs.sms.model.dto.applicationform.ApplicationFormParentDTO;
import com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO;
import com.isradlabs.sms.model.dto.image.ImageRenameDTO;
import com.isradlabs.sms.model.dto.student.StudentDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiMetaDataDTO;
import com.isradlabs.sms.model.dto.uimetadata.UiSelectItemDTO;
import com.isradlabs.sms.model.filters.SearchRequest;
import com.isradlabs.sms.model.filters.SearchSpecification;
import jakarta.transaction.Transactional;

@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {
  @Autowired
  ApplicationFormRepo applicationFormRepo;
  @Autowired
  ApplicationFormParentRepo applicationFormParentRepo;
  @Autowired 
  StudentRepo studentRepo;
  @Autowired
  SequenceProviderServiceImpl sequenceProviderService;
  private static A2BConverter a2bConverter = new A2BConverter();
  static {
/*
    a2bConverter.putProperty("applicationFormStudentDTO", "applicationFormStudent");
    a2bConverter.putProperty("applicationFormParentListDTO", "applicationFormParents");
    a2bConverter.putClass("com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO",
        "com.isradlabs.sms.model.domain.applicationform.ApplicationFormStudent");
    a2bConverter.putClass("com.isradlabs.sms.model.dto.applicationform.ApplicationFormParentDTO",
        "com.isradlabs.sms.model.domain.applicationform.ApplicationFormParent");
        */
  }

  // @Override
  public Page<StudentDTO> listApplicationForms(SearchRequest request) {

    SearchSpecification<ApplicationFormStudent> specification = new SearchSpecification<>(request);
    Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    Page<ApplicationFormStudent> pageData = applicationFormRepo.findAll(specification, pageable);
    ModelMapper modelMapper = new ModelMapper();
    Page<StudentDTO> applicationFormDTOPage = pageData.map(entity -> {
      System.out.println(entity.toString());
      StudentDTO dto = modelMapper.map(entity, StudentDTO.class);// p.employeEntityToDto(entity);
      return dto;
    });
    return applicationFormDTOPage;

  }

  @Override
  public void updateData(StudentDTO applicationFormDTO) {

    try {

      ApplicationFormStudent applicationFormOptional =
          applicationFormRepo.findByIdWithActive(applicationFormDTO.getId()).orElseThrow(//
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                  "APF-100-Application Form might be deleted or may not exist")//
          );

      applicationFormRepo.save(applicationFormOptional);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "APF-501-Marking Application Form delete failed");
    }
  }

  @Override
  public void deleteData(Long recordId) {
    try {

      ApplicationFormStudent applicationFormOptional =
          applicationFormRepo.findByIdWithActive(recordId).orElseThrow(//
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                  "APF-100-Application Form might be deleted or may not exist")//
          );
      applicationFormOptional.setSoftDelete(true);
      applicationFormRepo.save(applicationFormOptional);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "APF-501-Marking Application Form delete failed");
    }
  }

  @Override
  public ApplicationFormStudent insertData(StudentDTO applicationFormDTO) {
    try {

      if (applicationFormDTO.getId() != null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "APF-110-ID is present. Try with different option");
      }
      System.out.println("Agreement:" + applicationFormDTO.getAgreement());
      if (!applicationFormDTO.getAgreement()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "APF-111-Agreement should be accepted");
      }

      ModelMapper modelMapper = new ModelMapper();

      ApplicationFormStudent applicationForm =
          modelMapper.map(applicationFormDTO, ApplicationFormStudent.class);// p.employeEntityToDto(entity);
      System.out.println(applicationFormDTO.getFirstNameOfStudent());
      System.out.println(applicationForm.getFirstNameOfStudent());
      return applicationFormRepo.save(applicationForm);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "APF-510-Application Form Save failed", e);
    }

  }

  @Override
  public UiMetaDataDTO<ApplicationFormStudentDTO> getApplicationForm(Long recordId) {
    ApplicationFormStudentDTO dto=null;
    try {
      if (recordId != null && recordId > 0) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ApplicationFormStudent applicationFormOptional =
            applicationFormRepo.findByIdWithActive(recordId).orElseThrow(//
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "APF-120-Application Form might be deactivated or may not exist")//
            );
        // tryWithJSON(applicationFormOptional);
        dto = convertB2A(applicationFormOptional);
        //convertA2B(dto);
        // dto = modelMapper.map(applicationFormOptional, ApplicationFormStudentDTO.class);//
        // p.employeEntityToDto(entity);
        /*
         * for(int i=1;i<10;i++){ ApplicationFormParentDTO applicationFormParentDTO=new
         * ApplicationFormParentDTO(); applicationFormParentDTO.setId(Long.valueOf(i));
         * applicationFormParentDTO.setRelation("ssss"+i);
         * dto.getApplicationFormParentListDTO().add( applicationFormParentDTO); }
         * 
         * ApplicationFormParentDTO applicationFormParentDTO=new ApplicationFormParentDTO();
         * applicationFormParentDTO.setRelation("ssss"); dto.getApplicationFormParentListDTO().add(
         * applicationFormParentDTO);
         */
      } 

      System.out.println("Getting application form details");
      RestTemplate restTemplate = new RestTemplate();
      String uri = "http://localhost:8080/v1/metadata/page/APPLICATION_FORM";
      String result = restTemplate.getForObject(uri, String.class);
      System.out.println("Result:" + result);
      ObjectMapper objectMapper = new ObjectMapper();
      UiMetaDataDTO<ApplicationFormStudentDTO> uiMetaDataDTO = objectMapper.readValue(result, UiMetaDataDTO.class);
     
      uiMetaDataDTO.setDto(dto);
      System.out.println("uiMetaDataDTO:" + uiMetaDataDTO);
      return uiMetaDataDTO;
      
      //return null;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "APF-520-Getting Application Form failed", e);
    }
  }

  private ApplicationFormStudentDTO convertB2A(ApplicationFormStudent applicationFormStudent)
      throws JsonMappingException, JsonProcessingException {
    String applicationFormStudentJson = applicationFormStudent.toString();

    System.out.println("B JSON:" + applicationFormStudentJson);

    String applicationFormStudentB2AJson = a2bConverter.convertB2A(applicationFormStudentJson);

    System.out.println("After B2A Conversion:" + applicationFormStudentB2AJson);
    ObjectMapper modelMapper = new ObjectMapper();
    // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    ApplicationFormStudentDTO applicationFormStudentDTO =
        modelMapper.readValue(applicationFormStudentB2AJson, ApplicationFormStudentDTO.class);
    try {
      String json = modelMapper.writeValueAsString(applicationFormStudentDTO);
      System.out.println("B2A JSON:" + json);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return applicationFormStudentDTO;

   // return null;
  }

  private ApplicationFormStudent convertA2B(ApplicationFormStudentDTO applicationFormStudentDTO) {
    ApplicationFormStudent applicationFormStudentA2B=null;
   
    try {
      String applicationFormStudentDTOJsonString = applicationFormStudentDTO.toString();

      String a2bJson = a2bConverter.convertA2B(applicationFormStudentDTOJsonString);

      System.out.println("After A2B Conversion:" + a2bJson);
      ObjectMapper modelMapper = new ObjectMapper();
      // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
       applicationFormStudentA2B =
          modelMapper.readValue(a2bJson, ApplicationFormStudent.class);
      String json = modelMapper.writeValueAsString(applicationFormStudentA2B);
      System.out.println("A2B JSON:" + json);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return applicationFormStudentA2B;
  }

  @Override
  public void confirmStudentAdmission(Long applicationFormId) {
    // TODO - Copy all records from applicaiton form to Students related
    // tables with
    // Transactional
    ApplicationFormStudent applicationFormOptional =
        applicationFormRepo.findByIdWithActive(applicationFormId).orElseThrow(//
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "APF-120-Application Form might be deactivated or may not exist")//
        );
    ApplicationStatusEnum applicationStatus = applicationFormOptional.getApplicationStatus();
    switch (applicationStatus) {
      case ADMISSION_CONFIRMED:
        // Copy StudentApplicationForm
        // B:ApplicationformStudent
        // A:Student
        // A,B
        a2bConverter.putProperty("applicationFormStudentDTO", "applicationFormStudent");
        a2bConverter.putProperty("studentParents", "applicationFormParents");
        a2bConverter.putProperty("studentAddresses", "applicationFormAddresses");

        String studentJson = a2bConverter.convertB2A(applicationFormOptional.toString());
        System.out.println("Student JSON:" + studentJson);
        Student student;
        try {
          student = a2bConverter.convertToObject(studentJson, Student.class);
          student.setId(null); //
          for (StudentParent studentParents : student.getStudentParents()) {
            studentParents.setId(null);
          }
          for (StudentAddress studentAddress : student.getStudentAddresses()) {
            studentAddress.setId(null);
          }
          // System.out.println("Student BO JSON:" + student.toString());
          student=studentRepo.save(student);
          
          StudentAddress  studentAddress=new StudentAddress();
          studentAddress.setAddressType(AddressTypeEnum.COMMUNICATION);
          studentAddress.setAddressLine1("SDFSDFSDFSDF");
          studentAddress.setStudent(student);
         } catch (JsonProcessingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        // a2bConverte.convertObject();r
        // Copy parents
        // Copyy Address
        // Map Student -> Class Map
        break;
      default:
        break;

    }

  }

  @Override
  @Transactional
  public UiMetaDataDTO<ApplicationFormStudentDTO> submitApplicationForm(UiMetaDataDTO<ApplicationFormStudentDTO> uiMetaData)  {

    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper objectMapper = new ObjectMapper();
    // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    System.out.println("uiMetaData.getDto():" + uiMetaData.getDto());
    //ApplicationFormStudentDTO applicationFormStudentDTO =uiMetaData.getDto(); //java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to class com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO
    
    //applicationFormStudentDTO;
    try {
      //ApplicationFormStudentDTO applicationFormStudentDTO = objectMapper.readValue(uiMetaData.getDto().toString(), ApplicationFormStudentDTO.class);////java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to class com.isradlabs.sms.model.dto.applicationform.ApplicationFormStudentDTO
      
      
      ApplicationFormStudentDTO applicationFormStudentDTO =
          modelMapper.map(uiMetaData.getDto(), ApplicationFormStudentDTO.class);
      /*
      for (ApplicationFormParentDTO iterable_element : applicationFormStudentDTO
          .getApplicationFormParentListDTO()) {
        System.out.println(iterable_element);
      }
      */
      
      for (ApplicationFormParentDTO applicationFormParentDTO : applicationFormStudentDTO
          .getApplicationFormParents()) {
          System.out.println("applicationFormParentDTO:" + applicationFormParentDTO);
        //applicationFormParentDTO.setApplicationFormStudentDTO(applicationFormStudentDTO);
        if (applicationFormParentDTO.getId() <= 0) {
           applicationFormParentDTO.setId(null);
          
        }
      }
      ApplicationFormStudent applicationFormStudent = convertA2B(applicationFormStudentDTO);
      
      for (ApplicationFormParent applicationFormParent : applicationFormStudent.getApplicationFormParents()) {
        System.out.println("applicationFormParent:" + applicationFormParent);
       
      }
      //Move Photos Proper Locations
      movePhotosProperLocation(applicationFormStudent);
      
      applicationFormStudent = applicationFormRepo.save(applicationFormStudent);
      //Confirm Student Addmission
      confirmStudentAdmission(applicationFormStudent.getId());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
    return uiMetaData;

  }

  private void movePhotosProperLocation(ApplicationFormStudent applicationFormStudent) {
    
    ImageRenameDTO imageRenameDTO=new ImageRenameDTO();
    imageRenameDTO.setHomeDir(String.valueOf( applicationFormStudent.getId()));
    Map<String,String> moveFiles=new HashMap<>();
    if(applicationFormStudent.getPhoto() !=null) {
      moveFiles.put(applicationFormStudent.getPhoto(), "Child_Photo");
    }
   
    for (ApplicationFormParent applicationFormParent : applicationFormStudent.getApplicationFormParents()) {
      if(applicationFormParent.getPhoto() != null) {
        moveFiles.put(applicationFormParent.getPhoto(), "Parent_Photo_"+applicationFormParent.getRelation());
      }
      
    } 
    imageRenameDTO.setFilesToRename(moveFiles);
    System.out.println("Files Map:"+imageRenameDTO.getFilesToRename());
    
    System.out.println("ImageRenameDTO:" + imageRenameDTO.toString());
    System.out.println("Getting application form details");
    RestTemplate restTemplate = new RestTemplate();
    String uri = "http://localhost:8081/v1/uploads/images/rename";
    HttpHeaders headers = new HttpHeaders();
    //headers.setContentType(MediaType.APPLICATION_JSON);
    imageRenameDTO = restTemplate.postForObject(uri, imageRenameDTO,ImageRenameDTO.class);
    System.out.println("Result:" + imageRenameDTO.toString());
    applicationFormStudent.setPhoto(imageRenameDTO.getFilesToRename().get(applicationFormStudent.getPhoto()));
    applicationFormStudent.setPhotoUrl(imageRenameDTO.getFilesToRename().get(applicationFormStudent.getPhoto()));
    for (ApplicationFormParent applicationFormParent : applicationFormStudent.getApplicationFormParents()) {
      if(applicationFormParent.getPhoto() != null) {
        applicationFormParent.setPhoto(imageRenameDTO.getFilesToRename().get(applicationFormParent.getPhoto()));
        applicationFormParent.setPhotoUrl(imageRenameDTO.getFilesToRename().get(applicationFormParent.getPhoto()));
      }
      
    }
    
    /*
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(Include.ALWAYS, Include.NON_NULL));
    imageRenameDTO = objectMapper.readValue(result, UiMetaDataDTO.class);
    */
  }
  private void persistParenetDetails(ApplicationFormStudentDTO applicationFormDTO,
      ApplicationFormStudent applicationFormStudent) {
    /*
     * //Below one not Automatically converting... List<ApplicationFormParent>
     * applicationFormParents=applicationForm.getApplicationFormParents(); for
     * (ApplicationFormParent applicationFormParent : applicationFormParents) {
     * System.out.println("applicationFormParent:"+applicationFormParent); }
     */
    List<ApplicationFormParentDTO> applicationFormParentListDTO =
        applicationFormDTO.getApplicationFormParents();
    /*
     * List<ApplicationFormParent> applicationFormParents = new ArrayList<>();
     * modelMapper.map(applicationFormParentListDTO, applicationFormParents);//
     * p.employeEntityToDto(entity);
     * System.out.println("applicationFormParents:"+applicationFormParents);
     * 
     */
    // applicationFormParentRepo
    // applicationForm.setApplicationFormParents(applicationFormParents);

    ModelMapper modelMapper = new ModelMapper();
    List<ApplicationFormParent> applicationFormParents =
        applicationFormParentListDTO.stream().map((applicationFormParentDTO) -> {
          ApplicationFormParent t =
              modelMapper.map(applicationFormParentDTO, ApplicationFormParent.class);
          System.out.println("ID:" + t.getId());
          t.setApplicationFormStudent(applicationFormStudent);// .setId(applicationFormDTO.getId());
          if (t.getId() != null && t.getId().longValue() <= 0) {
            t.setId(null);
          }

          return t;
        }).collect(Collectors.toList());
    applicationFormParentRepo.saveAll(applicationFormParents);
    System.out.println("applicationFormParents:" + applicationFormParents);

  }

  @Override
  public List<UiSelectItemDTO> getUniqueAcademicYearsSortedByAcademicYear() {
    List<String> uniqueAcademicYearsSortedByAcademicYears =
        applicationFormRepo.getUniqueAcademicYearsSortedByAcademicYear();

    List<UiSelectItemDTO> transformedList =
        uniqueAcademicYearsSortedByAcademicYears.stream().map(a -> {
          UiSelectItemDTO v = new UiSelectItemDTO();
          v.setCode(a);
          v.setName(a);
          return v;
        }).collect(Collectors.toList());

    return transformedList;
  }

  @Override
  public UiMetaDataDTO<ApplicationFormStudentDTO> getNewApplicaitonForm() {
    UiMetaDataDTO<ApplicationFormStudentDTO> uiMetaDataDTO = null;
    try {
      ApplicationFormStudent applicationForm = new ApplicationFormStudent();
      ApplicationFormStudentDTO dto;
      // TODO: Get Current Academic Year from DB
      applicationForm.setAcademicYear("2025-26");
      applicationForm.setApplicationMode(ApplicationModeEnum.NEW);
      //Address Permanent
      ApplicationFormAddress permanantApplicationFormAddress=new ApplicationFormAddress();
      permanantApplicationFormAddress.setAddressType(AddressTypeEnum.PERMANANT);   
      //Address Communication
      ApplicationFormAddress communicationApplicationFormAddress=new ApplicationFormAddress();
      communicationApplicationFormAddress.setAddressType(AddressTypeEnum.COMMUNICATION);
      
      //Generate Application Number
      applicationForm.setFormApplicationNumber(sequenceProviderService.getNexSequenceNumber("TLA"));
      
      
      /*
      applicationForm.getApplicationFormAddresses().add(permanantApplicationFormAddress);
      applicationForm.getApplicationFormAddresses().add(communicationApplicationFormAddress);
      */
      applicationForm = applicationFormRepo.save(applicationForm);
      
      System.out.println("applicationForm:"+applicationForm.toString());
      Optional<ApplicationFormStudent> applicationFormOptt1=applicationFormRepo.findById(applicationForm.getId());
      System.out.println("applicationForm:"+applicationFormOptt1.get().toString());
      dto = convertB2A(applicationForm);

      System.out.println("Getting application form details");
      RestTemplate restTemplate = new RestTemplate();
      String uri = "http://localhost:8080/v1/metadata/page/APPLICATION_FORM";
      String result = restTemplate.getForObject(uri, String.class);
      System.out.println("Result:" + result);
      ObjectMapper objectMapper = new ObjectMapper();

      uiMetaDataDTO = objectMapper.readValue(result, UiMetaDataDTO.class);

      uiMetaDataDTO.setDto(dto);
      
      //return null;
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    System.out.println("uiMetaDataDTO:" + uiMetaDataDTO);
    return uiMetaDataDTO;

  }

 
}
