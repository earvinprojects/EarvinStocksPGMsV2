package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndexMAP {
	
	/**
	 * Calculate the average value of volume and main value.
	 * @param stockNo 股票代號
	 */
	public static StocksData[] calculateMAP(String stockNo) {
		String SQL_1 = "SELECT COUNT(*) AS COUNTS FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ";
		String SQL_2 = "SELECT STOCK_NO, DATE, START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE, VOLUME FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY DATE ";
		String SQL_3 = "INSERT INTO TAIWAN_DATA_POLARIS_INDEXES_VALUES (STOCK_NO, INDEX_CODE, DATE, VALUE) VALUES (?, ?, ?, ?) ";
		int indexDay = 5;
		int counts = 100;
		StocksData[] sd = null;

		try {
			// 取得筆數
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL_1);
			pstmt.setString(1, stockNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				counts = rs.getInt("COUNTS");
			}
			System.out.println("Total records is " + counts);
			// 計算
			sd = new StocksData[counts];
			pstmt = conn.prepareStatement(SQL_2);
			pstmt.setString(1, stockNo);
			rs = pstmt.executeQuery();
			for (int i = 0; i < counts; i++) {
//				System.out.println("Total records is " + rs.getString("STOCK_NO") + ", " + rs.getInt("DATE"));
				rs.next();
				sd[i] = new StocksData();
				sd[i].setStockNo(rs.getString("STOCK_NO"));
				sd[i].setTradeDate(rs.getInt("DATE"));
				sd[i].setStartPrice(rs.getDouble("START_PRICE"));
				sd[i].setHighPrice(rs.getDouble("HIGH_PRICE"));
				sd[i].setLowPrice(rs.getDouble("LOW_PRICE"));
				sd[i].setEndPrice(rs.getDouble("END_PRICE"));
				sd[i].setVolume(rs.getDouble("VOLUME"));
				
				System.out.println(sd[i].printData()); 				
			}
			
			for (int i = 0; i < sd.length; i++) {
				double average = 0;
				if (i < indexDay) {
					for (int j = 0; j <= i; j++) {
						average += sd[i-j].getEndPrice();
					}
					average /= (i+1);				
				} else {
					for (int j = 0; j < indexDay; j++) {
						average += sd[i-j].getEndPrice();
					}
					average /= indexDay;				
				}
				System.out.println(sd[i].printData() + " -- the average= " + average);
				// insert to db
				pstmt = conn.prepareStatement(SQL_3);
				pstmt.setString(1, sd[i].getStockNo());
				pstmt.setInt(2, 1001);
				pstmt.setLong(3, sd[i].getTradeDate());
				pstmt.setDouble(4, average);
//				pstmt.

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
	
	/**
	 * 更新日期：2017.08.21
	 * 新增技術指標的值到：TAIWAN_DATA_POLARIS_INDEXES_VALUES
	 * @param sd
	 */
	public void insertMAP(StocksData[] sd) {
		
	}
	
	/**
	 * 計算recordset筆數
	 * @param resultSet 
	 * @return int 資料筆數
	 */
	public static int getResultSetRowCount(ResultSet resultSet) {
	    int size = 0;
	    try {
	        resultSet.last();
	        size = resultSet.getRow();
	        resultSet.beforeFirst();
	    }
	    catch(Exception ex) {
	        return 0;
	    }
	    return size;
	}
	public static void main(String[] args) {
		StocksData[] sd = calculateMAP("2002");
	}
}
