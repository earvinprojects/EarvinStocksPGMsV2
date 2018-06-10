package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexVR {
	/**
	 * VR Index (成交量比率) 依據「量先於價」及「價量同步同向」之理論，計算一段期間內上漲日交易金 額與下跌日交易金額之比率關係，以為研依據
	 * Formula      : VR(n) = [UpTotVol(n)+(1/2×EquTotVol(n)] / [DownTotVol(n)+(1/2×EquTotVol(n)] × 100 
	 * UpTotVol(n)  : 表示過去n日股價上漲日之成交量總數
	 * DownTotVol(n): 表示過去n日股價下跌日之成交量總數 
	 * EquTotVol(n) : 表示過去n日股價不變日之成交量總數
	 * 
	 * @param sd
	 * @param indexDay
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateVR(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode("VR_" + indexDay);
//		System.out.println("[IndexVR.calculateVR(...)] -- IndexVR(VR, " + indexDay + ") = " + indexCode);

		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			int j = 0;
			double vrValue = 0, volUpValue = 0, volDownValue = 0, volEquValue = 0;
			while (j < sd.length) {
				if (j < (indexDay-1)) {
					vrValue = 0;
				} else {
					volUpValue = 0;
					volDownValue = 0;
					volEquValue = 0;
					for (int i = j; i > (j - indexDay); i--) {
						if (sd[i].getEndPrice() > sd[i].getStartPrice()) {
							volUpValue += sd[i].getVolume();
						} else if (sd[i].getEndPrice() < sd[i].getStartPrice()) {
							volDownValue += sd[i].getVolume();
						} else {
							volEquValue += sd[i].getVolume();
						}

					}
					// --- 若分母為0，則將值設為0 ---
					if ((volDownValue + (volEquValue / 2)) > 0) {
						vrValue = ((volUpValue + (volEquValue / 2)) / (volDownValue + (volEquValue / 2)))
								* 100;
					} else {
						vrValue = 0;
					}
				}

//				System.out.println(sd[j].printData() + " -- the vrValue= " + vrValue);
				indexData[j] = new TaiwanDataPolarisIndexesValues();
				indexData[j].setDate(sd[j].getDate());
				indexData[j].setIndexCode(indexCode);
				indexData[j].setStockNo(sd[j].getStockNo());
				indexData[j].setValue(vrValue);

				j += 1;
			}
		}
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950331, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateVR(sd, 6);
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
