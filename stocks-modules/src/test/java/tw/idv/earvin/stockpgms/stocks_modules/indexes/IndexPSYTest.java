package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexPSYTest {
	private static TaiwanDataPolarisIndexesValues[] compareData = null;

	@Before
	public void instantiate() throws Exception {
		compareData = new TaiwanDataPolarisIndexesValues[11];
		compareData[0] = new TaiwanDataPolarisIndexesValues();
		compareData[0].setDate(950209);
		compareData[0].setIndexCode(3013);
		compareData[0].setStockNo("2002");
		compareData[0].setValue(50.0);

		compareData[1] = new TaiwanDataPolarisIndexesValues();
		compareData[1].setDate(950210);
		compareData[1].setIndexCode(3013);
		compareData[1].setStockNo("2002");
		compareData[1].setValue(50.0);

		compareData[2] = new TaiwanDataPolarisIndexesValues();
		compareData[2].setDate(950213);
		compareData[2].setIndexCode(3013);
		compareData[2].setStockNo("2002");
		compareData[2].setValue(50.0);

		compareData[3] = new TaiwanDataPolarisIndexesValues();
		compareData[3].setDate(950214);
		compareData[3].setIndexCode(3013);
		compareData[3].setStockNo("2002");
		compareData[3].setValue(50.0);

		compareData[4] = new TaiwanDataPolarisIndexesValues();
		compareData[4].setDate(950215);
		compareData[4].setIndexCode(3013);
		compareData[4].setStockNo("2002");
		compareData[4].setValue(50.0);

		compareData[5] = new TaiwanDataPolarisIndexesValues();
		compareData[5].setDate(950216);
		compareData[5].setIndexCode(3013);
		compareData[5].setStockNo("2002");
		compareData[5].setValue(60.0);

		compareData[6] = new TaiwanDataPolarisIndexesValues();
		compareData[6].setDate(950217);
		compareData[6].setIndexCode(3013);
		compareData[6].setStockNo("2002");
		compareData[6].setValue(40.0);

		compareData[7] = new TaiwanDataPolarisIndexesValues();
		compareData[7].setDate(950220);
		compareData[7].setIndexCode(3013);
		compareData[7].setStockNo("2002");
		compareData[7].setValue(60.0);

		compareData[8] = new TaiwanDataPolarisIndexesValues();
		compareData[8].setDate(950221);
		compareData[8].setIndexCode(3013);
		compareData[8].setStockNo("2002");
		compareData[8].setValue(40.0);

		compareData[9] = new TaiwanDataPolarisIndexesValues();
		compareData[9].setDate(950222);
		compareData[9].setIndexCode(3013);
		compareData[9].setStockNo("2002");
		compareData[9].setValue(40.0);

		compareData[10] = new TaiwanDataPolarisIndexesValues();
		compareData[10].setDate(950223);
		compareData[10].setIndexCode(3013);
		compareData[10].setStockNo("2002");
		compareData[10].setValue(40.0);
	}

	@Test
	public void calculatePSYTest() {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = IndexPSY.calculatePSY(sd, 5);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				assertEquals(compareData[i].getValue(), indexData[i].getValue(), 0.1);
			}
		}
	}

}
