package tw.idv.earvin.stockpgms.stocks_modules.tables;

import java.math.BigDecimal;

public class TaiwanDataPolaris {
	private String date;
	private String stockNo;
	private String stockName;
	private BigDecimal startPrice;
	private BigDecimal highPrice;
	private BigDecimal lowPrice;
	private BigDecimal endPrice;
	private String upDown;
	private BigDecimal volume;
	private BigDecimal highestPrice;
	private BigDecimal lowestPrice;

	public String getDate() {
		return date;
	}

	public void setDate(String v) {
		date = v;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String v) {
		stockNo = v;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String v) {
		stockName = v;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(String v) {
		startPrice = new BigDecimal(v);
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String v) {
		highPrice = new BigDecimal(v);
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String v) {
		lowPrice = new BigDecimal(v);
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String v) {
		endPrice = new BigDecimal(v);
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String v) {
		upDown = v;
	}

	public BigDecimal getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(String v) {
		highestPrice = new BigDecimal(v);
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(String v) {
		lowestPrice = new BigDecimal(v);
	}
}
