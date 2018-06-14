package com.blockchain.server.exceptions;

import com.blockchain.client.exception.BlockChainClientException;
import com.blockchain.server.exceptions.model.MessageError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BlockChainClientException.class)
    protected ResponseEntity<Object> handleServerError(BlockChainClientException ex, WebRequest request) {
        MessageError error = new MessageError(ex.getErrorMessage(), ex.getUrl(), ex.getHttpStatus());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
}
