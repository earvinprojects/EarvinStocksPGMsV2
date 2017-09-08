package tw.idv.earvin.stockpgms.stocks_modules.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;

public class StocksIndexesName {
	private long indexCode;
	private String indexName;
	private String description;

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
		String SQL = "SELECT INDEX_CODE FROM STOCKS_INDEXES_NAME WHERE INDEX_NAME = ? ";
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
