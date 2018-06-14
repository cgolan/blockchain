package com.blockchain.server.exceptions.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class MessageError {

    private String message;
    private String url;
    private HttpStatus httpStatus;

    public MessageError(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public MessageError(String message, String url, HttpStatus httpStatus) {
        this.message = message;
        this.url = url;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
