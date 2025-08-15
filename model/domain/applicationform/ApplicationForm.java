package com.isradlabs.sms.applicationform.model.domain.applicationform;

import java.util.Date;
import com.isradlabs.sms.applicationform.model.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_application_form")
public class ApplicationForm extends BaseDomain {



	private static final long serialVersionUID = BaseDomain.serialVersionUID;

	@Column(name = "agreement")
	private Boolean agreement;

	@Column(name = "form_application_number")
	private String formApplicationNumber;

	@Column(name = "form_application_code")
	private String formApplicationCode;

	@Column(name = "first_name_of_student")
	private String firstNameOfStudent;

	@Column(name = "middle_name_of_student")
	private String middleNameOfStudent;

	@Column(name = "last_name_of_student")
	private String lastNameOfStudent;

	@Column(name = "class_for_admission")
	private String classForAdmission;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "mother_tongue")
	private String motherTongue;

	@Column(name = "languages_known")
	private String languagesKnown;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "category")
	private String category;

	@Column(name = "community")
	private String community;

	@Column(name = "second_language_opted")
	private String secondLanguageOpted;
	
	@Column(name = "personal_identification_mark_1")
	private String personalIdentificationMark1;
	@Column(name = "personal_identification_mark_2")
	private String personalIdentificationMark2;
	@Column(name = "specially_abled")
	private String speciallyAbled;
	@Column(name = "specially_abled_detals")
	private String speciallyAbledDetals;
	@Column(name = "alergy")
	private String alergy;
	@Column(name = "alergy_details")
	private String alergyDetails;

	@Column(name = "class_last_studied")
	private String classLastStudied;
	@Column(name = "name_of_previous_school")
	private String nameOfPreviousSchool;
	@Column(name = "address_of_previous_school")
	private String addressOfPreviousSchool;
	@Column(name = "afflicated_to")
	private String afflicatedTo;
	@Column(name = "medium_of_instruction")
	private String mediumOfInstruction;

	@Column(name = "required_bus_feacility")
	private Boolean requiredBusFeacility;
	@Column(name = "place_of_boarding")
	private String placeOfBoarding;

	@Column(name = "relation")
	private String relation;
	@Column(name = "name")
	private String name;
	@Column(name = "occupation")
	private String occupation;
	@Column(name = "qualification")
	private String qualification;
	@Column(name = "organization_name")
	private String organizationName;
	@Column(name = "residential_address")
	private String residentialAddress;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "email")
	private String email;

}
