package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;

public class StocksDataDAO {
	private String SQL1 = "SELECT * FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY LENGTH(DATE) , DATE";
	
/*	public StocksData[] GetStocksData(String stockNo) {
		
		return StocksData;
	}
*/	
/*	public StocksData[] GetStocksData(String beginData, String endData) {
		return StocksData;

	}
*/	
	public static void main(String[] args) {
		
	}
}
