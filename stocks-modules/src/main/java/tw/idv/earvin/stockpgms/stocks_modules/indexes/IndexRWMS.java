package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexRWMS {

	/**
	 * 計算RWMS技術指標
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
		System.out.println("[IndexRWMS.calculateRWMS(...)] -- IndexCode(RWMS, " + indexDay + ") = " + indexCode);

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
				// System.out.println(j + ", maxValue= " + maxValue + ", minValue= " + minValue
				// + " -- "
				// + sd[j].printData() + " -- the rwms value = " + (100 - wmsValue));
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

	/**
	 * Calculate the average value of RWMS and insert to DB(table :
	 * TAIWAN_DATA_POLARIS_INDEXES_VALUES).
	 * 
	 * @param stockNo
	 *            股票代號
	 */
	public static void CalculateRWMSAndInsertToDB(String stockNo, int indexDay) {
		int counts = 0;
		int updateCount = 0;
		int insertCount = 0;
		StocksData[] sd = null;
		System.out.println("RWMS指標作業開始 -- 處理股票代號= " + stockNo + ", 指標天數= " + indexDay);
		sd = StocksData.getStocksDataByStockNo(stockNo);
		System.out.println("本次計算資料總筆數為 " + sd.length);
		if (sd.length > 0) {
			TaiwanDataPolarisIndexesValues[] indexData = calculateRWMS(sd, indexDay);
			for (int i = 0; i < indexData.length; i++) {
				if (indexData[i].hasData()) {
					indexData[i].update();
					updateCount += 1;
				} else {
					indexData[i].insert();
					insertCount += 1;
				}
			}
		}
		System.out.println("RWMS指標作業完成 -- 處理股票代號= " + stockNo + ", 指標天數= " + indexDay + ", 總筆數= " + counts + ", 新增筆數= "
				+ insertCount + ", 更新筆數= " + updateCount);
	}

	public static void main(String[] args) {
		CalculateRWMSAndInsertToDB("2002", 10);
		// CalculateRWMSAndInsertToDB("2349", 5);
		// CalculateRWMSAndInsertToDB("2349", 30);
		// CalculateRWMSAndInsertToDB("2349", 90);
		// CalculateRWMSAndInsertToDB("2349", 180);
		// CalculateRWMSAndInsertToDB("2349", 360);
	}
}
