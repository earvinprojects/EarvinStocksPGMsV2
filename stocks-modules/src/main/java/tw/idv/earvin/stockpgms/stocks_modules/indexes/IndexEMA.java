package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexEMA {

	/**
	 * 說 明: Calculate the EMA value (指數平均數指標, Exponential Moving Average, EXPMA或EMA)
	 * Formula: EXPMA＝（當日或當期收盤價－上一日或上期EXPMA）／Ｎ＋上一日或上期EXPMA，其中，首次上期EXPMA值為上一期收盤價，Ｎ為天數
	 * 
	 * @param sd
	 *            股市資料
	 * @param indexDay
	 *            指標天數
	 * @return EMA技術指標值
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateEMA(StocksData[] sd, int indexDay) {
		String indexName = "EMA_" + indexDay;
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode(indexName);
		System.out.println("[IndexEMA.calculateEMA(...)] -- IndexCode(" + indexName + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			for (int i = 0; i < sd.length; i++) {
				int loops = 1;
				double emaValue = getTodayEMA(sd, i, indexDay, ((i < indexDay) ? -1 : loops));
				System.out.println(sd[i].printData() + " -- the EMA Value= " + emaValue);
				indexData[i] = new TaiwanDataPolarisIndexesValues();
				indexData[i].setStockNo(sd[i].getStockNo());
				indexData[i].setDate(sd[i].getDate());
				indexData[i].setIndexCode(indexCode);
				indexData[i].setValue(emaValue);
			}
		}
		return indexData;
	}

	private static double getTodayEMA(StocksData[] sd, int row, int indexDay, int loops) {
		if ((row < indexDay) && (loops < 0)) {
			if ((row % indexDay) == 0) {
				return sd[row].getEndPrice();
			} else {
				return ((2 * sd[row].getEndPrice()) / (indexDay + 1)
						+ (getTodayEMA(sd, row - 1, indexDay, loops) * (indexDay - 1) / (indexDay + 1)));
			}
		} else {
			if ((loops % indexDay) == 0) {
				return sd[row].getEndPrice();
			} else {
				return ((2 * sd[row].getEndPrice()) / (indexDay + 1)
						+ (getTodayEMA(sd, row - 1, indexDay, ++loops) * (indexDay - 1) / (indexDay + 1)));
			}
		}
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950331, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateEMA(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
				if (indexData[i].hasData())
					indexData[i].update();
				else
					indexData[i].insert();
			}
		}
		System.out.println("EMA 測試完成!!");
	}

}
