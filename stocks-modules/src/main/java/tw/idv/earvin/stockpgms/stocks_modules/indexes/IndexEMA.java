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
			double curEMAValue = 0;
			double prevEMAValue = 0;
			for (int i = 0; i < sd.length; i++) {
				if (i == 0) {
					curEMAValue = sd[i].getEndPrice();
					prevEMAValue = curEMAValue;
				} else {
//					curEMAValue = (sd[i].getEndPrice() * 2 / (indexDay + 1)) + prevEMAValue * (1 - (2 / (indexDay + 1)));
					curEMAValue = ((sd[i].getEndPrice() / (indexDay + 1)) * 2) + ((prevEMAValue / (indexDay + 1)) * (indexDay - 1));
					prevEMAValue = curEMAValue;
				}

				System.out.println(sd[i].printData() + " -- the EMA Value= " + curEMAValue);
				indexData[i] = new TaiwanDataPolarisIndexesValues();
				indexData[i].setStockNo(sd[i].getStockNo());
				indexData[i].setDate(sd[i].getDate());
				indexData[i].setIndexCode(indexCode);
				indexData[i].setValue(curEMAValue);
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateEMA(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
				// if (indexData[i].hasData())
				// indexData[i].update();
				// else
				// indexData[i].insert();
			}
		}
		System.out.println("EMA 測試完成!!");
	}

}
