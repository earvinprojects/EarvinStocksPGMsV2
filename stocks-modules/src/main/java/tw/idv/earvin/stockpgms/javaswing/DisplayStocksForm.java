package tw.idv.earvin.stockpgms.javaswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JComponent;

import tw.idv.earvin.stockpgms.javaswing.FrameData;

public class DisplayStocksForm extends JComponent {

    public float gsngBarWidth = 300;	// The bar_width of K map
    public float gsngTopCommand = 500;	// display the command bottom
    public float gsngTopFrame = 980;	// the height of top edge      (最上面顯示股價名稱、價格等文字的寬度-固定)
    public float gsngBottomFrame = 400;	// the height of bottom edge (最下面顯示年月文字的寬度-固定)
    public float gsngLeftLevel = 750;	// the width of left edge     (最左側顯示價格文字的寬度-固定)
    public float gsngRightLevel = 1750;	// the width of right edge    (最右側顯示指標文字的寬度-固定) 應是指最右側顯示技術指標值的寬度

	// 定義最多只能開10個frame
	public FrameData frameDatas[] = new FrameData[10];

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

		//------------------
		//-- 20180115 STR --
		//------------------
		System.out.println("location -- x= " + this.getX() + ", y= " + this.getY());
		System.out.println("size -- h= " + this.getHeight() + ", w= " + this.getWidth());
		g.drawRect(0+10, 0+10, this.getWidth()-20, this.getHeight()-20);	// 外框
		g.drawRect(0+10, 0+40, this.getWidth()-150, this.getHeight()-50);	// 內框
		g.drawRect(10+this.getWidth()-150, 0+10, 130, this.getHeight()-20);	// 右框
		g.drawLine(0+10, (int)(40+(this.getHeight()-50)/3), 10+this.getWidth()-150, (int)(40+(this.getHeight()-50)/3));	// k線圖底線

		int frameCount = 4;
		int startX = (int)(40+(this.getHeight()-50)/3);
		int addHeight = (this.getHeight()-50)*2/3/frameCount;
		for (int i = 0; i < (frameCount-1); i++) {
			startX += addHeight;
			g.drawLine(0+10, startX, 10+this.getWidth()-150, startX);	// k線圖底線			
		}
		//------------------
		//-- 20180115 END --
		//------------------
		
	
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
	}
	
	// 繪出股票圖
	// endIndex 結束日期
	// stockName 股票中文名稱
	public void DrawStockForm(Graphics g, int endIndex, String stockName) {
/*	    frameCount = cboFrameNum.Text ' 記錄目前的frame數目
 		// 設定各個frame高度
	    Call SetEachFrameHigh(frameCount)
	   	// 繪製frame外框
	    Call DrawOutlineOfFrames(frameCount)
	    // 記錄要顯示的資料起始位置(gsngStartindex
	    Call SetDisplayStartIndex
	    // 繪製要顯示的線圖是 日線 OR 週線 OR 月線
	    Call DrawStockFormByStockType(cboStocksType.Text, displayEndIndex)
*/
		SetEachFrameHigh(7);	// 設定各個frame高度
		DrawOutlineOfFrames(g, 7);	// 繪製frame外框
	}
	
	// 設定各個frame高度
	// frameCount frame數量
	public void SetEachFrameHigh(int frameCount) {
		if (frameCount == 1) {
			frameDatas[0].setHeight(this.getHeight() - gsngTopFrame - gsngTopCommand - gsngBottomFrame);
		} else {
			//mudtFrame(1).sngHeight = 3 * ((Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand - gsngBottomFrame)) / (frameCount + 2)
			frameDatas[0].setHeight((3 * this.getHeight() - gsngTopFrame - gsngTopCommand - gsngBottomFrame) / (frameCount + 2));
			for (int i = 1; i < frameCount; i++) {
				//mudtFrame(processFrame).sngHeight = (Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand - gsngBottomFrame) / (frameCount + 2)
				frameDatas[i].setHeight((this.getHeight() - gsngTopFrame - gsngTopCommand - gsngBottomFrame) / (frameCount + 2));
			}
		}
	}
	
	// 繪製frame外框
	// frameCount frame數量
	public void DrawOutlineOfFrames(Graphics g, int frameCount) {

/*	    Dim processFrame As Integer
	    ' left side
	    Line (0.98 * gsngLeftLevel, gsngBottomFrame)-(0.98 * gsngLeftLevel, Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand), RGB(200, 100, 0)
	    ' right side
	    Line (Abs(frmEarvinStocks.ScaleWidth) - gsngRightLevel, gsngBottomFrame)-(Abs(frmEarvinStocks.ScaleWidth) - gsngRightLevel, Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand), RGB(200, 100, 0)
	    ' bottom side
	    Line (0.98 * gsngLeftLevel, 0.98 * gsngBottomFrame)-(frmEarvinStocks.ScaleWidth - 0.99 * gsngRightLevel, 0.98 * gsngBottomFrame), RGB(200, 100, 0)
	    ' top side
	    Line (0.98 * gsngLeftLevel, Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand)-(frmEarvinStocks.ScaleWidth - 0.99 * gsngRightLevel, Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand), RGB(200, 100, 0)
	    gsngYshift = gsngBottomFrame
	    For processFrame = frameCount To 2 Step -1
	        gsngYshift = gsngYshift + mudtFrame(processFrame).sngHeight
	        ' draw bottom line of current frame
	        Line (gsngLeftLevel, gsngYshift)-(frmEarvinStocks.ScaleWidth - gsngRightLevel, gsngYshift), RGB(255, 255, 255)
	    Next
*/
		
	}
	
	// 記錄要顯示的資料起始位置(gsngStartindex
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
