package ru.ved.taskfiat.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Quote implements Serializable {

	private static final long serialVersionUID = 7741302477754775284L;

	private Long id;

	private String isin;

	private BigDecimal bid;

	private BigDecimal ask;

	private Integer bidSize;

	private Integer askSize;

	private BigDecimal elvl;

	public Quote(final String isin, final BigDecimal bid, final BigDecimal ask, final Integer bidSize,
			final Integer askSize, final BigDecimal elvl) {
		this.isin = isin;
		this.bid = bid;
		this.ask = ask;
		this.bidSize = bidSize;
		this.askSize = askSize;
		this.elvl = elvl;
	}

	// just to simplify test data loading
	public Quote(final String isin, final double bid, final double ask, final int bidSize, final int askSize,
			final double elvl) {
		this.isin = isin;
		this.bid = BigDecimal.valueOf(bid);
		this.ask = BigDecimal.valueOf(ask);
		this.bidSize = bidSize;
		this.askSize = askSize;
		this.elvl = BigDecimal.valueOf(elvl);
	}
	
	// just for tests
	public Quote(final String isin, final double bid, final double ask, final int bidSize, final int askSize) {
		this.isin = isin;
		this.bid = BigDecimal.valueOf(bid);
		this.ask = BigDecimal.valueOf(ask);
		this.bidSize = bidSize;
		this.askSize = askSize;
	}

	public Quote() {
		// default constructor
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setIsin(final String isin) {
		this.isin = isin;
	}

	public String getIsin() {
		return isin;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(final BigDecimal bid) {
		this.bid = bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(final BigDecimal ask) {
		this.ask = ask;
	}

	public Integer getBidSize() {
		return bidSize;
	}

	public void setBidSize(final Integer bidSize) {
		this.bidSize = bidSize;
	}

	public Integer getAskSize() {
		return askSize;
	}

	public void setAskSize(final Integer askSize) {
		this.askSize = askSize;
	}

	public BigDecimal getElvl() {
		return elvl;
	}

	public void setElvl(final BigDecimal elvl) {
		this.elvl = elvl;
	}

	@Override
	public String toString() {
		return "Quote [id=" + id + ", isin=" + isin + ", bid=" + bid + ", ask=" + ask + ", bidSize=" + bidSize
				+ ", askSize=" + askSize + ", elvl=" + elvl + "]";
	}

}
