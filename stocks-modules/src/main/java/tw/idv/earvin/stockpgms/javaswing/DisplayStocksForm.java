package tw.idv.earvin.stockpgms.javaswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JComponent;

import tw.idv.earvin.stockpgms.javaswing.FrameData;

public class DisplayStocksForm extends JComponent {

    public float gsngBarWidth = 300;	// The bar_width of K map
    public float gsngTopCommand = 500;	// display the command bottom
    public float gsngTopFrame = 980;	// the height of top edge      (最上面顯示股價名稱、價格等文字的寬度-固定)
    public float gsngBottomFrame = 400;	// the height of bottom edge (最下面顯示年月文字的寬度-固定)
    public float gsngLeftLevel = 750;	// the width of left edge     (最左側顯示價格文字的寬度-固定)
    public float gsngRightLevel = 1750;	// the width of right edge    (最右側顯示指標文字的寬度-固定) 應是指最右側顯示技術指標值的寬度

    
	//------------------
	//-- 20180116 STR --
	//------------------
    private int outerFrameHDistance = 10;
    private int outerFrameWDistance = 10;
    private int rightFrameWDistance = 150;
    private int upperFrameHDistance = 30;
    private int canUsedFrameWDistance = 0;	// 記錄可顯示股票資料的寬度
	// 定義最多只能開10個frame
	public FrameData frameDatas[] = new FrameData[10];
	//------------------
	//-- 20180116 STR --
	//------------------
     

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setForeground(Color.BLUE);
		System.out.println("[DisplayStocksForm.paintComponent()], width= " + this.getWidth() + ", height= " + this.getHeight());
//		setBackground(Color.GREEN);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
//		System.out.println("Frame Height= " + frameSize.getHeight() + ", Frame width= " + frameSize.getWidth());

