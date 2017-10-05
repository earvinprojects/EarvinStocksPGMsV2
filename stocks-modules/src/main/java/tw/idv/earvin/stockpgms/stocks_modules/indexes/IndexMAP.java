package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexMAP {
	/**
	 * Calculate MAP index
	 * 
	 * @param sd
	 * @param indexDay
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateMAP(StocksData[] sd, int indexDay) {
		String indexName = "MAP_" + indexDay;
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode(indexName);
		System.out.println("[IndexMAP.calculateMAP(...)] -- IndexCode(" + indexName + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			// Calculate the MAP
			for (int i = 0; i < sd.length; i++) {
				double average = 0;
				if (i < indexDay) {
					for (int j = 0; j <= i; j++) {
						average += sd[i - j].getEndPrice();
					}
					average /= (i + 1);
				} else {
					for (int j = 0; j < indexDay; j++) {
						average += sd[i - j].getEndPrice();
					}
					average /= indexDay;
				}
				System.out.println(sd[i].printData() + " -- MAP the average= " + average);
				indexData[i] = new TaiwanDataPolarisIndexesValues();
				indexData[i].setStockNo(sd[i].getStockNo());
				indexData[i].setDate(sd[i].getDate());
				indexData[i].setIndexCode(indexCode);
				indexData[i].setValue(average);
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateMAP(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
				if (indexData[i].hasData())
					indexData[i].update();
				else
					indexData[i].insert();
			}
		}
		System.out.println("測試完成!!");
	}
}
