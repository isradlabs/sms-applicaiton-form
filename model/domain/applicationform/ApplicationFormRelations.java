package com.isradlabs.sms.applicationform.model.domain.applicationform;

import com.isradlabs.sms.applicationform.model.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_application_form_relations")
public class ApplicationFormRelations extends BaseDomain {

  private static final long serialVersionUID = BaseDomain.serialVersionUID;


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

  @Column(name = "mobile_number_1")
  private String mobileNumber1;

  @Column(name = "mobile_number_2")
  private String mobileNumber2;

  @Column(name = "email")
  private String email;

}
