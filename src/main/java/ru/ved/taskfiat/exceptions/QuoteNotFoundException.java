package ru.ved.taskfiat.exceptions;

public class QuoteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4505239350606483464L;

	public QuoteNotFoundException(final String isin) {
		super("Could not find quote " + isin);
	}

}
