package tw.idv.earvin.stockpgms.javaswing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JComponent;

import tw.idv.earvin.stockpgms.javaswing.FrameData;
import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;

public class DisplayStocksForm extends JComponent {
    
	/**
	 * 20180201 不知是要做啥用的？
	 */
	private static final long serialVersionUID = 1L;
	//------------------
	//-- 20180116 STR --
	//------------------
    /** 參考  ..\‧Dropbox\myStocksPGMs\V2.0\EarvinStocksFormSettings(20180131).xls的圖例會比較清楚 **/
    private double outerFrameUpperDistance = 10;
    private double outerFrameBottomDistance = 10;
    private double outerFrameLeftDistance = 50;
    private double outerFrameRightDistance = 10;
    private double infoFrameHighDistance = 30;
    private double indexFrameWidthDistance = 150;
    private double mainFrameHighDistance = 0;
    private double mainFrameWidthDistance = 0;
    private double subFramesTotalHighDistance = 0;
    
    private double outerFrameStartX = outerFrameLeftDistance;
    private double outerFrameStartY = outerFrameUpperDistance;
    private double mainFrameStartX = outerFrameLeftDistance;
    private double mainFrameStartY = outerFrameStartY + infoFrameHighDistance;
    private double indexFrameStartX = 0; // 視窗寬度 - outerFrameRightDistance - indexFrameWidthDistance
    private double indexFrameStartY = 0;
    private double subFrameStartX = 0;
    private double subFrameStartY  = 0;

	private double kBarWidth = 20;   
    private int startDisplayRecord = 0;
	private int endDisplayRecord = 0;

	// 定義最多只能開10個frame
	private int frameCount = 5; // 暫定…
	public FrameData frameDatas[] = new FrameData[10];
	StocksData[] stocksData = null;	// 股票資料
//	IndexsData[] indexsData = null; // 股票技術指標資料(20180131: 先不處理…)
	//------------------
	//-- 20180116 STR --
	//------------------
     

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setForeground(Color.BLUE);
		System.out.println("[DisplayStocksForm.paintComponent()], width= " + this.getWidth() + ", height= " + this.getHeight());
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		Dimension frameSize = this.getSize();
		
