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
	private static Connection con = null; 

	private static String SQL_QUERY_BY_PK = "SELECT * FROM TAIWAN_DATA_POLARIS_INDEXES_VALUES WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
	private static String SQL_UPDATE_BY_PK = "UPDATE TAIWAN_DATA_POLARIS_INDEXES_VALUES SET VALUE = ? WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
	private static String SQL_INSERT = "INSERT INTO TAIWAN_DATA_POLARIS_INDEXES_VALUES (STOCK_NO, INDEX_CODE, DATE, VALUE) VALUES ( ?, ?, ?, ?) ";

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

	public int update() {
		System.out.println("[Call TaiwanDataPolarisIndexesValues vo.update()] -- ");
		return update(this);
	}

	public int insert() {
		System.out.println("[Call TaiwanDataPolarisIndexesValues vo.insert()] -- ");
		return insert(this);
	}

	// -- Static Functions ---------------------------------------------------//

	public static boolean hasData(TaiwanDataPolarisIndexesValues vo) {
//		Connection con = DatabaseImp.getConnection();
		if (con == null) {
			con = DatabaseImp.getConnection();
		}	
		return hasData(con, vo);
	}

	public static boolean hasData(Connection con, TaiwanDataPolarisIndexesValues vo) {
		boolean hasData = false;
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_QUERY_BY_PK);
			pstmt.setString(1, vo.getStockNo());
			pstmt.setLong(2, vo.getIndexCode());
			pstmt.setLong(3, vo.getDate());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				hasData = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hasData;
	}

	public static int update(TaiwanDataPolarisIndexesValues vo) {
//		Connection con = DatabaseImp.getConnection();
		if (con == null) {
			con = DatabaseImp.getConnection();
		}
		return update(con, vo);
	}

	public static int update(Connection con, TaiwanDataPolarisIndexesValues vo) {
		System.out.println("[Call TaiwanDataPolarisIndexesValues.update()] -- ");
		int result = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_BY_PK);
			pstmt.setDouble(1, vo.getValue());
			pstmt.setString(2, vo.getStockNo());
			pstmt.setLong(3, vo.getIndexCode());
			pstmt.setLong(4, vo.getDate());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int insert(TaiwanDataPolarisIndexesValues vo) {
//		Connection con = DatabaseImp.getConnection();
		if (con == null) {
			con = DatabaseImp.getConnection();
		}
		return insert(con, vo);
	}

	public static int insert(Connection con, TaiwanDataPolarisIndexesValues vo) {
		System.out.println("[Call TaiwanDataPolarisIndexesValues.insert()] -- ");
		int result = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getStockNo());
			pstmt.setLong(2, vo.getIndexCode());
			pstmt.setLong(3, vo.getDate());
			pstmt.setDouble(4, vo.getValue());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String print(TaiwanDataPolarisIndexesValues vo) {
		String thePrintString = "";
		thePrintString = "StockNo= " + vo.getStockNo() + ", IndexCode= " + vo.getIndexCode() + ", Date= " + vo.getDate()
				+ ", Value= " + vo.getValue();
		return thePrintString;
	}

	public static void main(String[] args) {
		TaiwanDataPolarisIndexesValues theVO = new TaiwanDataPolarisIndexesValues();
		theVO.setDate(1070101);
		theVO.setIndexCode(1005);
		theVO.setStockNo("2349");
		theVO.setValue(299.99);
//		System.out.println("The value is " + theVO.insert());
		System.out.println("The value is " + theVO.update());
		// System.out.println("The value is " +
		// TaiwanDataPolarisIndexesValues.insert(theVO));
//		System.out.println("The value is " + TaiwanDataPolarisIndexesValues.update(theVO));
//		System.out.println("The value is " + TaiwanDataPolarisIndexesValues.print(theVO));
	}
}
