package ru.ved.taskfiat.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.ved.taskfiat.model.Quote;

@Component
public class DaoImpl implements Dao {

	private static final Logger LOG = LoggerFactory.getLogger(DaoImpl.class);

	private final JdbcTemplate jdbcTemplate;

	public DaoImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public int insert(final Quote quote) {
		final String sql = "INSERT INTO quotes (isin, bid, ask, bidSize, askSize, elvl) " + "VALUES(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, quote.getIsin(), quote.getBid(), quote.getAsk(), quote.getBidSize(),
				quote.getAskSize(), quote.getElvl());
	}

	@Override
	@Transactional
	public int update(final Quote quote) {
		final String sql = "UPDATE quotes SET elvl = ? WHERE id = ?";
		return jdbcTemplate.update(sql, quote.getElvl(), quote.getId());
	}

	@Override
	public List<Quote> getAll() {
		final String query = "SELECT * FROM quotes";
		return jdbcTemplate.query(query, new QuoteMapper());
	}

	@Override
	public Optional<Quote> getByIsin(final String isin) {
		final String query = "SELECT * FROM quotes WHERE isin=?";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, new Object[] { isin }, new QuoteMapper()));
		} catch (final EmptyResultDataAccessException ex) {
			LOG.info("Unknown isin: {} requested", isin);
			return Optional.empty();
		}
	}

	@Override
	public void executeDdl(final String ddl) {
		jdbcTemplate.execute(ddl);
	}

}
