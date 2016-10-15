package sss.bean;

public class Stock {
	private Symbol symbol;
	private Type type;
	private int lastDividend;
	private float fixedDividend;
	private int parValue;
	
	public static enum Symbol {
		TEA, POP, ALE, GIN, JOE
	}
	
	public static enum Type {
		COMMON, PREFERRED
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(int lastDividend) {
		this.lastDividend = lastDividend;
	}

	public float getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(float fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public int getParValue() {
		return parValue;
	}

	public void setParValue(int parValue) {
		this.parValue = parValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stock [symbol=").append(symbol).append(", type=").append(type).append(", lastDividend=")
				.append(lastDividend).append(", fixedDividend=").append(fixedDividend).append(", parValue=")
				.append(parValue).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(fixedDividend);
		result = prime * result + lastDividend;
		result = prime * result + parValue;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Stock other = (Stock) obj;
		if (Float.floatToIntBits(fixedDividend) != Float.floatToIntBits(other.fixedDividend))
			return false;
		if (lastDividend != other.lastDividend)
			return false;
		if (parValue != other.parValue)
			return false;
		if (symbol != other.symbol)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
