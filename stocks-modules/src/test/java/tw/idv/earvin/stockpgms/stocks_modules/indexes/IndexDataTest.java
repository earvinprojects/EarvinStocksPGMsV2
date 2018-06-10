package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Vector;

import org.junit.Test;

import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexDataTest {

	@Test
	public void generateIndexDataTest() {
		IndexData indexData = new IndexData();
		indexData.setStockNo("2002");

		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, 5);
		indexData.setIndexValues(indexMAP);
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			indexData.setIndexValues(vec.get(i));
		}
		indexData.printData();
	}

}