		int kBarWidth = 10;
		int startDisplayRecord = 0;
		int endDisplayRecord = 1000;
		startDisplayRecord = endDisplayRecord -  (int) screenSize.getWidth() / kBarWidth;
		System.out.println("startDisplayRecord= " + startDisplayRecord +", endDisplayRecord= " + endDisplayRecord);

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
        End If
*/        
    	//-- 20180106 ADD END ---------------------------------
		DrawStockForm(g, 1, "test");
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
		DrawOutlineOfFrames2D(g, 7);	// 繪製frame外框
	}
	

	// 繪製frame外框 (Write OK, 20180119)
	// frameCount frame數量
	public void DrawOutlineOfFrames(Graphics g, int frameCount) {
		// 外框
		g.drawRect(outerFrameHDistance, outerFrameWDistance, this.getWidth() - 2 * outerFrameHDistance, this.getHeight() - 2 * outerFrameWDistance);
		// 右側邊框(顯示各Frame的技術指標資訊)
		g.drawRect(this.getWidth() - outerFrameWDistance - rightFrameWDistance, 
				(outerFrameHDistance + upperFrameHDistance), 
				rightFrameWDistance, 
				(this.getHeight() - 2 * outerFrameHDistance - upperFrameHDistance));
		// 上方股市資訊顯示區
		g.drawRect(outerFrameHDistance, 
				outerFrameWDistance, 
				(this.getWidth() - outerFrameWDistance * 2), 
				upperFrameHDistance);
		// 顯示主要股票K線圖(佔可使用之Frame高度 1/3)
		int mainStockFrameHDistance = (this.getHeight() - upperFrameHDistance - outerFrameHDistance * 2) / 3;
		//其它技術視窗(分享可使用之Frame高度 2/3)
		int otherIndexFrameHDistance = (this.getHeight() - upperFrameHDistance - outerFrameHDistance * 2) / 3 * 2;
		// 主要股票k線圖的底線 
		g.drawLine(outerFrameHDistance, 
				(outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance), 
				(this.getWidth() - rightFrameWDistance - outerFrameWDistance), 
				(outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance));
		
		// 記錄可顯示股票資料的寬度
		canUsedFrameWDistance = (this.getWidth() - rightFrameWDistance - outerFrameWDistance * 2);
		System.out.println("canUsedFrameWDistance= " + canUsedFrameWDistance);
		// 記錄可顯示股票資料各個frame的高度
		Vector<FrameData> fds = new Vector();
		fds.add(new FrameData(mainStockFrameHDistance, 0));	// 第1個是K線圖	
		// 其它指標視窗的底線(最後一個frame的底線不用畫)
		int startY = outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance;
		for (int i = 0; i < (frameCount - 1); i++) {
			startY += (otherIndexFrameHDistance / frameCount);
			g.drawLine(outerFrameHDistance, startY, (this.getWidth() - rightFrameWDistance - outerFrameWDistance), startY);	
			fds.add(new FrameData(otherIndexFrameHDistance / frameCount, 0));
		}
		// Last one
		fds.add(new FrameData(otherIndexFrameHDistance - otherIndexFrameHDistance * (frameCount-1) / frameCount, 0));
		// DEBUG-MSG: 顯示每個frame的高度
		for (int i = 0; i <= frameCount; i++) {
			FrameData fd = fds.get(i);
			System.out.println("Frame[" + i + "]= " + fd.getHeight());
		}
	}
	
	// 繪製frame外框 (Write OK, 20180119)
	// frameCount frame數量
	public void DrawOutlineOfFrames2D(Graphics g, int frameCount) {
		Graphics2D g2 = (Graphics2D) g;
		// 外框
		Shape outerFrame = new Rectangle2D.Double(outerFrameHDistance, outerFrameWDistance, this.getWidth() - 2 * outerFrameHDistance, this.getHeight() - 2 * outerFrameWDistance);
		g2.draw(outerFrame);
		// 右側邊框(顯示各Frame的技術指標資訊)
		Shape rightFrame = new Rectangle2D.Double(this.getWidth() - outerFrameWDistance - rightFrameWDistance, 
				(outerFrameHDistance + upperFrameHDistance), 
				rightFrameWDistance, 
				(this.getHeight() - 2 * outerFrameHDistance - upperFrameHDistance));	
		g2.draw(rightFrame);
		// 上方股市資訊顯示區
		Shape upperFrame = new Rectangle2D.Double(outerFrameHDistance, 
				outerFrameWDistance, 
				(this.getWidth() - outerFrameWDistance * 2), 
				upperFrameHDistance);	
		g2.draw(upperFrame);
		// 顯示主要股票K線圖(佔可使用之Frame高度 1/3)
		int mainStockFrameHDistance = (this.getHeight() - upperFrameHDistance - outerFrameHDistance * 2) / 3;
		//其它技術視窗(分享可使用之Frame高度 2/3)
		int otherIndexFrameHDistance = (this.getHeight() - upperFrameHDistance - outerFrameHDistance * 2) / 3 * 2;
		// 主要股票k線圖的底線 
		Point2D p1 = new Point2D.Double(outerFrameHDistance, (outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance));
		Point2D p2 = new Point2D.Double((this.getWidth() - rightFrameWDistance - outerFrameWDistance), (outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance));
		Line2D KBottomLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.gray);
		g2.draw(KBottomLine);
		
		// 記錄可顯示股票資料的寬度
		canUsedFrameWDistance = (this.getWidth() - rightFrameWDistance - outerFrameWDistance * 2);
		System.out.println("canUsedFrameWDistance= " + canUsedFrameWDistance);
		// 記錄可顯示股票資料各個frame的高度
		Vector<FrameData> fds = new Vector();
		fds.add(new FrameData(mainStockFrameHDistance, 0));	// 第1個是K線圖	
		// 其它指標視窗的底線(最後一個frame的底線不用畫)
		int startY = outerFrameWDistance + upperFrameHDistance + mainStockFrameHDistance;
		for (int i = 0; i < (frameCount - 1); i++) {
			startY += (otherIndexFrameHDistance / frameCount);
			Point2D p3 = new Point2D.Double(outerFrameHDistance, startY);
			Point2D p4 = new Point2D.Double((this.getWidth() - rightFrameWDistance - outerFrameWDistance), startY);
			Line2D FramesBottomLine = new Line2D.Double(p3, p4);
			g2.setPaint(Color.cyan);
			g2.draw(FramesBottomLine);

			fds.add(new FrameData(otherIndexFrameHDistance / frameCount, 0));
		}
		// Last one
		fds.add(new FrameData(otherIndexFrameHDistance - otherIndexFrameHDistance * (frameCount-1) / frameCount, 0));
		// DEBUG-MSG: 顯示每個frame的高度
		for (int i = 0; i <= frameCount; i++) {
			FrameData fd = fds.get(i);
			System.out.println("Frame[" + i + "]= " + fd.getHeight());
		}
	}
	
	// 記錄要顯示的資料起始位置(gsngStartindex)
	public void SetDisplayStartIndex() {
//		gsngStartIndex = IIf(Int(gsngEndIndex - frmEarvinStocks.Width / gsngBarWidth) >= 1, Int(gsngEndIndex - frmEarvinStocks.Width / gsngBarWidth), 1)
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
	}
	
	// 判斷要繪製的資料
	// 要傳入stockData, indexData
	public void DrawFrameData() {
/*	    Dim processFrame As Integer
	    Dim frameCount As Integer
	        
	    frameCount = frmEarvinStocks.cboFrameNum
	    gbytFrameNum = frameCount   ' 還不能拿掉，後面會用到…
	    
	    For processFrame = 1 To frameCount
	        Select Case mudtFrame(processFrame).bytAttribute
	            Case mKDmap
	                Call Chalk_Line_Map(gudtStock, gudtIndex, mKDmap, 3, processFrame)
	            Case mRSImap
	                Call Chalk_Line_Map(gudtStock, gudtIndex, mRSImap, 3, processFrame)
	            Case mPSYmap
	                Call Chalk_Line_Map(gudtStock, gudtIndex, mPSYmap, 2, processFrame)
	            Case mEMAmap
	                Call Chalk_Line_Map(gudtStock, gudtIndex, mEMAmap, 2, processFrame)
	            Case mBiasmap
	                Call Chalk_Line_Map(gudtStock, gudtIndex, mBiasmap, 1, processFrame)
	            Case mSectorMap
	                Call Chalk_Bar_MAP(gudtStock, gudtIndex, mSectorMap, 2, processFrame)
	            Case mMACDmap
	                Call Chalk_MACD_Map(gudtStock, gudtIndex, processFrame)
	            Case mQuantityMap ' 20100415 , Has problem ...
	                Call Chalk_Quantity_Map(gudtStock, gudtIndex, processFrame)
	         
	            '--- 下面有空再改~~ 應該著重在預測分析模型才對!! 又不是要練寫程式說~~ (20100416) ----
	            Case mKmap
	                Call Chalk_K_Map(gudtStock, gudtIndex, processFrame)
	            Case mStochRSImap
	                Call Chalk_StochRSI_Map(gudtStock, gudtIndex, processFrame)
	            Case mWRSImap
	                Call Chalk_WRSI_Map(gudtStock, gudtIndex, processFrame)
	            Case mWMSmap
	                Call Chalk_William_Map(gudtStock, gudtIndex, processFrame)
	            Case mRWMSmap
	                Call Chalk_RWilliam_Map(gudtStock, gudtIndex, processFrame)
	            Case mAccMap
	                Call Chalk_Acc_Map(gudtStock, gudtIndex, processFrame)
	            Case mTomeMap
	                Call Chalk_Tome_Map(gudtStock, gudtIndex, processFrame)
	            Case mGRGMap
	                Call Chalk_GRG_MAP(gudtStock, gudtIndex, processFrame)
	            Case mSignalMap
	                Call Chalk_Signal_Map(gudtStock, gudtIndex, processFrame)
	            Case Else
	        End Select
	    Next
*/	
	}
	
	
	// 在顯示畫面的右方顯示一些技術指標的值
	// 輸入參數: gudtStock     股價資料
	//          gudtIndex     技術指標資料
	//          intTotalIndex 資料總筆數
	//          sngIndex      目前畫面所在位置(第??筆)
	public void DrawStockIndexes() {
		
	}

}
