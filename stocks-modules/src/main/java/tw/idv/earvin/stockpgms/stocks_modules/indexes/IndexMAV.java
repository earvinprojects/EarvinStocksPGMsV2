package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexMAV {


	// TODO 20170906 取得股票資料(data from TAIWAN_DATA_POLARIS)
	private StocksData[] getStocksData(String stockNo) {
		StocksData[] sd = null;
		
		return sd;
	}
	
	public TaiwanDataPolarisIndexesValues[] calculateMAV(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;
		
		if (sd.length > 0) {
			indexData= new TaiwanDataPolarisIndexesValues[sd.length];
		}
		// Calculate the MAV
		for (int i = 0; i < sd.length; i++) {
			double average = 0;
			if (i < indexDay) {
				for (int j = 0; j <= i; j++) {
					average += sd[i - j].getVolume();
				}
				average /= (i + 1);
			} else {
				for (int j = 0; j < indexDay; j++) {
					average += sd[i - j].getVolume();
				}
				average /= indexDay;
			}
			System.out.println(sd[i].printData() + " -- the average= " + average);
			indexData[i] = new TaiwanDataPolarisIndexesValues();
			indexData[i].setDate(sd[i].getDate());
			indexData[i].setIndexCode(1001);
			indexData[i].setStockNo(sd[i].getStockNo());
			indexData[i].setValue(average);
		}
		
		return indexData;
	}
	
	// TODO 20170906 計算MAP技術指標資料
	public StocksData[] calculateMAV(String stockNo, int indexDay) {
		StocksData[] sd = getStocksData(stockNo);
		
		return sd;
	}
	
	// TODO 新增  or 異動MAP技術指標資料(TAIWAN_DATA_POLARIS_INDEXES_VALUES)
	public void updateMAV(StocksData[] sd) {
		
	}
	
	/**
	 * Calculate the average value of MAP and insert to DB(table : TAIWAN_DATA_POLARIS_INDEXES_VALUES).
	 * 
	 * @param stockNo
	 *            股票代號
	 */
	public static StocksData[] CalculateMAVAndInsertToDB(String stockNo, int indexDay) {
		String SQL_GET_TAIWAN_DATA_POLARIS_COUNTS_BY_STOCK_NO = "SELECT COUNT(*) AS COUNTS FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ";
		String SQL_QUERY_TAIWAN_DATA_POLARIS_BY_PK = "SELECT STOCK_NO, DATE, START_PRICE, HIGH_PRICE, LOW_PRICE, END_PRICE, VOLUME FROM TAIWAN_DATA_POLARIS WHERE STOCK_NO = ? ORDER BY DATE ";
		String SQL_QUERY_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK = "SELECT * FROM TAIWAN_DATA_POLARIS_INDEXES_VALUES WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
		String SQL_UPDATE_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK = "UPDATE TAIWAN_DATA_POLARIS_INDEXES_VALUES SET VALUE = ? WHERE STOCK_NO = ? AND INDEX_CODE = ? AND DATE = ? ";
		String SQL_INSERT_TAIWAN_DATA_POLARIS_INDEXES_VALUES = "INSERT INTO TAIWAN_DATA_POLARIS_INDEXES_VALUES (STOCK_NO, INDEX_CODE, DATE, VALUE) VALUES (?, ?, ?, ?) ";

		int counts = 0;
		int updateCount = 0;
		int insertCount = 0;
		StocksData[] sd = null;

		try {
			long indexCode = StocksIndexesName.getIndexCode("MAV_" + indexDay);
			// 取得筆數
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL_GET_TAIWAN_DATA_POLARIS_COUNTS_BY_STOCK_NO);
			pstmt.setString(1, stockNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				counts = rs.getInt("COUNTS");
			}
			System.out.println("作業開始，本次計算資料總筆數為 " + counts);
			// Generate StockData object
			sd = new StocksData[counts];
			pstmt = conn.prepareStatement(SQL_QUERY_TAIWAN_DATA_POLARIS_BY_PK);
			pstmt.setString(1, stockNo);
			rs = pstmt.executeQuery();
			for (int i = 0; i < counts; i++) {
				rs.next();
				sd[i] = new StocksData();
				sd[i].setStockNo(rs.getString("STOCK_NO"));
				sd[i].setDate(rs.getInt("DATE"));
				sd[i].setStartPrice(rs.getDouble("START_PRICE"));
				sd[i].setHighPrice(rs.getDouble("HIGH_PRICE"));
				sd[i].setLowPrice(rs.getDouble("LOW_PRICE"));
				sd[i].setEndPrice(rs.getDouble("END_PRICE"));
				sd[i].setVolume(rs.getDouble("VOLUME"));
			}
			// Calculate the MAV
			for (int i = 0; i < sd.length; i++) {
				double average = 0;
				if (i < indexDay) {
					for (int j = 0; j <= i; j++) {
						average += sd[i - j].getVolume();
					}
					average /= (i + 1);
				} else {
					for (int j = 0; j < indexDay; j++) {
						average += sd[i - j].getVolume();
					}
					average /= indexDay;
				}
				System.out.println(sd[i].printData() + " -- the average= " + average);

				// 將計算結果寫入 TAIWAN_DATA_POLARIS_INDEXES_VALUES
				pstmt = conn.prepareStatement(SQL_QUERY_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK);
				pstmt.setString(1, sd[i].getStockNo());
				pstmt.setLong(2, indexCode);
				pstmt.setLong(3, sd[i].getDate());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					// update
					pstmt = conn.prepareStatement(SQL_UPDATE_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK);
					pstmt.setDouble(1, average);
					pstmt.setString(2, sd[i].getStockNo());
					pstmt.setLong(3, indexCode);
					pstmt.setLong(4, sd[i].getDate());
					updateCount += 1;
				} else {
					// insert
					pstmt = conn.prepareStatement(SQL_INSERT_TAIWAN_DATA_POLARIS_INDEXES_VALUES);
					pstmt.setString(1, sd[i].getStockNo());
					pstmt.setLong(2, indexCode);
					pstmt.setLong(3, sd[i].getDate());
					pstmt.setDouble(4, average);
					insertCount += 1;
				}
				pstmt.executeUpdate();
			}
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("MAV 指標作業完成 -- 處理股票代號= " + stockNo + ", 總筆數= " + counts + ", 新增筆數= " + insertCount + ", 更新筆數= "
				+ updateCount);
		return sd;
	}

	/**
	 * TODO 更新日期：2017.08.21 新增技術指標的值到：TAIWAN_DATA_POLARIS_INDEXES_VALUES
	 * 
	 * @param sd
	 */
	public void insertMAP(StocksData[] sd) {

	}

	/**
	 * 計算recordset筆數
	 * 
	 * @param resultSet
	 * @return int 資料筆數
	 */
	public static int getResultSetRowCount(ResultSet resultSet) {
		int size = 0;
		try {
			resultSet.last();
			size = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (Exception ex) {
			return 0;
		}
		return size;
	}

	public static void main(String[] args) {
		StocksData[] sd = null;
		sd = CalculateMAVAndInsertToDB("2349", 5);
		sd = CalculateMAVAndInsertToDB("2349", 30);
		sd = CalculateMAVAndInsertToDB("2349", 90);
		sd = CalculateMAVAndInsertToDB("2349", 180);
		sd = CalculateMAVAndInsertToDB("2349", 360);
	}
}