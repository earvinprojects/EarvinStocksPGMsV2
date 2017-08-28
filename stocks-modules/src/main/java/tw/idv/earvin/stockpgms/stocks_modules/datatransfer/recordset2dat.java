package tw.idv.earvin.stockpgms.stocks_modules.datatransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class recordset2dat {

	public static void main(String[] args) {
		recordset2dat x = new recordset2dat();
//		x.testSelfType();
//		x.testArray();
		x.testRun();
	}

	private class gudtStockData {
		public double tradeDate; // 1
		public double startPrice; // 4
		public double highPrice; // 5
		public double lowPrice; // 6
		public double endPrice; // 7
		public double volume; // 9
		public double acc;
		public double tome;
	}

/*	public void testSelfType() {
		try {
			gudtStockData sd = new gudtStockData();

			sd.tradeDate = 1;
			sd.startPrice = 1;
			sd.highPrice = 1;
			sd.lowPrice = 1;
			sd.endPrice = 1;
			sd.volume = 1;
			sd.acc = 1;
			sd.tome = 1;

			System.out.println(sd.tradeDate + "," + "\t" + sd.startPrice + "," + "\t" + sd.highPrice + "," + "\t"
					+ sd.lowPrice + "," + "\t" + sd.endPrice + "," + "\t" + sd.volume + "," + "\t" + sd.acc + "," + "\t"
					+ sd.tome);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
*/
	public void testArray() {
		try {
			gudtStockData[] sd = new gudtStockData[2];
			for (int i = 0; i < 2; i++) {
				sd[i] = new gudtStockData();
				sd[i].tradeDate = 1;
				sd[i].startPrice = 1;
				sd[i].highPrice = 1;
				sd[i].lowPrice = 1;
				sd[i].endPrice = 1;
				sd[i].volume = 1;
				sd[i].acc = 1;
				sd[i].tome = 1;
			}
			int i = 1;
			for (gudtStockData sdn : sd) {
				System.out.println((i++) + "," + "\t" + sdn.tradeDate + "," + "\t" + sdn.startPrice + "," + "\t"
						+ sdn.highPrice + "," + "\t" + sdn.lowPrice + "," + "\t" + sdn.endPrice + "," + "\t"
						+ sdn.volume + "," + "\t" + sdn.acc + "," + "\t" + sdn.tome);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void testRun() {
		/*
		 * https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list
		 * -of-the-records/
		 */
		// TODO Auto-generated method stub
		String sql_cnt = "SELECT count(*) FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ?  ";
		String sql = "SELECT * FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY LENGTH(DATE), DATE ";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = null;
			//
			pstmt = conn.prepareStatement(sql_cnt);
			pstmt.setString(1, "2357");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			System.out.println("count=" + count);
			//

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "2357");
			rs = pstmt.executeQuery();
			int i = 0;
			gudtStockData[] sd = new gudtStockData[count];
			while (rs.next()) {
				sd[i] = new gudtStockData();
				sd[i].tradeDate = Double.parseDouble(rs.getString(1));
				sd[i].startPrice = Double.parseDouble(rs.getString(4));
				sd[i].highPrice = Double.parseDouble(rs.getString(5));
				sd[i].lowPrice = Double.parseDouble(rs.getString(6));
				sd[i].endPrice = Double.parseDouble(rs.getString(7));
				sd[i].volume = Double.parseDouble(rs.getString(9));
				sd[i].acc = 1;
				sd[i].tome = 1;
				i++;
			}
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
			
			i = 1;
			for (gudtStockData sdn : sd) {
				System.out.println((i++) + "," + "\t" + sdn.tradeDate + "," + "\t" + sdn.startPrice + "," + "\t"
						+ sdn.highPrice + "," + "\t" + sdn.lowPrice + "," + "\t" + sdn.endPrice + "," + "\t"
						+ sdn.volume + "," + "\t" + sdn.acc + "," + "\t" + sdn.tome);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
