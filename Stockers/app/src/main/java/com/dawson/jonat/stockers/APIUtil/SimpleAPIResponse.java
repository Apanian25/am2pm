package com.dawson.jonat.stockers.APIUtil;

public class SimpleAPIResponse {

    private int statusCode;
    private String messageBody;

    public SimpleAPIResponse(int statusCode, String messageBody){
        this.statusCode = statusCode;
        this.messageBody = messageBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
