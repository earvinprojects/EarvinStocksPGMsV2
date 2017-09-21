package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexStochRSI {
	
	/**
	 * -- not finish, needs calculate RSI first.
	 * @param sd
	 * @param indexDay
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateStochRSI(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;
		long indexCode = StocksIndexesName.getIndexCode("StochRSI_" + indexDay);
		System.out.println("[IndexRWMS.calculateStochRSI(...)] -- IndexCode(StochRSI, " + indexDay + ") = " + indexCode);
		int i = 0, j = 0;
		double minValue = 0, maxValue = 0;
		while (j <= sd.length) {
			if (j <= indexDay) {
				minValue = 0;
				maxValue = 100;
				for (i = 0; i < j; i++) {
//					if (minValue < sd[i].)
				}
			}
		}
		/*
    While j <= intStockNo
        If j <= StochRSI_No Then
            sngMax = 0
            sngMin = 100
            For i = 1 To j
                If sngMax < udtIndex(i).sngRSI_S Then
                    sngMax = udtIndex(i).sngRSI_S
                End If
                If sngMin > udtIndex(i).sngRSI_S Then
                    sngMin = udtIndex(i).sngRSI_S
                End If
            Next i
        Else
            sngMax = 0
            sngMin = 100
            For i = j To j - StochRSI_No + 1 Step -1
                If sngMax < udtIndex(i).sngRSI_S Then
                    sngMax = udtIndex(i).sngRSI_S
                End If
                If sngMin > udtIndex(i).sngRSI_S Then
                    sngMin = udtIndex(i).sngRSI_S
                End If
            Next i
        End If
        If (sngMax - sngMin <> 0) Then
            udtIndex(j).sngStochRSI = (udtIndex(j).sngRSI_S - sngMin) * 100 / (sngMax - sngMin)
        Else
            udtIndex(j).sngStochRSI = 0.5
        End If
        j = j + 1
    Wend
		 */

		
		
		
		return indexData;
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950201, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateStochRSI(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				System.out.println(i + ", value= " + indexData[i].print());
				// indexData[i].insert();
			}		
		}
	}
}
