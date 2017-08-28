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
	private long tradeDate;
	private String stockNo;
	private double startPrice;
	private double highPrice;
	private double lowPrice;
	private double endPrice;
	private double volume;
	
	private static String SQL_SELECT_TAIWAN_DATA_POLARIS = "SELECT DATE, START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE, VOLUME FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY DATE ";
	
	public long getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(long v) {
		tradeDate = v;
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
	
	public static Vector getStocksData(String stockNo) {
		Vector sd = new Vector();

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(SQL_SELECT_TAIWAN_DATA_POLARIS);
			PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_TAIWAN_DATA_POLARIS);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sd;
	}
	public static void main(String[] args) {
/*
		System.out.println("==== double variables =====");
		double d1 = 0.3;
		double d2 = 0.2;
		System.out.println("d1= " + d1);
		System.out.println("d2= " + d2);
		System.out.println(d2 - d1);
		
		System.out.println("==== BigDecimal variables(use double to new) =====");
		BigDecimal bd0 = new BigDecimal(d1);
		System.out.println("bd0= " + bd0);
		
		System.out.println("==== BigDecimal variables(use String to new) =====");
		BigDecimal bd1 = new BigDecimal("0.3");
		BigDecimal bd2 = new BigDecimal("0.2");
		System.out.println("bd1= " + bd1);
		System.out.println("bd2= " + bd2);
		System.out.println(bd2.subtract(bd1));
*/
		getStocksData("2002");
	}
}
