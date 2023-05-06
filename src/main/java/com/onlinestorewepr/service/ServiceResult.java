package com.onlinestorewepr.service;

public class ServiceResult {
  private String message;
  private String messageType;

  public ServiceResult() {}

  public ServiceResult(String message, String messageType) {
    this.message = message;
    this.messageType = messageType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }
}
