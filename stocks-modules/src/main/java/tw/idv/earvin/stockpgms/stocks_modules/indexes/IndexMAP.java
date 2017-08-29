package tw.idv.earvin.stockpgms.stocks_modules.indexes;

public class IndexMAP {
	
	/**
	 * Calculate the average value of volume and main value.
	 * @param sd 股票資料
	 * @param indexDay 技術指標天數
	 */
	public void calculateMAP(StocksData[] sd, int indexDay) {
		for (int i = 0; i < sd.length; i++) {
			double average = 0;
			if (i < indexDay) {
				for (int j = 0; j <= i; j++) {
					average += sd[i-j].getEndPrice();
				}
				average /= (i+1);				
			} else {
				for (int j = 0; j < indexDay; j++) {
					average += sd[i-j].getEndPrice();
				}
				average /= indexDay;				
			}
			System.out.println("the average[" + i + "]= " + average);
		}
	}
	
	/**
	 * 更新日期：2017.08.21
	 * 新增技術指標的值到：TAIWAN_DATA_POLARIS_INDEXES_VALUES
	 * @param sd
	 */
	public void insertMAP(StocksData[] sd) {
		
	}
}
