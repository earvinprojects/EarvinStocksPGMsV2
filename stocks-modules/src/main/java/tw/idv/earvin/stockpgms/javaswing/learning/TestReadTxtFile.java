package tw.idv.earvin.stockpgms.javaswing.learning;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import tw.idv.earvin.stockpgms.stocks_modules.indexes.*;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class TestReadTxtFile {
	// 日期,開盤,最高,最低,收盤, 成交量, 融資張數, 融券張數

	public static StocksData[] getStocksData() {
		StocksData[] stocksData = null;
		try {
			Path path = Paths.get("C:\\myData\\Dropbox\\myStocksPGMs\\V2.0\\PolarisDataToDat\\csv\\2002.csv");
			Charset charset = Charset.forName("MS950");
			List<String> lines;
			Vector<StocksData> vec = new Vector<StocksData>();
			lines = Files.readAllLines(path, charset);

			for (String line : lines) {
				String[] token = line.split(",");
				StocksData sd = new StocksData();
				sd.setDate(Long.parseLong(token[0]));
				sd.setStartPrice(Double.parseDouble(token[1]));
				sd.setHighPrice(Double.parseDouble(token[2]));
				sd.setLowPrice(Double.parseDouble(token[3]));
				sd.setEndPrice(Double.parseDouble(token[4]));
				sd.setVolume(Double.parseDouble(token[5]));
				sd.setMarginTrading(Double.parseDouble(token[6]));
				sd.setShortSelling(Double.parseDouble(token[7]));
				vec.add(sd);
			}
			stocksData = vec.toArray(new StocksData[vec.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stocksData;
	}
	
	public static IndexData getIndexsData(StocksData[] sd) {
		IndexData indexData = new IndexData();
		indexData.setStockNo("2002");
		
		for (int i = 5; i < 360; i ++) {
			if ( i == 5 || i == 20 || i == 60 || i == 120 || i == 240) {
				TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, i);
				indexData.setIndexValues(indexMAP);							
			}
		}
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			indexData.setIndexValues(vec.get(i));
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sds = getStocksData();
		for (int i = 0; i < sds.length; i++) {
			System.out.println((i + 1) + " : " + sds[i].printData());
		}
		
		IndexData ind = getIndexsData(sds);
		ind.printData();
	}
}
