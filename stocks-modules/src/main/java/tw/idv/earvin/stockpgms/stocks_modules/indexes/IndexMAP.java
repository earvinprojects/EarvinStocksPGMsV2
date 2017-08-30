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
		String SQL_2 = "SELECT * FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY DATE ";
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
			while (rs.next()) {
				System.out.println("Total records is " + rs.getString("STOCK_NO") + ", " + rs.getInt("DATE"));		
			}
/*			
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
				System.out.println("the average[" + i + "]= " + average);
			}			
*/
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
	
	public static void main(String[] args) {
		StocksData[] sd = calculateMAP("2002");
	}
}
