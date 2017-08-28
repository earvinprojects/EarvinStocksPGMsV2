/**
 * http://stevenitlife.blogspot.tw/2015/07/nio2.html
 * 
 * <== String to ByteBuffer, ByteBuffer to String examples ==>
 * http://stackoverflow.com/questions/1252468/java-converting-string-to-and-from-bytebuffer-and-associated-problems
 */
package tw.idv.earvin.stockpgms.stocks_modules;

import java.io.IOException;
//import java.io.File;
//import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
//import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class nio2Test {
	public static Charset charset = Charset.forName("UTF-8");
	public static CharsetEncoder encoder = charset.newEncoder();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			Statement stmt = conn.createStatement();
			String SQL = "select date, count(*) from taiwan_data_polaris where length(date) = 7 group by date order by date limit 10 ";
			ResultSet rs = stmt.executeQuery(SQL);

			// File writeFile = new File("C:\\myData\test.txt");

			Path path = Paths.get("C:\\myData\\test.txt");
			// 讀檔
			// ReadableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);
			// 開檔
			WritableByteChannel channel = Files.newByteChannel(path, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE });

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int i = 1;
			while (rs.next()) {
				String line = (i++) + "\t" + rs.getString(1) + "\t" + rs.getString(2) + "\n";
				// String to ByteBuffer
				channel.write(encoder.encode(CharBuffer.wrap(line)));
				System.out.println(line);
			}
			stmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
			channel.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
