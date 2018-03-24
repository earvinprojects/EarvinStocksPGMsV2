package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.Arrays;
import java.util.HashMap;
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
	private HashMap<String,TaiwanDataPolarisIndexesValues[]> indexValuesHM = new HashMap<String,TaiwanDataPolarisIndexesValues[]>();

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
	
	public void printDataHM() {
		System.out.println("=== [Display IndexData(HM) Object START] === ");
		System.out.println("STOCK_NO = " + this.getStockNo());
		for (int i = 0; i < indexValues.size(); i++) {
			TaiwanDataPolarisIndexesValues[] id = indexValues.get(i);
			for (int j = 0; j < id.length; j++) {
				System.out.println(id[j].getDate() + ", " + id[j].getIndexCode() + ", " + id[j].getValue());
			}
		}
		System.out.println("=== [Display IndexData(HM) Object END] === ");		
	}
	
	public static void main(String[] args) {
		IndexsData indexsData = new IndexsData();
		indexsData.setStockNo("2002");
/**
//		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950209, 950223, "2002");
		StocksData[] sd = TestReadTxtFile.getStocksData();
		TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, 5);
		indexData.setIndexValues(indexMAP);
		Vector<TaiwanDataPolarisIndexesValues[]> vec = IndexKD.calculateKD(sd, 9);
		for (int i = 0; i < vec.size(); i++) {
			indexData.setIndexValues(vec.get(i));
		}
		indexData.printData();
**/
		// 20180322 改存至HashMap
		HashMap<String,TaiwanDataPolarisIndexesValues[]> indexsDataHM = new HashMap<String,TaiwanDataPolarisIndexesValues[]>();
		StocksData[] sd = TestReadTxtFile.getStocksData();
		for (int i = 0; i <= 240; i++) {
			if (i == 5 || i == 20 || i == 60 || i == 120 || i == 240) {
				TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, i);
				indexsDataHM.put(String.valueOf(1000+i), indexMAP);
			}
		}
	}
}
