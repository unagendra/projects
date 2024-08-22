package com.coddingshuttle.SecurityApp.SecuityApplication.advice;


import com.coddingshuttle.SecurityApp.SecuityApplication.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * AuthenticationException contains several exceptions, Use HttpStatus 401 –
     * UnAuthorized to handle the response:
     *You can also handle individual exceptions
     * • AccountExpiredException
     * • BadCredentialsException
     * • CredentialsExpiredException
     * • AuthenticationCredentialsNotFoundException
     * • SessionAuthenticationException
     *
     * @param exception
     * @return
     */

    @ExceptionHandler(AuthenticationException.class) //parent
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception){
        ApiError apiError=new ApiError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED); //401
        return  new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    /**
     * JwtException contains several exceptions, Use HttpStatus 401 –
     * UnAuthorized to handle the response:
     *  ExpiredJwtException
     * • MalformedJwtException
     * • SignatureException
     * • UnsupportedJwtException
     * • IllegalArgumentException
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex) {
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Use Status 403-FORBIDDEN For Authorization Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

}
