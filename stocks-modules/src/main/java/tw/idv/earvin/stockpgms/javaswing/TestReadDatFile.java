package tw.idv.earvin.stockpgms.javaswing;

import java.io.FileInputStream;
import java.io.FileReader;

public class TestReadDatFile {
	// 日期,開盤,最高,最低,收盤, 成交量, 融資張數, 融券張數

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader(
					"C:\\myData\\Dropbox\\myStocksPGMs\\V2.0\\PolarisDataToDat\\csv\\2002.csv");
			char[] cbuf = new char[100];
			int hasRead = 0;
			while ((hasRead = fr.read(cbuf)) > 0) {
				System.out.println(new String(cbuf, 0, hasRead));
			}
			
			fr.close();
		} catch (Exception e) {

		}
	}

}
