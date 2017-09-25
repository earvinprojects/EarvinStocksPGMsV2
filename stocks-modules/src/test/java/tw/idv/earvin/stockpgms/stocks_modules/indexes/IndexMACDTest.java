package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexMACDTest {
	private static TaiwanDataPolarisIndexesValues[] compareData = null;
	@Before
	public void instantiate() throws Exception {
		compareData = new TaiwanDataPolarisIndexesValues[11];
		compareData[0] = new TaiwanDataPolarisIndexesValues();
		compareData[0].setDate(950209);
		compareData[0].setIndexCode(9012);
		compareData[0].setStockNo("2002");
		compareData[0].setValue(0);

		compareData[1] = new TaiwanDataPolarisIndexesValues();
		compareData[1].setDate(950210);
		compareData[1].setIndexCode(9012);
		compareData[1].setStockNo("2002");
		compareData[1].setValue(0);

		compareData[2] = new TaiwanDataPolarisIndexesValues();
		compareData[2].setDate(950213);
		compareData[2].setIndexCode(9012);
		compareData[2].setStockNo("2002");
		compareData[2].setValue(0);

		compareData[3] = new TaiwanDataPolarisIndexesValues();
		compareData[3].setDate(950214);
		compareData[3].setIndexCode(9012);
		compareData[3].setStockNo("2002");
		compareData[3].setValue(0.01);

		compareData[4] = new TaiwanDataPolarisIndexesValues();
		compareData[4].setDate(950215);
		compareData[4].setIndexCode(9012);
		compareData[4].setStockNo("2002");
		compareData[4].setValue(0.01);

		compareData[5] = new TaiwanDataPolarisIndexesValues();
		compareData[5].setDate(950216);
		compareData[5].setIndexCode(9012);
		compareData[5].setStockNo("2002");
		compareData[5].setValue(0.01);

		compareData[6] = new TaiwanDataPolarisIndexesValues();
		compareData[6].setDate(950217);
		compareData[6].setIndexCode(9012);
		compareData[6].setStockNo("2002");
		compareData[6].setValue(0.02);

		compareData[7] = new TaiwanDataPolarisIndexesValues();
		compareData[7].setDate(950220);
		compareData[7].setIndexCode(9012);
		compareData[7].setStockNo("2002");
		compareData[7].setValue(0.03);

		compareData[8] = new TaiwanDataPolarisIndexesValues();
		compareData[8].setDate(950221);
		compareData[8].setIndexCode(9012);
		compareData[8].setStockNo("2002");
		compareData[8].setValue(0.05);

		compareData[9] = new TaiwanDataPolarisIndexesValues();
		compareData[9].setDate(950222);
		compareData[9].setIndexCode(9012);
		compareData[9].setStockNo("2002");
		compareData[9].setValue(0.06);

		compareData[10] = new TaiwanDataPolarisIndexesValues();
		compareData[10].setDate(950223);
		compareData[10].setIndexCode(9012);
		compareData[10].setStockNo("2002");
		compareData[10].setValue(0.08);
	}

	@Test
	public void calculateMACDTest() {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexMACD.calculateMACD(sd, 12, 12, 26);
		for (int i = 0; i < vec.size(); i++) {
			TaiwanDataPolarisIndexesValues[] indexData = vec.get(i);
			// MACD指標是由3個值算出，此處只檢查MACD指標值的正確性
			if ((indexData != null) && (indexData[0].getIndexCode() == compareData[0].getIndexCode())) {
				for (int j = 0; j < indexData.length; j++) {
					assertEquals(compareData[j].getValue(), indexData[j].getValue(), 0.01);
				}
			}
		}
		
	}
}
