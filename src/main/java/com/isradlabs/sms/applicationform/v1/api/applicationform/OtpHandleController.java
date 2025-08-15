package com.isradlabs.sms.applicationform.v1.api.applicationform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.isradlabs.sms.applicationform.service.otp.OtpLocalHandlerServiceImpl;
import com.isradlabs.sms.model.dto.otp.OtpDTO;

@RestController
@RequestMapping("/v1/otp")

public class OtpHandleController {
  @Autowired
  OtpLocalHandlerServiceImpl otpHandleService;

  @PostMapping("/generateOtp")
  public OtpDTO generateOtp(@RequestBody OtpDTO otpDTO) {
    return otpHandleService.generateOtp(otpDTO);

  }

  @PostMapping("/validateOtp")
  public OtpDTO validateOtp(@RequestBody OtpDTO otpDTO) {
    return otpHandleService.validateOtp(otpDTO);

  }

  @PostMapping("/resendOtp")
  public OtpDTO resendOtp(@RequestBody OtpDTO otpDTO) {
    return otpHandleService.resendOtp(otpDTO);

  }
  @PostMapping("/mobileNumberChangedForOtp")
  public OtpDTO mobileNumberChangedForOtp(@RequestBody OtpDTO otpDTO) {
    return otpHandleService.mobileNumberChangedForOtp(otpDTO);

  }
  @GetMapping("/getEmptyOtpDTO")
  public OtpDTO getEmptyOtpDTO() {
    OtpDTO temp=otpHandleService.getEmptyOtpDTO();
    System.out.println(" otpHandleService.getEmptyOtpDTO():"+temp );
    return temp;

  }
}
