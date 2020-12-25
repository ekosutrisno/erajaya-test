package com.erajayaapi.exception;

/**
 * The type Api request exception.
 */
public class ApiRequestException extends RuntimeException {

   /**
    * Instantiates a new Api request exception.
    *
    * @param message the message
    */
   public ApiRequestException(String message) {
      super(message);
   }

   /**
    * Instantiates a new Api request exception.
    *
    * @param message the message
    * @param cause   the cause
    */
   public ApiRequestException(String message, Throwable cause) {
      super(message, cause);
   }
}
