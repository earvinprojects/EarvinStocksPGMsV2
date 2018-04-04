package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexPSY {

	public static TaiwanDataPolarisIndexesValues[] calculatePSY(StocksData[] sd, int indexDay) {
		String indexName = "PSY_" + indexDay;
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode(indexName);
//		System.out.println("[IndexPSY.calculatePSY(...)] -- IndexCode(" + indexName + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			double psyValue = 0;
			double upDay = 0;
			for (int i = 0; i < sd.length; i++) {
				upDay = 0;
				if (i < indexDay) {
					psyValue = 50;
				} else {
					for (int j = i; j > (i - indexDay); j--) {
						if ((sd[j].getEndPrice() - sd[j - 1].getEndPrice()) > 0)
							upDay += 1;
					}
					psyValue = upDay / indexDay * 100;
				}
//				System.out.println(sd[i].printData() + " -- PSY the psyValue= " + psyValue);
				indexData[i] = new TaiwanDataPolarisIndexesValues();
				indexData[i].setStockNo(sd[i].getStockNo());
				indexData[i].setDate(sd[i].getDate());
				indexData[i].setIndexCode(indexCode);
				indexData[i].setValue(psyValue);
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculatePSY(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
//				if (indexData[i].hasData())
//					indexData[i].update();
//				else
//					indexData[i].insert();
			}
		}
		System.out.println("測試完成!!");
	}
}
