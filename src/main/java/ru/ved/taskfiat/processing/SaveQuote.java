package ru.ved.taskfiat.processing;

import ru.ved.taskfiat.dao.Dao;
import ru.ved.taskfiat.model.Quote;

public class SaveQuote implements Runnable {
	
	private final Quote newQuote;
	
	private final Dao dao;
	
	public SaveQuote(Quote newQuote, Dao dao) {
		this.newQuote = newQuote;
		this.dao = dao;
	}

	@Override
	public void run() {
		if (newQuote.getBid() != null) {
			newQuote.setElvl(newQuote.getBid());
		} else {
			newQuote.setElvl(newQuote.getAsk());
		}
		dao.insert(newQuote);
	}

}
