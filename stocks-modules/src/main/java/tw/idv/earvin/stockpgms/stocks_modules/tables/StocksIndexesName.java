package tw.idv.earvin.stockpgms.stocks_modules.tables;

public class StocksIndexesName {
	private long indexCode;
	private String indexName;
	private String description;

	public long getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(long v) {
		indexCode = v;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String v) {
		indexName = v;
	}

	public String getDescription() {
		return description;
	}

	public void setDescirption(String v) {
		description = v;
	}

	// TODO
	public static long getIndexCode(String indexName) {
		long indexCode = 0;
		String sql = "SELECT INDEX_CODE FROM STOCK_INDEXES_NAME WHERE INDEX_NAME = ? ";

		return indexCode;
	}
}
