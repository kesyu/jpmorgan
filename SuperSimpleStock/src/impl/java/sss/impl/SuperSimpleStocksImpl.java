package sss.impl;

import sss.SuperSimpleStocks;
import sss.bean.Stock;

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
	public double calculateStockPriceFromRecordedTrades() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calculateGBCEAllShareIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
