package tw.idv.earvin.stockpgms.stocks_modules.datatransfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tw.idv.earvin.stockpgms.stocks_modules.db.DatabaseImp;

public class ParsePolarisFile {
	public static void main(String[] args) throws IOException {
		InsertTaiwanDataPolaris();
//		GetTaiwanDataPolaris();
	}
	
	public static void GetTaiwanDataPolaris() {
		String TRANS_DATE = "1000103";
		// establish connection
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DatabaseImp.getConnection();
			ps=conn.prepareStatement("SELECT * FROM TAIWAN_DATA_POLARIS WHERE DATE = ? "); 
            ps.setString(1,TRANS_DATE);
            ResultSet rs = ps.executeQuery();   
            while (rs.next()) {
            	System.out.println("aa=" + rs.getString("STOCK_NAME"));
            }
			
			ps.close();
			conn.close();
			
			System.out.println("讀檔完成!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void InsertTaiwanDataPolaris() {
		String TRANS_DATE = "20160919";

		// establish connection
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DatabaseImp.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO TAIWAN_DATA_POLARIS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			//-- 處理utf8 STR ---------------------------------------------------------------------------
			Path path = Paths.get("C:\\myData\\Close" + TRANS_DATE + "-utf8.txt");
			ReadableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);
			Charset charset = Charset.forName("UTF-8");
			List<String> lines;
			lines = Files.readAllLines(path, charset);
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if ("0123456789ABCDEFGHIJKMNOPQRSUVWXYZ".indexOf(line.substring(0, 1)) >= 0) {
					String area1 = line.substring(0, 10).trim();
					String area2 = line.substring(10, 29).trim();
					String[] area3 = line.substring(29, (line.length())).replaceAll("\\s+", " ").trim().split("\\s");
					ps.setString(1, String.valueOf((Integer.parseInt(TRANS_DATE) - 19110000)));
					ps.setString(2, area1);
					ps.setString(3, area2);
					for (int j = 0; j < area3.length; j++) {
						if (j == 4) {
							ps.setString((4 + j), area3[j]);
						} else {
							ps.setBigDecimal((4 + j), new BigDecimal(area3[j]));
						}
					}
					// ps.execute();
					int result = ps.executeUpdate();
					ps.clearParameters();
					// System.out.println(i + "= " + line);
					System.out.println(i + "= " + area1 + "--" + area2 + "--" + area3.length);
				}
			}
			conn.commit();
			ps.close();
			//-- 處理utf8 END ---------------------------------------------------------------------------
			
			
/**
			InputStream is = new FileInputStream("C:\\myData\\Close" + TRANS_DATE + ".txt");  
	        InputStreamReader reader = new InputStreamReader(is, "BIG5"); 
	        BufferedReader bReader = new BufferedReader(reader);
	        int i = 0;
	        String line;  
	        while ((line = bReader.readLine()) != null) {  
	        	System.out.println((i++) + "= " + line);
				if ("0123456789ABCDEFGHIJKMNOPQRSUVWXYZ".indexOf(line.substring(0, 1)) >= 0) {
					String area1 = line.substring(0, 10).trim();
					String area2 = line.substring(10, 29).trim();
					String[] area3 = line.substring(29, (line.length())).replaceAll("\\s+", " ").trim().split("\\s");
					ps.setString(1, String.valueOf((Integer.parseInt(TRANS_DATE) - 19110000)));
					ps.setString(2, area1);
					ps.setString(3, area2);
					for (int j = 0; j < area3.length; j++) {
						if (j == 4) {
							ps.setString((4 + j), area3[j]);
						} else {
							ps.setBigDecimal((4 + j), new BigDecimal(area3[j]));
						}
					}
					// ps.execute();
					int result = ps.executeUpdate();
					ps.clearParameters();
					if (i > 50) break;
					// System.out.println(i + "= " + line);
					System.out.println(i + "= " + area1 + "--" + area2 + "--" + area3.length);
				}
	        	
	        }  
	        is.close();  
			conn.commit();
			ps.close();
**/
			
			System.out.println("寫檔完成!!");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
