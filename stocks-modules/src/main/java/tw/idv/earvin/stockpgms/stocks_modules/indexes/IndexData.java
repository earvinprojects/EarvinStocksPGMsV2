package tw.idv.earvin.stockpgms.stocks_modules.indexes;

public class IndexData {
	private String stockNo;
	private long indexCode;
	private long tradeDate;
	private double value;
//	private Connection con = null;
	
	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String v) {
		stockNo = v;
	}
	public long getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(long v) {
		indexCode = v;
	}
	public long getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(long v) {
		tradeDate = v;
	}
	
	
	
	public void insert(IndexData v) {
		
	}
	
	public void delete(IndexData v) {
		
	}
	
	public static void main(String[] args) {

	}

}