		//-- 20180106 ADD STR ---------------------------------
        // 設定K-Bar
/*		If Abs(.Height) > 500 Then ' avoiding the happening of minimun window size
        	.ScaleHeight = -.Height ' set the x and y axial
        	.ScaleWidth = .Width
        	.ScaleTop = .Height
        	' gsngStartindex : 起始日期
        	' gsngEndindex   : 結束日期
        	' gsngBarwidth   : K-Bar的寬度
        	If gsngStartIndex > 1 Then
            	gsngStartIndex = Int(gsngEndIndex - frmEarvinStocks.Width / gsngBarWidth)
           	Else	
            	gsngEndIndex = gsngStartIndex + Int(frmEarvinStocks.Width / gsngBarWidth)
          	End If
           	Call DrawStockForm(gsngEndIndex, GetStockName(cboStocks.Text))
        End If
*/        
    	//-- 20180106 ADD END ---------------------------------
    	stocksData = TestReadTxtFile.getData();
    	endDisplayRecord = stocksData.length;
		if (startDisplayRecord > 1) {
			startDisplayRecord = endDisplayRecord -  (int) (mainFrameWidthDistance / kBarWidth);
		} else {
			endDisplayRecord = startDisplayRecord + (int) (mainFrameWidthDistance / kBarWidth);
		}
		System.out.println("startDisplayRecord= " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord + ", mainFrameWidthDistance= " + mainFrameWidthDistance + ", kBarWidth= " + kBarWidth);
		// draw stock form
		DrawStockForm(g, endDisplayRecord, "中鋼");
	}
	
	// 繪出股票圖
	// endIndex 結束日期
	// stockName 股票中文名稱
	public void DrawStockForm(Graphics g, int endIndex, String stockName) {
/*	    frameCount = cboFrameNum.Text ' 記錄目前的frame數目
 		// 設定各個frame高度 (ok)
	    Call SetEachFrameHigh(frameCount)
	   	// 繪製frame外框 (ok)
	    Call DrawOutlineOfFrames(frameCount)
	    // 記錄要顯示的資料起始位置(gsngStartindex)
	    Call SetDisplayStartIndex
	    // 繪製要顯示的線圖是 日線 OR 週線 OR 月線
	    Call DrawStockFormByStockType(cboStocksType.Text, displayEndIndex)
*/
//		SetEachFrameHigh(7);	// 設定各個frame高度
//		DrawOutlineOfFrames(g, 7);	// 繪製frame外框
		DrawOutlineOfFrames2D(g, frameCount);	// 繪製frame外框
		SetDisplayStartIndex();
		System.out.println("[SetDisplayStartIndex()] -- startDisplayRecord= " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord + ", mainFrameWidthDistance= " + mainFrameWidthDistance + ", kBarWidth= " + kBarWidth);
		DrawStockFormByStockType("日線", endDisplayRecord);
	}
	
	// 繪製frame外框 (Write OK, 20180119)
	// frameCount frame數量
	public void DrawOutlineOfFrames2D(Graphics g, int frameCount) {
		Graphics2D g2 = (Graphics2D) g;

		// 決定各個數值
		mainFrameWidthDistance = this.getWidth() - outerFrameLeftDistance - indexFrameWidthDistance - outerFrameRightDistance;
		indexFrameStartX = outerFrameLeftDistance + mainFrameWidthDistance;
		indexFrameStartY = outerFrameUpperDistance + infoFrameHighDistance;
		subFrameStartX = mainFrameStartX;
		subFrameStartY = mainFrameStartY + mainFrameHighDistance;
		// 顯示主要股票K線圖(佔可使用之Frame高度4/9)
		mainFrameHighDistance = (this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance - infoFrameHighDistance) * 4 / 9;
		//其它技術視窗(分享可使用之Frame高度 5/9)
		subFramesTotalHighDistance = this.getHeight() - outerFrameUpperDistance - infoFrameHighDistance - mainFrameHighDistance - outerFrameBottomDistance;
		// 外框(起點座標：outerFrameStartX，outerFrameStartY)
		Shape outerFrame = new Rectangle2D.Double(outerFrameStartX, outerFrameStartY, this.getWidth() - outerFrameLeftDistance - outerFrameRightDistance, this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance);
		g2.draw(outerFrame);
		// 右側邊框(顯示各Frame的技術指標資訊)
		Shape indexFrame = new Rectangle2D.Double(indexFrameStartX, indexFrameStartY, indexFrameWidthDistance, (this.getHeight() - outerFrameUpperDistance - outerFrameBottomDistance - infoFrameHighDistance));	
		g2.draw(indexFrame);
		// 上方股市資訊顯示區(起點座標：同外框)
		Shape infoFrame = new Rectangle2D.Double(outerFrameStartX, outerFrameStartY, (this.getWidth() - outerFrameLeftDistance - outerFrameRightDistance), infoFrameHighDistance);	
		g2.draw(infoFrame);
		// 主要股票k線圖的底線 
		Point2D p1 = new Point2D.Double(subFrameStartX, subFrameStartY);
		Point2D p2 = new Point2D.Double(indexFrameStartX, subFrameStartY);
		Line2D KBottomLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.gray);
		g2.draw(KBottomLine);
		// 記錄可顯示股票資料各個frame的高度
		Vector<FrameData> fds = new Vector<FrameData>();
		fds.add(new FrameData(mainFrameHighDistance, 0));	// 第1個是K線圖	
		// 其它指標視窗的底線(最後一個frame的底線不用畫)
		for (int i = 0; i < (frameCount - 1); i++) {
			subFrameStartY += (subFramesTotalHighDistance / (frameCount));
			Point2D p3 = new Point2D.Double(subFrameStartX, subFrameStartY);
			Point2D p4 = new Point2D.Double(indexFrameStartX, subFrameStartY);
			Line2D FramesBottomLine = new Line2D.Double(p3, p4);
			g2.setPaint(Color.BLUE);
			g2.draw(FramesBottomLine);
			fds.add(new FrameData(subFramesTotalHighDistance / frameCount, 0));
		}
		// Last one
		fds.add(new FrameData(subFramesTotalHighDistance - subFramesTotalHighDistance * (frameCount - 1) / frameCount, 0));
		// DEBUG-MSG: 顯示每個frame的高度
		for (int i = 0; i <= frameCount; i++) {
			FrameData fd = fds.get(i);
			System.out.println("Frame[" + i + "]= " + fd.getHeight());
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
	public void DrawStockFormByStockType(String stockType, int displayEndIndex) {
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
			DrawFrameData(stocksData);
//			DrawStockIndexes(gudtStockDay, gudtIndexDay, gintDayIndex, displayEndIndex)
		} else if (stockType.equals("週線")) {
			
		} else if (stockType.equals("月線")) {
			
		}
	}
	
	// 判斷要繪製的資料
	// 要傳入stockData, indexData(技術指標先略，不處理~~)
	public void DrawFrameData(StocksData[] sd) {
		// Chalk_K_Map(sds, ids, 0);
		// 判斷資料的高、低點值
		double highPrice = -1;
		double lowPrice = 99999;
		for (int i = 0; i < sd.length; i++) {
			if (highPrice < sd[i].getHighPrice())
				highPrice = sd[i].getHighPrice();
			if (lowPrice > sd[i].getLowPrice())
				lowPrice = sd[i].getLowPrice();
		}		
	}
	
	// 在顯示畫面的右方顯示一些技術指標的值
	// 輸入參數: gudtStock     股價資料
	//          gudtIndex     技術指標資料
	//          intTotalIndex 資料總筆數
	//          sngIndex      目前畫面所在位置(第??筆)
	public void DrawStockIndexes() {
		
	}
}
