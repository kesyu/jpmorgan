package sss.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import sss.SuperSimpleStocks;
import sss.bean.Stock;
import sss.bean.TradeRecord;

public class SuperSimpleStocksImpl implements SuperSimpleStocks {

	@Override
	public double calculateDividendYield(Stock stock, double price) {
		if (stock == null
				|| stock.getLastDividend() < 0
				|| stock.getFixedDividend() < 0
				|| stock.getParValue() < 0
				|| price <= 0) {
			throw new IllegalArgumentException();
		}
		if (Stock.Type.COMMON == stock.getType()) {
			return stock.getLastDividend() / price;
		}
		if (Stock.Type.PREFERRED == stock.getType()) {
			return stock.getFixedDividend()*stock.getParValue() / price;
		}
		return -1;
	}

	@Override
	public double calculatePERatio(Stock stock, double price) {
		double dividend = this.calculateDividendYield(stock, price);
		if (dividend <= 0 || price < 0) {
			throw new IllegalArgumentException();
		}
		return price / dividend;
	}

	@Override
	public double calculateStockPrice(Stock.Symbol stockSymbol, List<TradeRecord> tradeRecords, LocalDateTime currentTime) {
		if (tradeRecords == null || tradeRecords.isEmpty() || !validTradeRecordListElements(tradeRecords)) {
			throw new IllegalArgumentException();
		}

		List<TradeRecord> filteredList = tradeRecords.stream()
				.filter(e -> Math.abs(ChronoUnit.MINUTES.between(e.getTimeStamp(), currentTime)) < 15)
				.filter(e -> stockSymbol.equals(e.getStockSymbol()))
				.collect(Collectors.toList());

		double numerator = filteredList.stream().mapToDouble(e -> e.getPrice()*e.getQuantityOfShares()).sum();
		double denominator = filteredList.stream().mapToDouble(e -> e.getQuantityOfShares()).sum();
		return numerator / denominator;
	}

	@Override
	public double calculateGBCEAllShareIndex(List<Stock> stocks, List<TradeRecord> tradeRecords) {
		if (stocks.isEmpty()) {
			throw new IllegalArgumentException();
		}
		double root = 1.0/stocks.size();
		double result = 1;
		for (Stock stock : stocks) {
			result *= this.calculateStockPrice(stock.getSymbol(), tradeRecords, LocalDateTime.now());
		}
		return Math.pow(result, root);
	}

	private boolean validTradeRecordListElements(List<TradeRecord> tradeRecords) {
		List<TradeRecord> list = tradeRecords.stream()
				.filter(e -> (e.getTimeStamp() == null
					|| e.getStockSymbol() == null
					|| e.getPrice() == 0
					|| e.getQuantityOfShares() == 0))
				.collect(Collectors.toList());
		return list.isEmpty();
	}
}
