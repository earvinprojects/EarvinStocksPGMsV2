package tw.idv.earvin.stockpgms.stocks_modules;

import java.sql.*;
import java.util.*;

public class mySQLConnectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/stocksdb", "root", "lin32ledi");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			Statement stmt = conn.createStatement();
			String SQL = "select date, count(*) from taiwan_data_polaris where length(date) = 7 group by date order by date limit 10 ";
			ResultSet rs = stmt.executeQuery(SQL);
			int i = 1;
			while (rs.next()) {
				System.out.println((i++) + "\t" + rs.getString(1) + "\t" + rs.getString(2));
			}
			stmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
