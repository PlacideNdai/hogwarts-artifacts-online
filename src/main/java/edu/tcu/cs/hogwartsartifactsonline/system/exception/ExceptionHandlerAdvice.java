package edu.tcu.cs.hogwartsartifactsonline.system.exception;

//import edu.tcu.cs.hogwartsartifactsonline.artifact.ArtifactNotFoundException;
import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ObjectNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    Result HandleObjectNotFoundException(ObjectNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    /**
     * This handles invalid inputs.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return new Result(false, StatusCode.BAD_REQUEST, "Provided arguments are invalid, see data for details.", map);
    }

    @ExceptionHandler({AccountStatusException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAccountStatusException(AccountStatusException ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "User account is abnormal", ex.getMessage());
    }


    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAuthenticationNotFoundException(Exception ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "username or password is incorrect", ex.getMessage());
    }


    @ExceptionHandler({InvalidBearerTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "Access token provided is expired, revoked, malform, or invalid for other reasons. ", ex.getMessage());
    }


    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result handleAccessDefinedException(AccessDeniedException ex) {
        return new Result(false, StatusCode.FORBIDDEN, "No permission", ex.getMessage());
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Result handleOtherException(Exception ex) {
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
    }



}
