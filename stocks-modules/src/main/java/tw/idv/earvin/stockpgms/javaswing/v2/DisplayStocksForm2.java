package tw.idv.earvin.stockpgms.javaswing.v2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import tw.idv.earvin.stockpgms.javaswing.learning.TestReadTxtFile;
import tw.idv.earvin.stockpgms.javaswing.v1.EarvinStocksFrameTest;
import tw.idv.earvin.stockpgms.javaswing.v1.FrameData;
import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;
import javax.swing.JComponent;

public class DisplayStocksForm2 extends JComponent {
	/**
	 * 20180201 不知是要做啥用的？
	 */
	private static final long serialVersionUID = 1L;
	//------------------
	//-- 20180116 STR --
	//------------------
    /** 參考  ..\‧Dropbox\myStocksPGMs\V2.0\EarvinStocksFormSettings(20180131).xls的圖例會比較清楚 **/
	
	//-- 常數區 --------------------------------------------------------------------------------------------------
    final private double outerFrameUpperDistance = 10;
    final private double outerFrameBottomDistance = 25;
    final private double outerFrameLeftDistance = 50;
    final private double outerFrameRightDistance = 10;
    final private double infoFrameHighDistance = 30;
    final private double indexFrameWidthDistance = 150;
    final private int MAX_FRAME_COUNT = 10;
   
	Stroke strokeDottedLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {2, 2}, 0);
	Stroke stokeSolidLine = new BasicStroke(1);

    
    //-- 一般變數區 -----------------------------------------------------------------------------------------------
    private double mainFrameHighDistance = 0;
    private double mainFrameWidthDistance = 0;
    private double subFramesTotalHighDistance = 0;
    
    // 最外方框的左上角座標(同   股票資訊顯示區左上角座標)
    private double outerFrameStartX = outerFrameLeftDistance;
    private double outerFrameStartY = outerFrameUpperDistance;
    // 股票資訊顯示區左上角座標
    private double infoFrameStartX = outerFrameLeftDistance;
    private double infoFrameStartY = outerFrameUpperDistance;
    // K線圖的左上角座標
    private double mainFrameStartX = outerFrameLeftDistance;
    private double mainFrameStartY = outerFrameStartY + infoFrameHighDistance;
    // 右側的技術指標左上角座標
    private double indexFrameStartX = 0; // 視窗寬度 - outerFrameRightDistance - indexFrameWidthDistance
    private double indexFrameStartY = 0;
    // 技術指標區第一個方框左上角座標
    private double subFrameStartX = 0;
    private double subFrameStartY  = 0;

	private double kBarWidth = 10;		// K線圖的K-Bar預設寬度   
    private int startDisplayRecord = 0;	// 預設顯示於畫面上資料的起始位置
	private int endDisplayRecord = 0;	// 預設顯示於畫面上資料的結束位置
	public int frameCount = 5; // (暫定…) 定義最能開5個frame
	public FrameData frameData[] = new FrameData[MAX_FRAME_COUNT];	// 儲存開啟frame的相關資料
	StocksData[] stocksData = null;	// 股票資料
//	IndexsData[] indexsData = null; // 股票技術指標資料(20180131: 先不處理…)
	private int totalStocksCount = 0;	// 股票資料總筆數

	public DisplayStocksForm2() {
		super();
	}
	
	public DisplayStocksForm2(Object o) {
		frameCount = (int) o;
	}
	
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		setForeground(Color.BLUE);
		
    	stocksData = TestReadTxtFile.getData();
		totalStocksCount = stocksData.length;
    	endDisplayRecord = totalStocksCount;
    	// 計算要顯示的資料起、迄位置
		if (startDisplayRecord > 1) {
			startDisplayRecord = endDisplayRecord -  (int) (mainFrameWidthDistance / kBarWidth);
		} else {
			endDisplayRecord = startDisplayRecord + (int) (mainFrameWidthDistance / kBarWidth);
		}
