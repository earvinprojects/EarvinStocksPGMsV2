package tw.idv.earvin.stockpgms.javaswing;

public class StockData {
	/*
	 * Public Type StockData sngDate As Single sngStartprice As Single sngHighPrice
	 * As Single sngLowPrice As Single sngEndprice As Single sngVol As Single sngAcc
	 * As Single sngTome As Single End Type
	 */
	/*
	 * select_sql = "SELECT DATE, START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE,
	 * VOLUME, MARGIN_TRADING, SHORT_SELLING FROM TAIWAN_DATA_POLARIS_STOCKS WHERE
	 * STOCK_NO = %s ORDER BY DATE "
	 */

	private int date;
	private double startPrice;
	private double highPrice;
	private double lowPrice;
	private double endPrice;
	private double volume;
	private double marginTrading;
	private double shortSelling;

	public double getDate() {
		return date;
	}

	public void setDate(int v) {
		date = v;
	}

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double v) {
		startPrice = v;
	}

	public double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(double v) {
		highPrice = v;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(double v) {
		lowPrice = v;
	}

	public double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(double v) {
		endPrice = v;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double v) {
		volume = v;
	}

	public double getMarginTrading() {
		return marginTrading;
	}

	public void setMarginTrading(double v) {
		marginTrading = v;
	}

	public double getShortSelling() {
		return shortSelling;
	}

	public void setShortSelling(double v) {
		shortSelling = v;
	}

}
