package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexKDTest {
	private Vector<TaiwanDataPolarisIndexesValues[]> CompareVec = new Vector<TaiwanDataPolarisIndexesValues[]>();
	
	@Before
	public void instantiate() throws Exception {
		TaiwanDataPolarisIndexesValues[] compareKIndexData = null;
		TaiwanDataPolarisIndexesValues[] compareDIndexData = null;
		compareKIndexData = new TaiwanDataPolarisIndexesValues[11];
		compareKIndexData[0] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[0].setDate(950209);
		compareKIndexData[0].setIndexCode(6009);
		compareKIndexData[0].setStockNo("2002");
		compareKIndexData[0].setValue(43.33);

		compareKIndexData[1] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[1].setDate(950210);
		compareKIndexData[1].setIndexCode(6009);
		compareKIndexData[1].setStockNo("2002");
		compareKIndexData[1].setValue(39.15);

		compareKIndexData[2] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[2].setDate(950213);
		compareKIndexData[2].setIndexCode(6009);
		compareKIndexData[2].setStockNo("2002");
		compareKIndexData[2].setValue(34.43);

		compareKIndexData[3] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[3].setDate(950214);
		compareKIndexData[3].setIndexCode(6009);
		compareKIndexData[3].setStockNo("2002");
		compareKIndexData[3].setValue(47.95);

		compareKIndexData[4] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[4].setDate(950215);
		compareKIndexData[4].setIndexCode(6009);
		compareKIndexData[4].setStockNo("2002");
		compareKIndexData[4].setValue(50.72);

		compareKIndexData[5] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[5].setDate(950216);
		compareKIndexData[5].setIndexCode(6009);
		compareKIndexData[5].setStockNo("2002");
		compareKIndexData[5].setValue(58.81);

		compareKIndexData[6] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[6].setDate(950217);
		compareKIndexData[6].setIndexCode(6009);
		compareKIndexData[6].setStockNo("2002");
		compareKIndexData[6].setValue(60.04);

		compareKIndexData[7] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[7].setDate(950220);
		compareKIndexData[7].setIndexCode(6009);
		compareKIndexData[7].setStockNo("2002");
		compareKIndexData[7].setValue(73.36);

		compareKIndexData[8] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[8].setDate(950221);
		compareKIndexData[8].setIndexCode(6009);
		compareKIndexData[8].setStockNo("2002");
		compareKIndexData[8].setValue(71.9);

		compareKIndexData[9] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[9].setDate(950222);
		compareKIndexData[9].setIndexCode(6009);
		compareKIndexData[9].setStockNo("2002");
		compareKIndexData[9].setValue(68.62);

		compareKIndexData[10] = new TaiwanDataPolarisIndexesValues();
		compareKIndexData[10].setDate(950223);
		compareKIndexData[10].setIndexCode(6009);
		compareKIndexData[10].setStockNo("2002");
		compareKIndexData[10].setValue(68.74);
		CompareVec.add(compareKIndexData);

		compareDIndexData = new TaiwanDataPolarisIndexesValues[11];
		compareDIndexData[0] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[0].setDate(950209);
		compareDIndexData[0].setIndexCode(7009);
		compareDIndexData[0].setStockNo("2002");
		compareDIndexData[0].setValue(47.78);

		compareDIndexData[1] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[1].setDate(950210);
		compareDIndexData[1].setIndexCode(7009);
		compareDIndexData[1].setStockNo("2002");
		compareDIndexData[1].setValue(44.9);

		compareDIndexData[2] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[2].setDate(950213);
		compareDIndexData[2].setIndexCode(7009);
		compareDIndexData[2].setStockNo("2002");
		compareDIndexData[2].setValue(41.41);

		compareDIndexData[3] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[3].setDate(950214);
		compareDIndexData[3].setIndexCode(7009);
		compareDIndexData[3].setStockNo("2002");
		compareDIndexData[3].setValue(43.59);

		compareDIndexData[4] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[4].setDate(950215);
		compareDIndexData[4].setIndexCode(7009);
		compareDIndexData[4].setStockNo("2002");
		compareDIndexData[4].setValue(45.97);

		compareDIndexData[5] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[5].setDate(950216);
		compareDIndexData[5].setIndexCode(7009);
		compareDIndexData[5].setStockNo("2002");
		compareDIndexData[5].setValue(50.25);

		compareDIndexData[6] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[6].setDate(950217);
		compareDIndexData[6].setIndexCode(7009);
		compareDIndexData[6].setStockNo("2002");
		compareDIndexData[6].setValue(53.51);

		compareDIndexData[7] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[7].setDate(950220);
		compareDIndexData[7].setIndexCode(7009);
		compareDIndexData[7].setStockNo("2002");
		compareDIndexData[7].setValue(60.13);

		compareDIndexData[8] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[8].setDate(950221);
		compareDIndexData[8].setIndexCode(7009);
		compareDIndexData[8].setStockNo("2002");
		compareDIndexData[8].setValue(64.05);

		compareDIndexData[9] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[9].setDate(950222);
		compareDIndexData[9].setIndexCode(7009);
		compareDIndexData[9].setStockNo("2002");
		compareDIndexData[9].setValue(65.57);

		compareDIndexData[10] = new TaiwanDataPolarisIndexesValues();
		compareDIndexData[10].setDate(950223);
		compareDIndexData[10].setIndexCode(7009);
		compareDIndexData[10].setStockNo("2002");
		compareDIndexData[10].setValue(66.63);
		CompareVec.add(compareDIndexData);
	}

	@Test
	public void calculateKDTest() {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			TaiwanDataPolarisIndexesValues[] indexData = vec.get(i);
			TaiwanDataPolarisIndexesValues[] compareData = CompareVec.get(i);
			if ((indexData != null) && (indexData[0].getIndexCode() == compareData[0].getIndexCode())) {
				for (int j = 0; j < indexData.length; j++) {
					assertEquals(compareData[j].getValue(), indexData[j].getValue(), 0.01);
				}
			}
		}
	}
}
