package com.erajayaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Api exception handler.
 */
@ControllerAdvice
public class ApiExceptionHandler {

   /**
    * Handle api request exception response entity.
    *
    * @param e the e
    * @return the response entity
    */
   @ExceptionHandler(value = {ApiRequestException.class})
   public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
      //Payload
      HttpStatus notFound = HttpStatus.NOT_FOUND;

      ApiException apiException = new ApiException(e.getMessage(),
              notFound,
              notFound.value(),
              ZonedDateTime.now(ZoneId.of("Z")));

      //Return Response Entity
      return new ResponseEntity<>(apiException, notFound);
   }
}
