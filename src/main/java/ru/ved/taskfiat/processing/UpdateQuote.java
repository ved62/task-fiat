package ru.ved.taskfiat.processing;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ved.taskfiat.dao.Dao;
import ru.ved.taskfiat.model.Quote;

public class UpdateQuote implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(UpdateQuote.class);
	
	private final Quote oldQuote;
	
	private final Quote newQuote;
	
	private final Dao dao;
	
	public UpdateQuote(Quote oldQuote, Quote newQuote, Dao dao) {
		this.oldQuote = oldQuote;
		this.newQuote = newQuote;
		this.dao = dao;
	}

	@Override
	public void run() {
		boolean updated = false;
		final BigDecimal newBid = newQuote.getBid();
		final BigDecimal newAsk = newQuote.getAsk();
		final BigDecimal oldElvl = newQuote.getElvl();
		if (oldElvl == null) {
			oldQuote.setElvl(newBid != null ? newBid : newAsk);
			updated = true;
		} else {
			if ((newBid != null) && (oldElvl.compareTo(newBid) < 0)) {
				oldQuote.setElvl(newBid);
				updated = true;
			} else {
				if ((newBid == null) && (oldElvl.compareTo(newAsk) > 0)) {
					oldQuote.setElvl(newAsk);
					updated = true;
				}
			}
		}
		if (updated && (dao.update(oldQuote) != 1)) {
				LOG.error("record {} update failed", oldQuote);
		}
	}

}
