package com.isradlabs.sms.applicationform.model.dto;

import java.util.Date;
import lombok.Data;

@Data
public class BaseDTO {
  protected Long id;

  private Date createdDate;


  private Date updatedDate;


  private Date createdBy;


  private Date updatedBy;

  private Boolean softDelete = false;

  private Date effectiveDate;
}
