package com.shervilg.spinboard.helper;

import com.shervilg.spinboard.dto.request.EmailRequest;

public interface EmailHelper {
  void sendEmail(EmailRequest emailRequest);
}
