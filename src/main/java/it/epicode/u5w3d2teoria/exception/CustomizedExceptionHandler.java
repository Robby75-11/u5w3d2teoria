package it.epicode.u5w3d2teoria.exception;

import it.epicode.u5w3d2teoria.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice //è un controller che gestisce gli errori
public class CustomizedExceptionHandler {

    @ExceptionHandler(NotFoundException.class)//serve per mappare il metodo che gestisce questa eccezione
    //quindi quando in Spring si genera una eccezione di tipo NotFoundException, Spring
    //passerà l'onere della risposta al client a questo metodo
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandler(NotFoundException e){
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
    @ExceptionHandler(ValidationException.class)//serve per mappare il metodo che gestisce questa eccezione
    //quindi quando in Spring si genera una eccezione di tipo NotFoundException, Spring
    //passerà l'onere della risposta al client a questo metodo
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationExceptionHandler(ValidationException e){
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
}