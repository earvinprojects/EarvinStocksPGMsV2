package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Vector;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexKD {
	public static Vector<TaiwanDataPolarisIndexesValues[]> calculateKD(StocksData[] sd, int indexDay) {
		Vector<TaiwanDataPolarisIndexesValues[]> vec = new Vector<TaiwanDataPolarisIndexesValues[]>();
		TaiwanDataPolarisIndexesValues[] kIndexData = null;
		TaiwanDataPolarisIndexesValues[] dIndexData = null;
		long kIndexCode = StocksIndexesName.getIndexCode("K_" + indexDay);
		long dIndexCode = StocksIndexesName.getIndexCode("D_" + indexDay);
		System.out.println(
				"[IndexKD.calculateKD(...)] -- IndexKD(KD, " + indexDay + ") = " + kIndexCode + ", " + dIndexCode);

		if (sd.length > 0) {
			kIndexData = new TaiwanDataPolarisIndexesValues[sd.length];
			dIndexData = new TaiwanDataPolarisIndexesValues[sd.length];
			
			int j = 0;
			double maxValue = 0, minValue = 0, rsvValue = 0;
			double prevKValue = 0, prevDValue = 0, KValue = 0, DValue = 0;
			while (j < sd.length) {
				maxValue = -1;
				minValue = 999999;
				if (j < indexDay) {
					for (int i = 0; i < j; i++) {
						if (maxValue < sd[i].getHighPrice())
							maxValue = sd[i].getHighPrice();
						if (minValue > sd[i].getLowPrice())
							minValue = sd[i].getLowPrice();
					}
				} else {
					for (int i = j; i > (j - indexDay); i--) {
						if (maxValue < sd[i].getHighPrice())
							maxValue = sd[i].getHighPrice();
						if (minValue > sd[i].getLowPrice())
							minValue = sd[i].getLowPrice();
					}
				}
				if (maxValue != minValue) {
					rsvValue = (sd[j].getEndPrice() - minValue) / (maxValue - minValue) * 100;
				} else {
					rsvValue = 50;
				}
				KValue = prevKValue * 2 / 3 + rsvValue / 3;
				DValue = prevDValue * 2 / 3 + KValue / 3;
				prevKValue = KValue;
				prevDValue = DValue;
				System.out.println(sd[j].printData() + " -- the KValue= " + KValue + ", the DValue= " + DValue);
				kIndexData[j] = new TaiwanDataPolarisIndexesValues();
				kIndexData[j].setDate(sd[j].getDate());
				kIndexData[j].setIndexCode(kIndexCode);
				kIndexData[j].setStockNo(sd[j].getStockNo());
				kIndexData[j].setValue(KValue);

				dIndexData[j] = new TaiwanDataPolarisIndexesValues();
				dIndexData[j].setDate(sd[j].getDate());
				dIndexData[j].setIndexCode(dIndexCode);
				dIndexData[j].setStockNo(sd[j].getStockNo());
				dIndexData[j].setValue(DValue);

				j += 1;
			}
			vec.add(kIndexData);
			vec.add(dIndexData);
		}
		return vec;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 951225, "2002");
		Vector<TaiwanDataPolarisIndexesValues[]> vec = calculateKD(sd, 6);
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
