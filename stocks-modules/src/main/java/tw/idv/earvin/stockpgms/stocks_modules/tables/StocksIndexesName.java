package tw.idv.earvin.stockpgms.stocks_modules.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;

public class StocksIndexesName {
	private long indexCode;
	private String indexName;
	private String description;
	private HashMap<String, Long> sinHM = new HashMap<String, Long>();
	
	private void StocksIndexsName() {
		// 20180330 Add new HashMap
		//--- IndexName number STR ---------------------------------------------------
		sinHM.put("MAP", Long.parseLong("1000"));
		sinHM.put("BIAS", Long.parseLong("2000"));
		sinHM.put("PSY", Long.parseLong("3000"));
		sinHM.put("VR", Long.parseLong("4000"));
		sinHM.put("RSI", Long.parseLong("5000"));
		sinHM.put("K", Long.parseLong("6000"));
		sinHM.put("D", Long.parseLong("7000"));
		sinHM.put("WMS", Long.parseLong("8000"));
		sinHM.put("MACD", Long.parseLong("9000"));
		sinHM.put("MAV", Long.parseLong("10000"));
		sinHM.put("RWMS", Long.parseLong("11000"));
		sinHM.put("StochRSI", Long.parseLong("12000"));
		sinHM.put("DIF_for_MACD", Long.parseLong("13000"));
		sinHM.put("CY_for_MACD", Long.parseLong("14000"));
		sinHM.put("EMA", Long.parseLong("15000"));
		// for (Object key : sinHM.keySet()) {
		// System.out.println("aaaaa: " + key + ", " + sinHM.get(key));
		// }
		//--- IndexName number END ---------------------------------------------------
	}

	public long getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(long v) {
		indexCode = v;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String v) {
		indexName = v;
	}

	public String getDescription() {
		return description;
	}

	public void setDescirption(String v) {
		description = v;
	}
	
	public static long getIndexCode(String indexName) {
		long indexCode = 0;
		// 20180610 unix 有分大小寫，所以table要用小寫，要不然會找不到table
//		String SQL = "SELECT INDEX_CODE FROM STOCKS_INDEXES_NAME WHERE INDEX_NAME = ? ";
		String SQL = "SELECT INDEX_CODE FROM stocks_indexes_name WHERE INDEX_NAME = ? ";
		try {
			// 取得筆數
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, indexName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				indexCode = rs.getInt("INDEX_CODE");
			}
			System.out.println(indexName + " 代碼為 " + indexCode);
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return indexCode;
	}
}
