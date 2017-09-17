package tw.idv.earvin.stockpgms.stocks_modules.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import tw.idv.earvin.stockpgms.stocks_modules.db.DatabaseImp;

public class TaiwanDataPolarisIndexesValues {
	private String stockNo;
	private long indexCode;
	private long date;
	private double value;

	private String SQL_QUERY_BY_PK = "SELECT * FROM TAIWAN_DATA_POLARIS_INDEXES_VALUES WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
	private String SQL_UPDATE_BY_PK = "UPDATE TAIWAN_DATA_POLARIS_INDEXES_VALUES SET VALUE = ? WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
	private String SQL_INSERT = "INSERT INTO TAIWAN_DATA_POLARIS_INDEXES_VALUES (STOCK_NO, INDEX_CODE, DATE, VALUE) VALUES ( ?, ?, ?, ?) ";

	
	
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
	public Vector<TaiwanDataPolarisIndexesValues> queryByPK() {
		Vector<TaiwanDataPolarisIndexesValues> vc = new Vector<TaiwanDataPolarisIndexesValues>();
		
		Connection con = DatabaseImp.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_QUERY_BY_PK);
			pstmt.setString(1, getStockNo());
			pstmt.setLong(2,getIndexCode());
			pstmt.setLong(3, getDate());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				vc.add(this);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vc;
	}

	// TODO 20170914 如何以物件方式儲存資料?? 找實作方式!!
	public int update() {
		int result = 0;
		Vector<TaiwanDataPolarisIndexesValues> vc = new Vector<TaiwanDataPolarisIndexesValues>();
	
		if (vc.size() > 0) {
			// 更新資料
			
		} else {
			// 新增資料
			
		}

		return result;
	}

	public static void main(String[] args) {
		//20170917 TODO
		TaiwanDataPolarisIndexesValues theData = new TaiwanDataPolarisIndexesValues();
		theData.setDate(1060821);
		theData.setIndexCode(1005);
		theData.setStockNo("2349");
		theData.setValue(0);

		Vector<TaiwanDataPolarisIndexesValues> vc = queryByPK();
		
	}

}
