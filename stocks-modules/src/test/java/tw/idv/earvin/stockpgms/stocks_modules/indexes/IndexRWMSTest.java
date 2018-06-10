package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexRWMSTest {
	private static TaiwanDataPolarisIndexesValues[] compareData = null;

	@Before
	public void instantiate() throws Exception {
		compareData = new TaiwanDataPolarisIndexesValues[11];
		compareData[0] = new TaiwanDataPolarisIndexesValues();
		compareData[0].setDate(950209);
		compareData[0].setIndexCode(11006);
		compareData[0].setStockNo("2002");
		compareData[0].setValue(30);

		compareData[1] = new TaiwanDataPolarisIndexesValues();
		compareData[1].setDate(950210);
		compareData[1].setIndexCode(11006);
		compareData[1].setStockNo("2002");
		compareData[1].setValue(30.77);

		compareData[2] = new TaiwanDataPolarisIndexesValues();
		compareData[2].setDate(950213);
		compareData[2].setIndexCode(11006);
		compareData[2].setStockNo("2002");
		compareData[2].setValue(25);

		compareData[3] = new TaiwanDataPolarisIndexesValues();
		compareData[3].setDate(950214);
		compareData[3].setIndexCode(11006);
		compareData[3].setStockNo("2002");
		compareData[3].setValue(75);

		compareData[4] = new TaiwanDataPolarisIndexesValues();
		compareData[4].setDate(950215);
		compareData[4].setIndexCode(11006);
		compareData[4].setStockNo("2002");
		compareData[4].setValue(56.25);

		compareData[5] = new TaiwanDataPolarisIndexesValues();
		compareData[5].setDate(950216);
		compareData[5].setIndexCode(11006);
		compareData[5].setStockNo("2002");
		compareData[5].setValue(75);

		compareData[6] = new TaiwanDataPolarisIndexesValues();
		compareData[6].setDate(950217);
		compareData[6].setIndexCode(11006);
		compareData[6].setStockNo("2002");
		compareData[6].setValue(62.5);

		compareData[7] = new TaiwanDataPolarisIndexesValues();
		compareData[7].setDate(950220);
		compareData[7].setIndexCode(11006);
		compareData[7].setStockNo("2002");
		compareData[7].setValue(100);

		compareData[8] = new TaiwanDataPolarisIndexesValues();
		compareData[8].setDate(950221);
		compareData[8].setIndexCode(11006);
		compareData[8].setStockNo("2002");
		compareData[8].setValue(64);

		compareData[9] = new TaiwanDataPolarisIndexesValues();
		compareData[9].setDate(950222);
		compareData[9].setIndexCode(11006);
		compareData[9].setStockNo("2002");
		compareData[9].setValue(52.17);

		compareData[10] = new TaiwanDataPolarisIndexesValues();
		compareData[10].setDate(950223);
		compareData[10].setIndexCode(11006);
		compareData[10].setStockNo("2002");
		compareData[10].setValue(60.87);
	}

	@Test
	public void calculateRWMSTest() {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = IndexRWMS.calculateRWMS(sd, 6);
		if (indexData != null) {
			for (int i = 0; i < indexData.length; i++) {
				assertEquals(compareData[i].getValue(), indexData[i].getValue(), 0.01);
			}
		}
	}
}
