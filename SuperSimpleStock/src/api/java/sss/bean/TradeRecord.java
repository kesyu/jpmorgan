package sss.bean;

import java.time.LocalDateTime;

import sss.bean.Stock.Symbol;
import sss.service.TradeService;
import sss.service.TradeService.TradeType;

public class TradeRecord {
	private TradeService.TradeType tradeType;
	private Stock.Symbol stockSymbol;
	private int quantityOfShares;
	private int price;
	private LocalDateTime timeStamp;
	
	public TradeRecord() {}
	
	public TradeRecord(TradeType tradeType, Symbol stockSymbol, int quantityOfShares, int price,
			LocalDateTime timeStamp) {
		this.tradeType = tradeType;
		this.stockSymbol = stockSymbol;
		this.quantityOfShares = quantityOfShares;
		this.price = price;
		this.timeStamp = timeStamp;
	}

	public TradeService.TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeService.TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public Stock.Symbol getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(Stock.Symbol stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public int getQuantityOfShares() {
		return quantityOfShares;
	}

	public void setQuantityOfShares(int quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradeRecord [tradeType=").append(tradeType).append(", stockSymbol=").append(stockSymbol)
				.append(", quantityOfShares=").append(quantityOfShares).append(", price=").append(price)
				.append(", timeStamp=").append(timeStamp).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + price;
		result = prime * result + quantityOfShares;
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((tradeType == null) ? 0 : tradeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeRecord other = (TradeRecord) obj;
		if (price != other.price)
			return false;
		if (quantityOfShares != other.quantityOfShares)
			return false;
		if (stockSymbol != other.stockSymbol)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (tradeType != other.tradeType)
			return false;
		return true;
	}
	
}
