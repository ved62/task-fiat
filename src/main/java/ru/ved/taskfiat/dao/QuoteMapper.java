package ru.ved.taskfiat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.ved.taskfiat.model.Quote;

public class QuoteMapper implements RowMapper<Quote> {

	@Override
	public Quote mapRow(final ResultSet rs, final int rowNum) throws SQLException {

		final Quote quote = new Quote();

		quote.setId(rs.getLong("id"));
		quote.setAsk(rs.getBigDecimal("ask"));
		quote.setAskSize(rs.getInt("askSize"));
		quote.setBid(rs.getBigDecimal("bid"));
		quote.setBidSize(rs.getInt("bidSize"));
		quote.setElvl(rs.getBigDecimal("elvl"));
		quote.setIsin(rs.getString("isin"));
		return quote;
	}

}
