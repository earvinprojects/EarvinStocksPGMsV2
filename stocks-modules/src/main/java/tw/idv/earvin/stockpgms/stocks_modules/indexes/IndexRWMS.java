package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexRWMS {
	/**
	 * 計算RWMS技術指標(其實就是WMS技術指標，我也搞不懂這樣子兩者不是沒差異，那弄這兩個指標要做啥？)
	 * 
	 * @param sd
	 *            股票資料
	 * @param indexDay
	 *            計算的指標天數
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateRWMS(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode("RWMS_" + indexDay);
//		System.out.println("[IndexRWMS.calculateRWMS(...)] -- IndexCode(RWMS, " + indexDay + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			// Calculate the RWMS index
			int i = 0, j = 0;
			double maxValue, minValue, wmsValue;

			while (j < sd.length) {
				maxValue = -1;
				minValue = 99999999;
				if (j < indexDay) {
					for (i = 0; i <= j; i++) {
						if (maxValue < sd[i].getHighPrice()) {
							maxValue = sd[i].getHighPrice();
						}
						if (minValue > sd[i].getLowPrice()) {
							minValue = sd[i].getLowPrice();
						}
					}
				} else {
					for (i = j; i > (j - indexDay); i--) {
						if (maxValue < sd[i].getHighPrice()) {
							maxValue = sd[i].getHighPrice();
						}
						if (minValue > sd[i].getLowPrice()) {
							minValue = sd[i].getLowPrice();
						}
					}
				}
				if (maxValue != minValue) {
					wmsValue = (maxValue - sd[j].getEndPrice()) / (maxValue - minValue) * 100;
				} else {
					wmsValue = 50;
				}
//				System.out.println(sd[i].printData() + " -- RWMS= " + (100 - wmsValue));
				indexData[j] = new TaiwanDataPolarisIndexesValues();
				indexData[j].setDate(sd[j].getDate());
				indexData[j].setIndexCode(indexCode);
				indexData[j].setStockNo(sd[j].getStockNo());
				indexData[j].setValue(100 - wmsValue);

				j = j + 1;
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateRWMS(sd, 6);
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
