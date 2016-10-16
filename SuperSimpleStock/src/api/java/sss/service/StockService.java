package sss.service;

import java.util.LinkedList;
import java.util.List;

import sss.bean.Stock;

public class StockService {
	private List<Stock> stockList;

	public StockService () {
		stockList = new LinkedList<Stock>();
		
		Stock teaStock = new Stock(Stock.Symbol.TEA, Stock.Type.COMMON, 0, 0, 100);
		Stock popStock = new Stock(Stock.Symbol.POP, Stock.Type.COMMON, 8, 0, 100);
		Stock aleStock = new Stock(Stock.Symbol.ALE, Stock.Type.COMMON, 23, 0, 60);
		Stock ginStock = new Stock(Stock.Symbol.GIN, Stock.Type.PREFERRED, 8, 0.02f, 100);
		Stock joeStock = new Stock(Stock.Symbol.JOE, Stock.Type.COMMON, 13, 0, 250);
		
		stockList.add(teaStock);
		stockList.add(popStock);
		stockList.add(aleStock);
		stockList.add(ginStock);
		stockList.add(joeStock);
	}

	public List<Stock> getStocks() {
		return stockList;
	}

	public Stock getStockBySymbol(Stock.Symbol stockSymbol) {
		return this.getStocks().stream().filter(e -> stockSymbol.equals(e.getSymbol())).findFirst().get();
	}
}