//		System.out.println("startDisplayRecord= " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord + ", mainFrameWidthDistance= " + mainFrameWidthDistance + ", kBarWidth= " + kBarWidth);
		DrawStockForm(g, endDisplayRecord, "中鋼");
	}
	
	// 繪出股票圖
	// endIndex 結束日期
	// stockName 股票中文名稱
	public void DrawStockForm(Graphics g, int endIndex, String stockName) {
//		SetEachFrameHigh(7);	// 設定各個frame高度(目前併在 DrawOutlineOfFrames2D() 函式中)
		DrawOutlineOfFrames2D(g, frameCount);	// 繪製frame外框
		SetDisplayStartIndex();
//		System.out.println("[DrawStockForm()] -- startDisplayRecord= " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord + ", mainFrameHighDistance= " + mainFrameHighDistance + ", mainFrameWidthDistance= " + mainFrameWidthDistance + ", kBarWidth= " + kBarWidth);
		DrawStockFormByStockType(g, "日線", endDisplayRecord);
	}
	
	// 繪製frame外框 (Write OK, 20180119)
	// frameCount frame數量
	public void DrawOutlineOfFrames2D(Graphics g, int frameCount) {
		Graphics2D g2 = (Graphics2D) g;

		// 決定各個數值
		mainFrameWidthDistance = this.getWidth() - outerFrameLeftDistance - indexFrameWidthDistance - outerFrameRightDistance;
		// 顯示主要股票K線圖(佔可使用之Frame高度4/9)
		mainFrameHighDistance = (this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance - infoFrameHighDistance) * 4 / 9;
		//其它技術視窗(分享可使用之Frame高度 5/9)
		subFramesTotalHighDistance = this.getHeight() - outerFrameUpperDistance - infoFrameHighDistance - mainFrameHighDistance - outerFrameBottomDistance;

		// 儲存Main Frame相關資訊
		if (frameData[0] == null) {
			frameData[0] = new FrameData();
		}
		frameData[0].setX(mainFrameStartX);
		frameData[0].setY(mainFrameStartY);
		frameData[0].setHeight(mainFrameHighDistance);
		
		indexFrameStartX = outerFrameLeftDistance + mainFrameWidthDistance;
		indexFrameStartY = outerFrameUpperDistance + infoFrameHighDistance;
		subFrameStartX = mainFrameStartX;
		
		// 設定每一個subFrame(frameData索引值由1開始)的左上角座標
//		subFrameStartY = mainFrameStartY + mainFrameHighDistance;
		for (int i = 1; i <= frameCount; i++) {
			subFrameStartY = mainFrameStartY + mainFrameHighDistance + subFramesTotalHighDistance / frameCount * (i - 1);
			if (frameData[i] == null) {
				frameData[i] = new FrameData();
			}
			frameData[i].setX(subFrameStartX);
			frameData[i].setY(subFrameStartY);
		}

		// 外框(起點座標：outerFrameStartX，outerFrameStartY)
		Shape outerFrame = new Rectangle2D.Double(outerFrameStartX, outerFrameStartY, this.getWidth() - outerFrameLeftDistance - outerFrameRightDistance, this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance);
		g2.draw(outerFrame);
		// 上方股市資訊顯示區(起點座標：同外框)
		Shape infoFrame = new Rectangle2D.Double(infoFrameStartX, infoFrameStartY, (this.getWidth() - outerFrameLeftDistance - outerFrameRightDistance), infoFrameHighDistance);	
		g2.draw(infoFrame);
		// 右側邊框(顯示各Frame的技術指標資訊)
		Shape indexFrame = new Rectangle2D.Double(indexFrameStartX, indexFrameStartY, indexFrameWidthDistance, (this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance - infoFrameHighDistance));	
		g2.draw(indexFrame);
		// 主要股票k線圖的底線 (即為第一個subFrame的左上角座標)
		Point2D p1 = new Point2D.Double(frameData[1].getX(), frameData[1].getY());
		Point2D p2 = new Point2D.Double(indexFrameStartX, frameData[1].getY());
		Line2D KBottomLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.BLUE);
		g2.draw(KBottomLine);
		
		// 其它指標視窗的底線(最後一個frame的底線不用畫)
		System.out.println("[DrawOutlineOfFrames2D()] -- indexFrameStartX= " + indexFrameStartX);
		for (int i = 1; i <= frameCount; i++) {
			System.out.println("[DrawOutlineOfFrames2D()] -- Frame[" + i + "]= " + frameData[i].getX() + ", " + frameData[i].getY() + ", " + frameData[i].getHeight());
			frameData[i].setHeight(subFramesTotalHighDistance / frameCount);

			Point2D p3 = new Point2D.Double(frameData[i].getX(), frameData[i].getY());
			Point2D p4 = new Point2D.Double(indexFrameStartX, frameData[i].getY());			
			Line2D FramesBottomLine = new Line2D.Double(p3, p4);
			g2.setPaint(Color.BLUE);
			g2.draw(FramesBottomLine);
		}
	}
	
	// 記錄要顯示的資料起始位置(startDisplayRecord)
	public void SetDisplayStartIndex() {
		if ((endDisplayRecord - (mainFrameWidthDistance / kBarWidth)) >= 0) {
			startDisplayRecord = endDisplayRecord - (int) (mainFrameWidthDistance / kBarWidth);
		} else {
			startDisplayRecord = 0;
		}
	}
	
	// 繪製要顯示的線圖是 日線 OR 週線 OR 月線
	// stockType 日線 OR 週線 OR 月線
	// displayEndIndex
	public void DrawStockFormByStockType(Graphics g, String stockType, int displayEndIndex) {
/*	    If stockType = "日線" Then
 			// 根據每個frame所選擇要顯示的指標來繪製圖形
	        Call DrawFrameData(gudtStockDay, gudtIndexDay)
	        // Display each index values on right side
	        Call DrawStockIndexes(gudtStockDay, gudtIndexDay, gintDayIndex, displayEndIndex) 
	    ElseIf stockType = "週線" Then
	        Call DrawFrameData(gudtStockWeek, gudtIndexWeek)
	        Call DrawStockIndexes(gudtStockWeek, gudtIndexWeek, gintWeekIndex, displayEndIndex)
	    ElseIf stockType = "月線" Then
	        Call DrawFrameData(gudtStockMonth, gudtIndexMonth)
	        Call DrawStockIndexes(gudtStockMonth, gudtIndexMonth, gintMonthIndex, displayEndIndex)
	    End If
*/		
		if (stockType.equals("日線")) {
			DrawFrameData(g, stocksData);
//			DrawStockIndexes(gudtStockDay, gudtIndexDay, gintDayIndex, displayEndIndex)
		} else if (stockType.equals("週線")) {
			
		} else if (stockType.equals("月線")) {
			
		}
	}
	
	// 判斷要繪製的資料
	// 要傳入stockData, indexData(技術指標先略，不處理~~)
	public void DrawFrameData(Graphics g, StocksData[] sd) {
		Graphics2D g2 = (Graphics2D) g;
		
		// Chalk_K_Map(sds, ids, 0);
		// 判斷資料的高、低點值
		double highestPrice = -1;
		double lowestPrice = 99999;
		System.out.println("[DrawFrameData()] -- startDisplayRecord= " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord);
		for (int i = startDisplayRecord; i <= endDisplayRecord ; i++) {
			if (highestPrice < sd[i].getHighPrice())
				highestPrice = sd[i].getHighPrice();
			if (lowestPrice > sd[i].getLowPrice())
				lowestPrice = sd[i].getLowPrice();
		}		
		System.out.println("[DrawFrameData()] -- highestPrice= " + highestPrice + ", lowestPrice= " + lowestPrice);
		
		//--  MainFrame(主要股票視窗< K-window> 視窗內畫 5 條虛線) 橫線(虛線)  --//
		// 虛線起、迄點(p1, p2)
		Point2D p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + 10);	// 距離外框上緣下移 10 pixels
		Point2D p2 = new Point2D.Double(mainFrameStartX + mainFrameWidthDistance, mainFrameStartY + 10); // 距離外框上緣下移 10 pixels
		// 畫線
		Line2D KFrameLine = new Line2D.Double(p1, p2);
		g2.setStroke(strokeDottedLine);
		g2.setPaint(Color.red);
		g2.draw(KFrameLine);
		
		// Draw K圖中間的3條虛線
		double eachKFrameLineDistance = (mainFrameHighDistance - 20) / 4.0;
		for (int i = 1; i < 4; i++) {
			p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + 10 + (eachKFrameLineDistance * i));
			p2 = new Point2D.Double(mainFrameStartX+mainFrameWidthDistance, mainFrameStartY + 10 + (eachKFrameLineDistance * i));
			KFrameLine = new Line2D.Double(p1, p2);
			g2.setPaint(Color.darkGray);
			g2.draw(KFrameLine);	
		}

		p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + mainFrameHighDistance - 10); // 距離外框底線上移 10 pixels
		p2 = new Point2D.Double(mainFrameStartX+mainFrameWidthDistance, mainFrameStartY + mainFrameHighDistance - 10); // 距離外框底線上移 10 pixels
		KFrameLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.red);
		g2.draw(KFrameLine);

		//-----------------------------------------------------------------------------------------
		
		//-- MainFrame : Draw K-Bar (2018.02.05) --//
