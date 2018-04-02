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
		
		// MAP
		for (int i = 5; i < 360; i ++) {
			if ( i == 5 || i == 20 || i == 60 || i == 120 || i == 240) {
				// MAP
				TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, i);
				indexsData.setIndexValues(String.valueOf(indexMAP[0].getIndexCode()), indexMAP);							
				// MAV
				TaiwanDataPolarisIndexesValues[] indexMAV = IndexMAV.calculateMAV(sd, i);
				indexsData.setIndexValues(String.valueOf(indexMAV[0].getIndexCode()), indexMAV);
			}
			
			if ( i == 5 || i == 10 || i == 20) {
				// PSY
				TaiwanDataPolarisIndexesValues[] indexPSY = IndexPSY.calculatePSY(sd, i);
				indexsData.setIndexValues(String.valueOf(indexPSY[0].getIndexCode()), indexPSY);							
				// RSI
				TaiwanDataPolarisIndexesValues[] indexRSI = IndexRSI.calculateRSI(sd, i);
				indexsData.setIndexValues(String.valueOf(indexRSI[0].getIndexCode()), indexRSI);										
				// RWMS
				TaiwanDataPolarisIndexesValues[] indexRWMS = IndexRWMS.calculateRWMS(sd,i);
				indexsData.setIndexValues(String.valueOf(indexRWMS[0].getIndexCode()), indexRWMS);							
				// VR 
				TaiwanDataPolarisIndexesValues[] indexVR = IndexVR.calculateVR(sd, i);
				indexsData.setIndexValues(String.valueOf(indexVR[0].getIndexCode()), indexVR);							
				// StochRSI
//				TaiwanDataPolarisIndexesValues[] indexStochRSI = IndexStochRSI.calculateStochRSI(sd, i);
//				indexsData.setIndexValues(String.valueOf(indexStochRSI[0].getIndexCode()), indexStochRSI);	
			}	
		}
		
		// KD
		Vector<TaiwanDataPolarisIndexesValues[]> vecKD = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vecKD.size(); i++) {
			TaiwanDataPolarisIndexesValues[] values =  vecKD.get(i);
			indexsData.setIndexValues(String.valueOf(values[0].getIndexCode()), values);							
		}
		
		// MACD
		Vector<TaiwanDataPolarisIndexesValues[]> vecMACD = IndexMACD.calculateMACD(sd, 12, 12, 26);
		for (int i = 0; i < vecMACD.size(); i++) {
			TaiwanDataPolarisIndexesValues[] values =  vecMACD.get(i);
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
