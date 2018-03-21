package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Arrays;
import java.util.Vector;

import tw.idv.earvin.stockpgms.javaswing.learning.TestReadTxtFile;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

/**
 * Copy from IndexData // 2018.01.30
 * @author earvin
 *
 */
public class IndexsData {
	private String stockNo;
	private Vector<TaiwanDataPolarisIndexesValues[]> indexValues = new Vector<TaiwanDataPolarisIndexesValues[]>();

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String v) {
		stockNo = v;
	}

	public Vector<TaiwanDataPolarisIndexesValues[]> getIndexValues() {
		return indexValues;
	}

	public void setIndexValues(TaiwanDataPolarisIndexesValues[] v) {
		TaiwanDataPolarisIndexesValues[] i = Arrays.copyOf(v, v.length);;
		indexValues.add(i);
	}
	
	public void printData() {
		System.out.println("=== [Display IndexData Object START] === ");
		System.out.println("STOCK_NO = " + this.getStockNo());
		for (int i = 0; i < indexValues.size(); i++) {
			TaiwanDataPolarisIndexesValues[] id = indexValues.get(i);
			for (int j = 0; j < id.length; j++) {
				System.out.println(id[j].getDate() + ", " + id[j].getIndexCode() + ", " + id[j].getValue());
			}
		}
		System.out.println("=== [Display IndexData Object END] === ");
	}
	
	public static void main(String[] args) {
		IndexData indexData = new IndexData();
		indexData.setStockNo("2002");

//		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		StocksData[] sd = TestReadTxtFile.getStocksData();
		TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, 5);
		indexData.setIndexValues(indexMAP);
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			indexData.setIndexValues(vec.get(i));
		}
		indexData.printData();
	}
}
