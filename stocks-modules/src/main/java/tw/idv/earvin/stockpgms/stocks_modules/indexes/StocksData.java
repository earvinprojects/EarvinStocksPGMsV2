package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Vector;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StocksData {
	private long date;
	private String stockNo;
	private double startPrice;
	private double highPrice;
	private double lowPrice;
	private double endPrice;
	private double volume;
	private double marginTrading; // 融資張數
	private double shortSelling; // 融券張數
	private double advanceDeclineLine; // 騰落指標
	private double upDownFirms; // 漲跌家數
	private double foreignStock; // 外資庫存
	private double sitAndCbStock; // 投信庫存
	private double selfEmployedStock; // 自營商庫存
	private double legalPersonStock; // 法人庫存
	private double openInterestStock; // 未平倉量

	private static String SQL_SELECT_TAIWAN_DATA_POLARIS_BY_STOCK_NO = "SELECT DATE, START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE, VOLUME FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY DATE ";
	// private static String
	// SQL_SELECT_TAIWAN_DATA_POLARIS_BY_STOCK_NO_AND_TWO_DATES = "SELECT DATE,
	// START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE, VOLUME FROM
	// TAIWAN_DATA_POLARIS WHERE DATE BETWEEN ? AND ? AND STOCK_NO = ? ORDER BY DATE
	// ";
	private static String SQL_SELECT_TAIWAN_DATA_POLARIS_BY_STOCK_NO_AND_TWO_DATES = "SELECT A.DATE, A.STOCK_NO, A.STOCK_NAME, A.START_PRICE, A.HIGH_PRICE, A.LOW_PRICE, A.END_PRICE, A.VOLUME, "
			+ "B.SHORT_SELLING, B.ADVANCE_DECLINE_LINE, B.UP_DOWN_FIRMS, B.FOREIGN_STOCK, B.SIT_AND_CB_STOCK, B.SELF_EMPLOYED_STOCK, B.LEGAL_PERSON_STOCK, B.OPEN_INTEREST_STOCK "
			+ "FROM TAIWAN_DATA_POLARIS A " + "LEFT OUTER JOIN TAIWAN_DATA_POLARIS_STOCKS B "
			+ "ON A.DATE = B.DATE AND A.STOCK_NO = B.STOCK_NO "
			+ "WHERE A.DATE BETWEEN ? AND ? AND A.STOCK_NO = ? ORDER BY A.DATE ";

	public long getDate() {
		return date;
	}

	public void setDate(long v) {
		date = v;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String v) {
		stockNo = v;
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

	public double getAdvanceDeclineLine() {
		return advanceDeclineLine;
	}

	public void setAdvanceDeclineLine(double v) {
		advanceDeclineLine = v;
	}

	public double getUpDownFirms() {
		return upDownFirms;
	}

	public void setUpDownFirms(double v) {
		upDownFirms = v;
	}

	public double getForeignStock() {
		return foreignStock;
	}

	public void setForeignStock(double v) {
		foreignStock = v;
	}

	public double getSitAndCbStock() {
		return sitAndCbStock;
	}

	public void setSitAndCbStock(double v) {
		sitAndCbStock = v;
	}

	public double getSelfEmployedStock() {
		return selfEmployedStock;
	}

	public void setSelfEmployedStock(double v) {
		selfEmployedStock = v;
	}

	public double getLegalPersonStock() {
		return legalPersonStock;
	}

	public void setLegalPersonStock(double v) {
		legalPersonStock = v;
	}

	public double getOpenInterestStock() {
		return openInterestStock;
	}

	public void setOpenInterestStock(double v) {
		openInterestStock = v;
	}

	/**
	 * -- NOT FINISH -- 以股票代號取得指定的股票資料(資料來源：TAIWAN_DATA_POLARIS)
	 * 
	 * @param stockNo
	 *            股票代號
	 * @return 查詢的股票資料
	 */
	public static Vector getStocksDataByStockNo(String stockNo) {
		Vector sd = new Vector();

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_TAIWAN_DATA_POLARIS_BY_STOCK_NO);
			pstmt.setString(1, stockNo);
			ResultSet rs = pstmt.executeQuery();

			int i = 1;
			while (rs.next()) {
				System.out.println((i++) + "\t" + rs.getString(1) + "\t" + rs.getString(2));
			}
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sd;
	}

	/**
	 * 以日期區間+股票代號取得指定的股票資料(資料來源：TAIWAN_DATA_POLARIS)
	 * 
	 * @param startDate
	 *            開始日期
	 * @param endDate
	 *            結束日期
	 * @param stockNo
	 *            股票代號
	 * @return 查詢的股票資料
	 */
	public static StocksData[] getStocksDataByStockNoAndDateBetween(long startDate, long endDate, String stockNo) {
		StocksData[] stocksData = null;
		try {
			Vector<StocksData> vec = new Vector<StocksData>();

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_TAIWAN_DATA_POLARIS_BY_STOCK_NO_AND_TWO_DATES);
			pstmt.setLong(1, startDate);
			pstmt.setLong(2, endDate);
			pstmt.setString(3, stockNo);
			ResultSet rs = pstmt.executeQuery();

			int i = 1;
			while (rs.next()) {
				StocksData sd = new StocksData();
				sd.setDate(rs.getLong("DATE"));
				sd.setStockNo(rs.getString("STOCK_NO"));
				sd.setStartPrice(rs.getDouble("START_PRICE"));
				sd.setHighPrice(rs.getDouble("HIGH_PRICE"));
				sd.setLowPrice(rs.getDouble("LOW_PRICE"));
				sd.setEndPrice(rs.getDouble("END_PRICE"));
				sd.setVolume(rs.getDouble("VOLUME"));
				sd.setShortSelling(rs.getDouble("SHORT_SELLING"));
				sd.setAdvanceDeclineLine(rs.getDouble("ADVANCE_DECLINE_LINE"));
				sd.setUpDownFirms(rs.getDouble("UP_DOWN_FIRMS"));
				sd.setForeignStock(rs.getDouble("FOREIGN_STOCK"));
				sd.setSitAndCbStock(rs.getDouble("SIT_AND_CB_STOCK"));
				sd.setSelfEmployedStock(rs.getDouble("SELF_EMPLOYED_STOCK"));
				sd.setLegalPersonStock(rs.getDouble("LEGAL_PERSON_STOCK"));
				sd.setOpenInterestStock(rs.getDouble("OPEN_INTEREST_STOCK"));

				vec.add(sd);
				// System.out.println((i++) + "\t" + rs.getString(1) + "\t" + rs.getString(2));
				// System.out.println((i++) + "\t" + sd.printData());
			}
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
			stocksData = vec.toArray(new StocksData[vec.size()]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stocksData;
	}

	/**
	 * 列印資料
	 * 
	 * @return 欲列印的字串
	 */
	public String printData() {
		String theString = "";
		theString = "Date= " + getDate() + ", StockNo= " + getStockNo() + ", StartPrice= " + getStartPrice()
				+ ", HighPrice= " + getHighPrice() + ", LowPrice= " + getLowPrice() + ", EndPrice= " + getEndPrice()
				+ ", Volume= " + getVolume() + ", ShortSelling = " + getShortSelling() + ", AdvanceDeclineLine= "
				+ getAdvanceDeclineLine() + ", UpDownFirms= " + getUpDownFirms() + ", ForeignStock= "
				+ getForeignStock() + ", SitAndCbStock= " + getSitAndCbStock() + ", SelfEmployedStock= "
				+ getSelfEmployedStock() + ", LegalPersonStock= " + getLegalPersonStock() + ", OpenInterestStock= "
				+ getOpenInterestStock();
		return theString;
	}

	public static void main(String[] args) {
		// getStocksDataByStockNo("2002");
		StocksData[] sd = getStocksDataByStockNoAndDateBetween(950201, 951231, "2002");
		System.out.println("the stocks count= " + sd.length);
		for (int i = 0; i < sd.length; i++) {
			System.out.println(sd[i].printData());
		}
	}
}
