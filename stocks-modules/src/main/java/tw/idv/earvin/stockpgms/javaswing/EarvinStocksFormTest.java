package tw.idv.earvin.stockpgms.javaswing;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

import javax.swing.*;

public class EarvinStocksFormTest extends javax.swing.JFrame {
	
	class DisplayStocksForm extends JComponent {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setForeground(Color.BLUE);
			g.drawString("Hello", 240, 140);

			g.fillRect(130, 130, 100, 80);
			g.drawOval(130, 130, 50, 60);
			g.fillOval(130, 130, 50, 60);
			g.drawArc(310, 200, 40, 50, 90, 60);
			g.fillArc(310, 130, 40, 50, 180, 40);
			
			//-- 20180113 STR --
			System.out.println("location -- x= " + this.getX() + ", y= " + this.getY());
			System.out.println("size -- h= " + this.getHeight() + ", w= " + this.getWidth());
			g.drawRect(0+5, 0+5, this.getWidth()-10, this.getHeight()-10);	// left corner
	
			
			//-- 20180113 END --
			
			System.out.println("[DisplayStocksForm.paintComponent()], width= " + this.getWidth() + ", height= " + this.getHeight());
//			setBackground(Color.GREEN);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
//			System.out.println("Frame Height= " + frameSize.getHeight() + ", Frame width= " + frameSize.getWidth());

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
		}
	}

	public static void main(String[] args) {
		try {
//			System.out.println(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			System.out.println("當前系統可用的所有LAF");
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				System.out.println(info.getName() + " --> " + info);
			}

		} catch (Exception e) {
			System.out.println("error");
		}
		EarvinStocksFormTest f = new EarvinStocksFormTest();
		// f.setLayout(new FlowLayout());
		f.setSize(1000, 800);
		System.out.println("frame=" + f.getBackground());
		f.setVisible(true);
	}

	// 建構子
	public EarvinStocksFormTest() {
		super("Earvin's Stocks Form");
		
		this.getContentPane().setLayout(new BorderLayout());
		// 設定視窗的大小
		this.setSize(1200, 800);
		// 取得螢幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("[EarvinStocksFormTest()] screen Height= " + screenSize.getHeight() + ", screen width= " + screenSize.getWidth());
		// 取得視窗大小
		
		Dimension frameSize = this.getSize();
		System.out.println("[EarvinStocksFormTest()] Frame Height= " + frameSize.getHeight() + ", Frame width= " + frameSize.getWidth());
		// 比較螢幕與視窗的高度
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		// 比較螢幕與視窗的寬度
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		// 將視窗定位於螢幕中央
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		
		// JDK 5.0以後，使用swing的寫法~~
		// Add Toolbar
		JPanel toolbar = new JPanel();
		toolbar.setBackground(Color.YELLOW);
		// toolbar.setBounds(0,0,200,200);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton testButton1 = new JButton("Test1");
		toolbar.add(testButton1);
		JButton testButton2 = new JButton("Test2");
		toolbar.add(testButton2);
		JButton testButton3 = new JButton("Test3");
		toolbar.add(testButton3);
		JButton testButton4 = new JButton("Test4");
		toolbar.add(testButton4);
		this.add(toolbar, BorderLayout.NORTH);
		// 顯示股票線圖
		this.add(new DisplayStocksForm(), BorderLayout.CENTER);
		

		// 視窗事件 (20180107 事件的寫法有3種，但是我有點忘了~~")
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}


	
	// 繪出股票圖
	// endIndex 結束日期
	// stockName 股票中文名稱
	public void DrawStockForm(int endIndex, String stockName) {
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
		
	}
	
	// 設定各個frame高度
	// frameCount frame數量
	public void SetEachFrameHigh(int frameCount) {
/*	    Dim processFrame As Integer ' 目前準備處理的frame
	    
	    If frameCount = 1 Then
	        mudtFrame(1).sngHeight = Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand - gsngBottomFrame
	    Else
	        mudtFrame(1).sngHeight = 3 * ((Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand - gsngBottomFrame)) / (frameCount + 2)
	        For processFrame = 2 To frameCount
	            mudtFrame(processFrame).sngHeight = (Abs(frmEarvinStocks.ScaleHeight) - gsngTopFrame - gsngTopCommand - gsngBottomFrame) / (frameCount + 2)
	        Next
	    End If
*/
	}
	
	// 繪製frame外框
	// frameCount frame數量
	public void DrawOutlineOfFrames(int frameCount) {
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
