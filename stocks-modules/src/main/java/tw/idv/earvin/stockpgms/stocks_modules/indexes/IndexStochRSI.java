package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexStochRSI {

	/**
	 * -- Calculate index StockRSI. Before do it, needs calculate RSI first. 
	 * 
	 * @param sd
	 * @param indexDay
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateStochRSI(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = IndexRSI.calculateRSI(sd, indexDay);
		long indexCode = StocksIndexesName.getIndexCode("StochRSI_" + indexDay);
		System.out.println(
				"[IndexStochRSI.calculateStochRSI(...)] -- IndexCode(StochRSI, " + indexDay + ") = " + indexCode);
		int i = 0, j = 0;
		double minValue = 0, maxValue = 0, stochRSIValue = 0;
		while (j < indexData.length) {
			minValue = 100;
			maxValue = 0;
			if (j <= indexDay) {
				for (i = 0; i < j; i++) {
					if (minValue > indexData[i].getValue())
						minValue = indexData[i].getValue();
					if (maxValue < indexData[i].getValue())
						maxValue = indexData[i].getValue();
				}
			} else {
				for (i = j; i > (j - indexDay); i--) {
					if (minValue > indexData[i].getValue())
						minValue = indexData[i].getValue();
					if (maxValue < indexData[i].getValue())
						maxValue = indexData[i].getValue();
				}
			}
			if ((maxValue - minValue) != 0) {
				stochRSIValue = (indexData[j].getValue() - minValue) * 100 / (maxValue - minValue);
			} else {
				stochRSIValue = 0.5;
			}
			indexData[j].setIndexCode(indexCode);
			indexData[j].setValue(stochRSIValue);
			j += 1;
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950201, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateStochRSI(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
			}
		}
		System.out.println("測試完成");
	}
}
