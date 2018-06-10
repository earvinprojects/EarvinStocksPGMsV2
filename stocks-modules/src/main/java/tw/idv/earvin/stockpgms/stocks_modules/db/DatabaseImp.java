package tw.idv.earvin.stockpgms.stocks_modules.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImp {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/stocksdb", "root", "lin32ledi");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return conn;
		}

	}
}
