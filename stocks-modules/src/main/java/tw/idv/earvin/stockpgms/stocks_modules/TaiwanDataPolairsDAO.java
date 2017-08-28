package tw.idv.earvin.stockpgms.stocks_modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TaiwanDataPolairsDAO {
	public static void main(String[] args) {
		ReadByNIO();
	}

	public static void ReadByIO() {
		String filename = "C:\\Users\\linea_000\\Dropbox\\myStocksPGMs\\stocksfiles\\Close20160729.TXT";
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// nothing ...
		}
	}

	public static void ReadByNIO() {
		Path path = Paths.get("C:\\myData\\PGM_TEST_AREA\\SOURCE\\Close20160728.TXT");
		Path pathTo = Paths.get("C:\\myData\\PGM_TEST_AREA\\Test.TXT");

		Charset charset = Charset.forName("MS950");
		List<String> lines;
		try {
			// 把檔案讀進lines，不適合讀取大型檔案(會out of memory)
			lines = Files.readAllLines(path, charset);
//			System.out.println(lines);
			Files.write(pathTo, lines, charset, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE });
			// 一次讀取一行!!
			StringBuilder sb = new StringBuilder(); 
			// foreach command : for(declaration : expression)
			for(String line : lines) { 
				System.out.println(line); 
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
