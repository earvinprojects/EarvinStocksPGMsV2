package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Vector;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexMACD {
	/**
	 * 計算MACD技術指標
	 * 
	 * @param sd
	 * @param macdIndexDay
	 * @param emaSIndexDay
	 * @param emaLIndexDay
	 * @return
	 */
	public static Vector<TaiwanDataPolarisIndexesValues[]> calculateMACD(StocksData[] sd, int macdIndexDay,
			int emaSIndexDay, int emaLIndexDay) {
		Vector<TaiwanDataPolarisIndexesValues[]> vec = new Vector<TaiwanDataPolarisIndexesValues[]>();
		TaiwanDataPolarisIndexesValues[] macdIndexData = null;
		TaiwanDataPolarisIndexesValues[] difIndexData = null;
		TaiwanDataPolarisIndexesValues[] cyIndexData = null;
		long macdIndexCode = StocksIndexesName.getIndexCode("MACD_" + macdIndexDay);
		long difIndexCode = StocksIndexesName.getIndexCode("DIF_" + macdIndexDay);
		long cyIndexCode = StocksIndexesName.getIndexCode("CY_" + macdIndexDay);
//		System.out.println("[IndexRWMS.calculateMACD(...)] -- IndexCode(MACD, " + macdIndexDay + "," + emaSIndexDay	+ "," + emaLIndexDay + ") = " + macdIndexCode);

		if (sd.length > 0) {
			macdIndexData = new TaiwanDataPolarisIndexesValues[sd.length];
			difIndexData = new TaiwanDataPolarisIndexesValues[sd.length];
			cyIndexData = new TaiwanDataPolarisIndexesValues[sd.length];
			// Calculate the MACD index
			int i = 0;
			double diValue = 0, emaSValue = 0, emaLValue = 0;
			double macdValue = 0, difValue = 0, cyValue = 0;
			double preEmaSValue = sd[0].getEndPrice();
			double preEmaLValue = sd[0].getEndPrice();
			double preMacdValue = 0;

			while (i < sd.length) {
				diValue = (sd[i].getHighPrice() + sd[i].getLowPrice() + sd[i].getEndPrice() * 2) / 4;
				emaSValue = preEmaSValue + (2 * (diValue - preEmaSValue) / (1 + emaSIndexDay));
				emaLValue = preEmaLValue + (2 * (diValue - preEmaLValue) / (1 + emaLIndexDay));
				difValue = emaSValue - emaLValue;
				macdValue = preMacdValue + (2 * (difValue - preMacdValue) / (1 + macdIndexDay));
				cyValue = difValue - macdValue;

//				System.out.println(sd[i].printData() + " -- MACD= " + macdValue + ", DIF= " + difValue + ", CY= " + cyValue);
				macdIndexData[i] = new TaiwanDataPolarisIndexesValues();
				macdIndexData[i].setDate(sd[i].getDate());
				macdIndexData[i].setIndexCode(macdIndexCode);
				macdIndexData[i].setStockNo(sd[i].getStockNo());
				macdIndexData[i].setValue(macdValue);

				difIndexData[i] = new TaiwanDataPolarisIndexesValues();
				difIndexData[i].setDate(sd[i].getDate());
				difIndexData[i].setIndexCode(difIndexCode);
				difIndexData[i].setStockNo(sd[i].getStockNo());
				difIndexData[i].setValue(difValue);

				cyIndexData[i] = new TaiwanDataPolarisIndexesValues();
				cyIndexData[i].setDate(sd[i].getDate());
				cyIndexData[i].setIndexCode(cyIndexCode);
				cyIndexData[i].setStockNo(sd[i].getStockNo());
				cyIndexData[i].setValue(cyValue);

				preEmaSValue = emaSValue;
				preEmaLValue = emaLValue;
				preMacdValue = macdValue;

				i = i + 1;
			}
			vec.add(macdIndexData);
			vec.add(difIndexData);
			vec.add(cyIndexData);
		}
		return vec;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950309, "2002");
		Vector<TaiwanDataPolarisIndexesValues[]> vec = calculateMACD(sd, 12, 12, 26);
		for (int i = 0; i < vec.size(); i++) {
			TaiwanDataPolarisIndexesValues[] indexData = vec.get(i);
			if (indexData != null) {
				for (int j = 0; j < indexData.length; j++) {
					System.out.println(j + ", value= " + indexData[j].print());
					if (indexData[j].hasData())
						indexData[j].update();
					else
						indexData[j].insert();
				}
			}
		}
		System.out.println("測試完成!!");
	}
}
