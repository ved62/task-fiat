package ru.ved.taskfiat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.ved.taskfiat.exceptions.QuoteNotFoundException;

@ControllerAdvice
public class QuoteNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(QuoteNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String quoteNotFoundHandler(final QuoteNotFoundException ex) {
		return ex.getMessage();
	}
}
