package ru.ved.taskfiat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.ved.taskfiat.exceptions.QuoteValidationException;

@ControllerAdvice
public class QuoteValidationAdvice {

	@ResponseBody
	@ExceptionHandler(QuoteValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String quoteNotFoundHandler(final QuoteValidationException ex) {
		return ex.getMessage();
	}

}
