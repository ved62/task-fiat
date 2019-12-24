package ru.ved.taskfiat.dao;

import java.util.List;
import java.util.Optional;

import ru.ved.taskfiat.model.Quote;

public interface Dao {

	int insert(Quote quote);

	int update(Quote quote);

	List<Quote> getAll();

	Optional<Quote> getByIsin(String isin);

	void executeDdl(String ddl);

}
