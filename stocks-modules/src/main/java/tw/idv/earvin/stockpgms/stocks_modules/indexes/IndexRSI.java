package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexRSI {

	public static TaiwanDataPolarisIndexesValues[] calculateRSI(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode("RSI_" + indexDay);
//		System.out.println("[IndexRSI.calculateRSI(...)] -- IndexCode(RSI, " + indexDay + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			int j = 0;
			double upValue = 0, downValue = 0, diffValue = 0, rsiValue = 0;
			while (j < sd.length) {
				rsiValue = 0;
				upValue = 0;
				downValue = 0;
				if (j < indexDay) {
					for (int i = 1; i <= j; i++) {
						diffValue = sd[i].getEndPrice() - sd[i - 1].getEndPrice();
						if (diffValue > 0)
							upValue += diffValue;
						else
							downValue += Math.abs(diffValue);
					}
					upValue /= (j + 1);
					downValue /= (j + 1);
				} else {
					for (int i = j; i > (j - indexDay); i--) {
						diffValue = sd[i].getEndPrice() - sd[i - 1].getEndPrice();
						if (diffValue > 0)
							upValue += diffValue;
						else
							downValue += Math.abs(diffValue);
					}
					upValue /= indexDay;
					downValue /= indexDay;
				}
				if ((j != 0) && ((upValue + downValue) != 0)) {
					rsiValue = upValue / (upValue + downValue) * 100;
				} else {
					rsiValue = 50;
				}
//				System.out.println(sd[j].printData() + " -- the rsiValue= " + rsiValue);
				indexData[j] = new TaiwanDataPolarisIndexesValues();
				indexData[j].setDate(sd[j].getDate());
				indexData[j].setIndexCode(indexCode);
				indexData[j].setStockNo(sd[j].getStockNo());
				indexData[j].setValue(rsiValue);

				j += 1;
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951225, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateRSI(sd, 6);
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
