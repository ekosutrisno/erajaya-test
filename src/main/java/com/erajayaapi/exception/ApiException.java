package com.erajayaapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * The type Api exception.
 */
@Data
public class ApiException {
   private final String message;
   private final HttpStatus httpStatus;
   private final Integer statusCode;
   private final ZonedDateTime timeStamp;
}
