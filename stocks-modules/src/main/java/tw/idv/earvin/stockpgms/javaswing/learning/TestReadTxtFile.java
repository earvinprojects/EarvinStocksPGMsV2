package tw.idv.earvin.stockpgms.javaswing.learning;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
	
	public static IndexsData getIndexsData(StocksData[] sd) {
		IndexsData indexsData = new IndexsData();
		indexsData.setStockNo("2002");
		
		for (int i = 5; i < 360; i ++) {
			if ( i == 5 || i == 20 || i == 60 || i == 120 || i == 240) {
				TaiwanDataPolarisIndexesValues[] values = IndexMAP.calculateMAP(sd, i);
				indexsData.setIndexValues(String.valueOf(1000+i), values);							
			}
		}

		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			TaiwanDataPolarisIndexesValues[] values =  vec.get(i);
			indexsData.setIndexValues(String.valueOf(values[0].getIndexCode()), values);							
		}
		return indexsData;
	}

	public static void main(String[] args) {
		StocksData[] sds = getStocksData();
		for (int i = 0; i < sds.length; i++) {
			System.out.println((i + 1) + " : " + sds[i].printData());
		}
		
		IndexsData ind = getIndexsData(sds);
		ind.printAllData();
	}
}
