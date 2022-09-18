package com.shervilg.spinboard.dto.request;

import lombok.Data;

@Data
public class EmailRequest {
  private String to;
  private String from;
  private String subject;
  private String contentType = "text/plain";
  private String content;
}