//		System.out.println("[DrawFrameData()] -- Draw K-Bar START");
		g2.setPaint(Color.DARK_GRAY);
		for (int i = startDisplayRecord; i < endDisplayRecord ; i++) {
//			System.out.println("[DrawFrameData()] -- value= " + sd[i].printData());
			double KBarStartX = 0;	// K-Bar左上角的x座標
			double KBarStartY = 0;	// K-Bar左上角的y座標
			double KBarHigh = 0;	// 當天的K-Bar的高度
			double barHigh2 = 0;	// (當天的開盤價 - 收盤價) 轉換成像素的值
			double barHigh3 = 0;
			double barHigh4 = 0;
			double eachPricePixels = (mainFrameHighDistance) / (highestPrice - lowestPrice); // 指數(or 價格)轉換成像素的值

			//-- draw K-Bar START -------------------------------------------------------------------------------------------
			g2.setStroke(new BasicStroke(1));
			if (sd[i].getStartPrice() == sd[i].getEndPrice()) {
				g2.setPaint(Color.RED);
				barHigh2 = (highestPrice - sd[i].getStartPrice()) * eachPricePixels;
				KBarStartX = mainFrameStartX + kBarWidth * i;
				KBarStartY = mainFrameStartY + barHigh2;
				p1 = new Point2D.Double(KBarStartX, KBarStartY);
				p2 = new Point2D.Double(KBarStartX + kBarWidth, KBarStartY);
//				System.out.println("[DrawFrameData()] -- p1.x= " + p1.getX() + ",p1.y= " +p1.getY());
//				System.out.println("[DrawFrameData()] -- p2.x= " + p2.getX() + ",p2.y= " +p2.getY());
				Line2D KLine = new Line2D.Double(p1, p2);
				g2.draw(KLine);	
				// 垂直線
				barHigh3 = (highestPrice - sd[i].getHighPrice()) * eachPricePixels;
				Point2D p3 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh3);
				barHigh4 = (highestPrice - sd[i].getLowPrice()) * eachPricePixels;
				Point2D p4 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh4);
				Line2D KLine2 = new Line2D.Double(p3, p4);
				g2.draw(KLine2);					
			} else {
				if (sd[i].getStartPrice() > sd[i].getEndPrice()) {
					g2.setPaint(Color.GREEN);
					KBarHigh = (sd[i].getStartPrice() - sd[i].getEndPrice()) * eachPricePixels;	
					barHigh2 = (highestPrice -sd[i].getStartPrice()) * eachPricePixels;
				} else {
					g2.setPaint(Color.RED);
					KBarHigh = (sd[i].getEndPrice() - sd[i].getStartPrice()) * eachPricePixels;
					barHigh2 = (highestPrice -sd[i].getEndPrice()) * eachPricePixels;
				}
				KBarStartX = mainFrameStartX + kBarWidth * i;
				KBarStartY = mainFrameStartY + barHigh2;
				Shape kFrame = new Rectangle2D.Double(KBarStartX, KBarStartY, kBarWidth, KBarHigh);
				System.out.println("[DrawFrameData()] -- mainFrameStartX= " + (mainFrameStartX + kBarWidth*i) + ", mainFrameStartY= " + (mainFrameStartY + eachPricePixels));
//				g2.draw(kFrame);	// 空心	
				g2.fill(kFrame);	// 填滿
				// 垂直線
				barHigh3 = (highestPrice - sd[i].getHighPrice()) * eachPricePixels;
				Point2D p3 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh3);
				barHigh4 = (highestPrice - sd[i].getLowPrice()) * eachPricePixels;
				Point2D p4 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh4);
				Line2D KLine2 = new Line2D.Double(p3, p4);
				g2.draw(KLine2);					
			}
			//-- draw K-Bar END ---------------------------------------------------------------------------------------------

			
			//-- 20180223 畫K線圖的垂直線(以月份來畫線) START ---------------------------------------------------
			if (i < endDisplayRecord) {
				String d1 = String.valueOf(sd[i].getDate());
				String d2 = String.valueOf(sd[i+1].getDate());
				d1 = d1.substring(d1.length()-4, d1.length()-2);
				d2 = d2.substring(d2.length()-4, d2.length()-2);
//				System.out.println("d1= " + d1 + ", d2= " + d2);
				if (!d1.equals(d2)) {
					double tradeDateStart = KBarStartX;
					double tradeDateEnd = mainFrameStartY; 
					Point2D p5 = new Point2D.Double(tradeDateStart, mainFrameStartY);
					tradeDateEnd = mainFrameStartY + (this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance - infoFrameHighDistance);
					Point2D p6 = new Point2D.Double(tradeDateStart, tradeDateEnd);
					Line2D KLine3 = new Line2D.Double(p5, p6);
					g2.setPaint(Color.GRAY);
					g2.setStroke(strokeDottedLine);
					g2.draw(KLine3);				
					// 交易日期
					g2.setPaint(Color.BLUE);
					String tradeDate = String.valueOf(sd[i].getDate());
					g2.drawString(tradeDate.substring(0, tradeDate.length()-2), (float) tradeDateStart, (float) tradeDateEnd+10);	
				}
			}	
			//-- 20180223 畫K線圖的垂直線(以月份來畫線) END   ---------------------------------------------------
		
			
			// 20180221 顯示最高價、最低價 (本來想用JLabel，但是不熟，而且可能影響到寫法，先用繪製文字處理)
			g2.setPaint(Color.BLUE);
			if (sd[i].getHighPrice() == highestPrice) {
				g2.drawString(Double.toString(highestPrice) , (float) KBarStartX, (float) (mainFrameStartY + barHigh3));
			} else if (sd[i].getLowPrice() == lowestPrice ) {
				g2.drawString(Double.toString(lowestPrice) , (float) KBarStartX, (float) (mainFrameStartY + barHigh4));
			}
		}		
		// 20180221 顯示K圖旁邊的指數值
		double indexValue = 0;
		DecimalFormat df = new DecimalFormat(".##");
		for (int j = 0; j < 5; j++) {
			indexValue = highestPrice - (10 + eachKFrameLineDistance * j) * (highestPrice - lowestPrice) / mainFrameHighDistance;
			g2.drawString(df.format(indexValue) , 0, (float) ((mainFrameStartY + 10 + eachKFrameLineDistance * j)));				
		}
	}
	
	// 在顯示畫面的右方顯示一些技術指標的值
	// 輸入參數: gudtStock     股價資料
	//          gudtIndex     技術指標資料
	//          intTotalIndex 資料總筆數
	//          sngIndex      目前畫面所在位置(第??筆)
	public void DrawStockIndexes() {
		
	}
	

	
	
	
	public static void main(String[] args) {
		EarvinStocksFrameTest f = new EarvinStocksFrameTest("EarvinStocksFrame V0.2"); 
	      f.setLayout(new BorderLayout()); 
	      DisplayStocksForm2 stockForm2 = new DisplayStocksForm2(5); 
	      f.add(stockForm2, BorderLayout.CENTER); 
//	      f.setSize(f.getPreferredSize()); 
	      f.center(); 
//	      f.setResizable(false); 
	      f.setVisible(true); 
	}
}
