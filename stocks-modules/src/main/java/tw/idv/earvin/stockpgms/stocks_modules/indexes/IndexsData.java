package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.util.HashMap;

import tw.idv.earvin.stockpgms.javaswing.learning.TestReadTxtFile;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

/**
 * Copy from IndexData // 2018.01.30
 * 
 * @author earvin
 *
 */
public class IndexsData {
	private String stockNo;
	private HashMap<String, TaiwanDataPolarisIndexesValues[]> indexValues = new HashMap<String, TaiwanDataPolarisIndexesValues[]>();

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String v) {
		stockNo = v;
	}

	public TaiwanDataPolarisIndexesValues[] getIndexValues(String key) {
		return indexValues.get(key);
	}

	public void setIndexValues(String key, TaiwanDataPolarisIndexesValues[] values) {
		indexValues.put(key, values);
	}

	public void printAllData() {
		System.out.println("=== [Display printAllData() Object START] === ");
		System.out.println("STOCK_NO = " + this.getStockNo());
		
		for (Object key : indexValues.keySet()) {
//            System.out.println(key + " : " + indexValues.get(key));
            printDataByKey((String)key);
		}
		System.out.println("=== [Display printAllData() Object END] === ");
	}

	public void printDataByKey(String key) {
		TaiwanDataPolarisIndexesValues[] mapValues = getIndexValues(key);
		if (mapValues != null) {
			for (int i = 0; i < mapValues.length; i++) {
				System.out.println("第 " + i + " 筆:" + mapValues[i].getIndexCode() + ", " + mapValues[i].getValue());
			}			
		} else {
			System.out.println("找不到資料!!!!");
		}
	}

	public static void main(String[] args) {
		IndexsData indexsData = new IndexsData();
		indexsData.setStockNo("2002");

		StocksData[] sd = TestReadTxtFile.getStocksData();
		for (int i = 0; i <= 240; i++) {
			if (i == 5 || i == 20 || i == 60 || i == 120 || i == 240) {
				TaiwanDataPolarisIndexesValues[] indexMAP = IndexMAP.calculateMAP(sd, i);
				indexsData.setIndexValues(String.valueOf(1000 + i), indexMAP);
			}
		}
//		indexsData.printDataByKey("1060");
		indexsData.printAllData();
	}
}
