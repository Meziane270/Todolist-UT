package com.todolist.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;

@ControllerAdvice(basePackages = {"com.todolist"})
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    }

    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("technicalError", "Une erreur technique est survenue !");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unknownError(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidObjectException.class)
    public ResponseEntity<Void> invalidObjectError(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
