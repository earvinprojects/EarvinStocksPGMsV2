package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tw.idv.earvin.stockpgms.stocks_modules.tables.StocksIndexesName;
import tw.idv.earvin.stockpgms.stocks_modules.tables.TaiwanDataPolarisIndexesValues;

public class IndexRWMS {

	/**
	 * 計算RWMS技術指標
	 * 
	 * @param sd
	 * @param indexDay
	 * @return
	 */
	public static TaiwanDataPolarisIndexesValues[] calculateRWMS(StocksData[] sd, int indexDay) {
		TaiwanDataPolarisIndexesValues[] indexData = null;

		long indexCode = StocksIndexesName.getIndexCode("RWMS_" + indexDay);
		System.out.println("[IndexRWMS.calculateRWMS(...)] -- IndexCode(RWMS, " + indexDay + ") = " + indexCode);
		
		if (sd.length > 0) {
			indexData = new TaiwanDataPolarisIndexesValues[sd.length];
			// Calculate the RWMS index
			int i = 0, j = 0;
			double maxValue, minValue, wmsValue;

			while (j < sd.length) {
				maxValue = -1;
				minValue = 99999999;
				if (j < indexDay) {
					for (i = 0; i <= j; i++) {
						if (maxValue < sd[i].getHighPrice()) {
							maxValue = sd[i].getHighPrice();
						}
						if (minValue > sd[i].getLowPrice()) {
							minValue = sd[i].getLowPrice();
						}
					}
				} else {
					for (i = j; i > (j - indexDay); i--) {
						if (maxValue < sd[i].getHighPrice()) {
							maxValue = sd[i].getHighPrice();
						}
						if (minValue > sd[i].getLowPrice()) {
							minValue = sd[i].getLowPrice();
						}
					}
				}
				if (maxValue != minValue) {
					wmsValue = (maxValue - sd[j].getEndPrice()) / (maxValue - minValue) * 100;
				} else {
					wmsValue = 50;
				}

				System.out.println(j + ", maxValue= " + maxValue + ", minValue= " + minValue + " -- " + sd[j].printData() + " -- the rwms value = " + (100 - wmsValue));
				indexData[j] = new TaiwanDataPolarisIndexesValues();
				indexData[j].setDate(sd[i].getDate());
				indexData[j].setIndexCode(indexCode);
				indexData[j].setStockNo(sd[i].getStockNo());
				indexData[j].setValue(100 - wmsValue);

				j = j + 1;
			}
		}
		return indexData;
	}

	// TODO 新增 or 異動RWMS技術指標資料(TAIWAN_DATA_POLARIS_INDEXES_VALUES)
	public void updateRWMS(TaiwanDataPolarisIndexesValues[] indexData) {
	}

	/**
	 * Calculate the average value of RWMS and insert to DB(table :
	 * TAIWAN_DATA_POLARIS_INDEXES_VALUES).
	 * 
	 * @param stockNo
	 *            股票代號
	 */
	public static StocksData[] CalculateRWMSAndInsertToDB(String stockNo, int indexDay) {
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
			System.out.println("RWMS指標作業開始 -- 處理股票代號= " + stockNo + ", 指標天數= " + indexDay);
			long indexCode = StocksIndexesName.getIndexCode("RWMS_" + indexDay);
			// 取得筆數
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/stocksdb", "root", "lin32ledi");
			PreparedStatement pstmt = conn.prepareStatement(SQL_GET_TAIWAN_DATA_POLARIS_COUNTS_BY_STOCK_NO);
			pstmt.setString(1, stockNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				counts = rs.getInt("COUNTS");
			}
			System.out.println("本次計算資料總筆數為 " + counts);
			if (counts > 0) {
				// -- Generate StockData object ---------------------------
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
				// -- Calculate the RWMS -----------------------------------------
//				TaiwanDataPolarisIndexesValues[] indexData = calculateRWMS(sd, indexDay); -- 2017.09.08 要處理…

				int i = 0, j = 0;
				while (j <= sd.length) {
					double rwmsValue = 0, wmsValue = 0;
					double maxValue = -1, minValue = 99999999;
					if (j <= indexDay) {
						for (i = 1; i < j; i++) {
							if (maxValue < sd[i].getHighPrice()) {
								maxValue = sd[i].getHighPrice();
							}
							if (minValue > sd[i].getLowPrice()) {
								minValue = sd[i].getLowPrice();
							}
						}
					} else {
						for (i = j; i < (j - indexDay); i--) {
							if (maxValue < sd[i].getHighPrice()) {
								maxValue = sd[i].getHighPrice();
							}
							if (minValue > sd[i].getLowPrice()) {
								minValue = sd[i].getLowPrice();
							}
						}
					}
					if (maxValue != minValue) {
						wmsValue = (maxValue - sd[j].getEndPrice()) / (maxValue - minValue) * 100;
					} else {
						wmsValue = 50;
					}
					rwmsValue = 100 - wmsValue;
					System.out.println(sd[j].printData() + " -- the rwms value = " + rwmsValue);

					// -- 將計算結果寫入 TAIWAN_DATA_POLARIS_INDEXES_VALUES ------
					pstmt = conn.prepareStatement(SQL_QUERY_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK);
					pstmt.setString(1, sd[i].getStockNo());
					pstmt.setLong(2, indexCode);
					pstmt.setLong(3, sd[i].getDate());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						// update
						pstmt = conn.prepareStatement(SQL_UPDATE_TAIWAN_DATA_POLARIS_INDEXES_VALUES_BY_PK);
						pstmt.setDouble(1, rwmsValue);
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
						pstmt.setDouble(4, rwmsValue);
						insertCount += 1;
					}
					pstmt.executeUpdate();
					j = j + 1;
				}
			}
			pstmt.close();
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("RWMS指標作業完成 -- 處理股票代號= " + stockNo + ", 指標天數= " + indexDay + ", 總筆數= " + counts + ", 新增筆數= "
				+ insertCount + ", 更新筆數= " + updateCount);
		return sd;
	}

	/**
	 * TODO 更新日期：2017.08.21 新增技術指標的值到：TAIWAN_DATA_POLARIS_INDEXES_VALUES
	 * 
	 * @param sd
	 */
	public void insertRWMS(StocksData[] sd) {
		TaiwanDataPolarisIndexesValues[] indexData = calculateRWMS(sd, 5);
		for (int i = 0; i < indexData.length; i++) {
			indexData[i].insert();
		}
		
	}

	public static void main(String[] args) {
		StocksData[] sd = StocksData.getStocksDataByStockNoAndDateBetween(950201, 951231, "2002");
		TaiwanDataPolarisIndexesValues[] indexData = calculateRWMS(sd,5);
		for (int i = 0; i <indexData.length; i++) {
//			System.out.println("Date= " + indexData[i].getDate() + ", index_code= " + indexData[i].getIndexCode() + ", stockno_no= " + indexData[i].getStockNo() + ", index_value= " + indexData[i].getValue());
			System.out.println(i + ", value= " + indexData[i].print());
//			indexData[i].insert();
			// insert TaiwanData_Polaris_Indexes_Values
		}

//		sd = CalculateRWMSAndInsertToDB("2349", 5);
//		sd = CalculateRWMSAndInsertToDB("2349", 30);
//		sd = CalculateRWMSAndInsertToDB("2349", 90);
//		sd = CalculateRWMSAndInsertToDB("2349", 180);
//		sd = CalculateRWMSAndInsertToDB("2349", 360);
	}
}
