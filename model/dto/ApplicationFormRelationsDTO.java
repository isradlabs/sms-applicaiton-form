package com.isradlabs.sms.applicationform.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class ApplicationFormRelationsDTO extends BaseDTO {

  private Boolean agreement = false;

  private String formApplicationNumber;

  private String formApplicationCode;

  private String firstNameOfStudent;

  private String middleNameOfStudent;

  private String lastNameOfStudent;

  private String classForAdmission;

  @JsonFormat(pattern = "MM/dd/yyyy")
  private Date dateOfBirth;

  private String gender;

  private String bloodGroup;

  private String motherTongue;

  private String languagesKnown;

  private String aadharNumber;

  private String nationality;

  private String category;

  private String community;

  private String secondLanguageOpted;

  private String personalIdentificationMark1;

  private String personalIdentificationMark2;

  private String speciallyAbled;

  private String speciallyAbledDetals;

  private String alergy;

  private String alergyDetails;

  private String classLastStudied;

  private String nameOfPreviousSchool;

  private String addressOfPreviousSchool;

  private String afflicatedTo;

  private String mediumOfInstruction;

  private Boolean requiredBusFeacility = false;

  private String placeOfBoarding;

}
