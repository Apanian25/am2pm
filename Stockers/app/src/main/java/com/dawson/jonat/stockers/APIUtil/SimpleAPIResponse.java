package com.dawson.jonat.stockers.APIUtil;

/**
 * Object holder for an HttpResponse
 *
 * @author Danny
 */
public class SimpleAPIResponse {

    private int statusCode;
    private String messageBody;

    /**
     * Instantiates the instance variables
     *
     * @param statusCode
     * @param messageBody
     */
    public SimpleAPIResponse(int statusCode, String messageBody){
        this.statusCode = statusCode;
        this.messageBody = messageBody;
    }

    /**
     *
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     *
     *
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return messageBody
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     *
     * @param messageBody
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
