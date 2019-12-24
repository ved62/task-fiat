package ru.ved.taskfiat.processing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.ved.taskfiat.dao.Dao;
import ru.ved.taskfiat.exceptions.QuoteValidationException;
import ru.ved.taskfiat.model.Quote;

@Component
public class QuoteProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(QuoteProcessor.class);

	private final Dao dao;
	private final Executor executor;

	public QuoteProcessor(final Dao dao) {
		this.dao = dao;
		executor = Executors.newSingleThreadExecutor();
	}

	public List<Quote> getAll() {
		return dao.getAll();
	}

	public Quote getByIsin(final String isin) {
		if (!isValidIsin(isin)) {
			return null;
		}
		final Optional<Quote> optQuote = dao.getByIsin(isin);
		if (optQuote.isPresent()) {
			return optQuote.get();
		}
		return null;
	}

	public Quote saveOrUpdate(final Quote quote) {
		if (isValid(quote)) {
			final Optional<Quote> optQuote = dao.getByIsin(quote.getIsin());
			if (optQuote.isPresent()) {
				final Quote oldQuote = optQuote.get();
				if (updateQuote(oldQuote, quote)) {
					executor.execute(() -> dao.update(oldQuote));
					return oldQuote;
				}
			} else {
				return saveQuote(quote);
			}
		} else {
			throw new QuoteValidationException(quote);
		}
		return null;
	}

	private static boolean updateQuote(final Quote oldQuote, final Quote newQuote) {
		final BigDecimal newBid = newQuote.getBid();
		final BigDecimal newAsk = newQuote.getAsk();
		final BigDecimal oldElvl = newQuote.getElvl();
		if (oldElvl == null) {
			oldQuote.setElvl(newBid != null ? newBid : newAsk);
			return true;
		}
		if ((newBid != null) && (oldElvl.compareTo(newBid) < 0)) {
			oldQuote.setElvl(newBid);
			return true;
		}
		if ((newBid == null) && (oldElvl.compareTo(newAsk) > 0)) {
			oldQuote.setElvl(newAsk);
			return true;
		}
		return false;
	}

	private Quote saveQuote(final Quote newQuote) {
		if (newQuote.getBid() != null) {
			newQuote.setElvl(newQuote.getBid());
		} else {
			newQuote.setElvl(newQuote.getAsk());
		}
		executor.execute(() -> dao.insert(newQuote));
		return newQuote;
	}

	private static boolean isValid(final Quote quote) {
		final String isin = quote.getIsin();
		if (!isValidIsin(isin)) {
			return false;
		}
		if ((quote.getBid() == null) && (quote.getAsk() == null)) {
			LOG.error("both Bid and Ask are NULL");
			return false;
		}
		if (quote.getBid().compareTo(quote.getAsk()) < 0) {
			return true;
		}
		LOG.error("Bid greater then Ask");
		return false;
	}

	private static boolean isValidIsin(final String isin) {
		if ((isin == null) || (isin.length() != 12)) {
			LOG.error("isin is NULL or has wrong length");
			return false;
		}
		return true;
	}

}
