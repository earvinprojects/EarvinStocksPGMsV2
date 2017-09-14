package tw.idv.earvin.stockpgms.stocks_modules.tables;

import java.util.Vector;

public class TaiwanDataPolarisIndexesValues {
	private String stockNo;
	private long indexCode;
	private long date;
	private double value;
	
	

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

	public long getDate() {
		return date;
	}

	public void setDate(long v) {
		date = v;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double v) {
		value = v;
	}

	// TODO
	public Vector queryByPK() {
		// Vector轉Array
		// String[] arr = (String[]) tmp.toAarray(new String[0]);
		Vector vc = new Vector();
		
		return vc;
	}

	// TODO 20170914 如何以物件方式儲存資料?? 找實作方式!!
	public int update() {
		int result = 0;

		return result;
	}

	public static void main(String[] args) {

	}

}
