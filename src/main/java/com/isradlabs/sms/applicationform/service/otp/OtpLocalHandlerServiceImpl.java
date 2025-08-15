package com.isradlabs.sms.applicationform.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isradlabs.sms.applicationform.repository.applicationform.SequenceProviderRepo;
import com.isradlabs.sms.model.constants.OtpAccessStatusEnum;
import com.isradlabs.sms.model.dto.otp.OtpDTO;


@Service
public class OtpLocalHandlerServiceImpl {
  

  private Long otpExpiresInMinute = 2L;
  private int otpAttemptAllowed = 2;

  public OtpDTO generateOtp(OtpDTO otpDTO) {

    otpDTO.setExpiresInSeconds(otpExpiresInMinute * 1000);
    otpDTO.setAuthenticationMode(OtpAccessStatusEnum.GENERATED);
    return otpDTO;
  }

  public OtpDTO validateOtp(OtpDTO otpDTO) {

    if (otpDTO != null && otpDTO.getOtp().equalsIgnoreCase("00000")) {
      otpDTO.setAuthenticationMode(OtpAccessStatusEnum.SUCCESS);
    } else {

      if (otpDTO.getOtpAttemptAllowed() == 1) {
        otpDTO.setAuthenticationMode (OtpAccessStatusEnum.OTP_ATTEMPT_REACHED);
      } else {
        otpDTO.setAuthenticationMode        (OtpAccessStatusEnum.OTP_WRONG);
        otpDTO.setOtpAttemptAllowed((otpDTO.getOtpAttemptAllowed() - 1));
      }

    }
    return otpDTO;
  }

  public OtpDTO getEmptyOtpDTO() {
    OtpDTO otpDTO = new OtpDTO();
    otpDTO.setOtp("");
    otpDTO.setAuthenticationMode(OtpAccessStatusEnum.NEW);
    otpDTO.setOtpAttemptAllowed(otpAttemptAllowed);

    return otpDTO;
  }

  public OtpDTO resendOtp(OtpDTO otpDTO) {
    generateOtp(otpDTO);
    otpDTO.setAuthenticationMode(OtpAccessStatusEnum.GENERATED);
    return otpDTO;
  }

  public OtpDTO mobileNumberChangedForOtp(OtpDTO otpDTO) {
    return getEmptyOtpDTO();
  }
}
