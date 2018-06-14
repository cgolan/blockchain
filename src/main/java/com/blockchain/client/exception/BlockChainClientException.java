package com.blockchain.client.exception;

import org.springframework.http.HttpStatus;

public class BlockChainClientException extends RuntimeException {

    private String errorMessage;
    private String url;
    private HttpStatus httpStatus;

    public BlockChainClientException(String message, String url) {
        this.errorMessage = message;
        this.url = url;
    }

    public BlockChainClientException(String message, String url, HttpStatus httpStatus) {
        this.errorMessage = message;
        this.url = url;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
