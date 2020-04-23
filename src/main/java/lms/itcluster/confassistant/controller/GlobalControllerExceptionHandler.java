package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.exception.ForbiddenAccessException;
import lms.itcluster.confassistant.exception.NoSuchEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleNullPointerExceptions(RuntimeException ex, Model model, HttpServletResponse response) {
        model.addAttribute("message", "Oops, something goes wrong. Please try again later");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(ex.getMessage(), ex);
        return "message";
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public String handleNullPointerExceptions(NoSuchEntityException ex, Model model, HttpServletResponse response) {
        model.addAttribute("message", "Not found");
        response.setStatus(HttpStatus.NOT_FOUND.value());
        log.error(ex.getMessage(), ex);
        return "message";
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public String handleNullPointerExceptions(ForbiddenAccessException ex, Model model, HttpServletResponse response) {
        model.addAttribute("message", "Forbidden");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        log.error(ex.getMessage(), ex);
        return "message";
    }


}
