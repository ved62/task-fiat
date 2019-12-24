package ru.ved.taskfiat.exceptions;

import ru.ved.taskfiat.model.Quote;

public class QuoteValidationException extends RuntimeException {

	private static final long serialVersionUID = -4535239112122687320L;

	public QuoteValidationException(final Quote quote) {
		super("Quote " + quote + " is invalid!");
	}

}
