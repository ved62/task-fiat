package ru.ved.taskfiat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.ved.taskfiat.exceptions.QuoteNotFoundException;
import ru.ved.taskfiat.model.Quote;
import ru.ved.taskfiat.processing.QuoteProcessor;

@RestController
@RequestMapping("/quotes")
class QuoteController {

	private final QuoteProcessor processor;

	public QuoteController(final QuoteProcessor processor) {
		this.processor = processor;
	}

	@GetMapping
	public List<Quote> all() {
		return processor.getAll();
	}

	@GetMapping(value = "/{isin}")
	public Quote single(@PathVariable final String isin) {
		final Quote quote = processor.getByIsin(isin);
		if (quote == null) {
			throw new QuoteNotFoundException(isin);
		}
		return quote;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Quote saveOrUpdate(@RequestBody final Quote quote) {
		return processor.saveOrUpdate(quote);
	}

}
