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
import java.awt.event.*;

import tw.idv.earvin.stockpgms.javaswing.learning.TestReadTxtFile;
import tw.idv.earvin.stockpgms.javaswing.v1.FrameData;
import tw.idv.earvin.stockpgms.stocks_modules.indexes.IndexData;
import tw.idv.earvin.stockpgms.stocks_modules.indexes.IndexsData;
import tw.idv.earvin.stockpgms.stocks_modules.indexes.StocksData;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayStocksForm2 extends JComponent implements MouseMotionListener {
	/**
	 * 20180201 不知是要做啥用的？
	 */
	private static final long serialVersionUID = 1L;
	// ------------------
	// -- 20180116 STR --
	// ------------------
	/**
	 * 參考
	 * ..\‧Dropbox\myStocksPGMs\V2.0\EarvinStocksFormSettings(20180131).xls的圖例會比較清楚
	 **/

	// -- 常數區 --//
	final private double HIGHEST_VALUE = 99999;
	final private double LOWEST_VALUE = -1;
	final private double OUTER_FRAME_UPPER_DISTANCE = 38; // 需加上toolbar的高度(因為這個變數到處都有在用，所以數值先寫死)
	final private double OUTER_FRAME_BOTTOM_DISTANCE = 25;
	final private double OUTER_FRAME_LEFT_DISTANCE = 50;
	final private double OUTER_FRAME_RIGHT_DISTANCE = 10;
	final private double INFO_FRAME_HIGH_DISTANCE = 30;
	final private double INDEX_FRAME_WIDTH_DISTANCE = 150;
	final private int MAX_FRAME_COUNT = 10;

	Stroke strokeDottedLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2, 2 }, 0);
	Stroke stokeSolidLine = new BasicStroke(1);

	// -- 一般變數區 --//
	private double mainFrameHighDistance = 0;
	private double mainFrameWidthDistance = 0;
	private double subFramesTotalHighDistance = 0;

	// 最外方框的左上角座標(同 股票資訊顯示區左上角座標)
	private double outerFrameStartX = OUTER_FRAME_LEFT_DISTANCE;
	private double outerFrameStartY = OUTER_FRAME_UPPER_DISTANCE;
	// 股票資訊顯示區左上角座標
	private double infoFrameStartX = OUTER_FRAME_LEFT_DISTANCE;
	private double infoFrameStartY = OUTER_FRAME_UPPER_DISTANCE;
	// K線圖的左上角座標
	private double mainFrameStartX = OUTER_FRAME_LEFT_DISTANCE;
	private double mainFrameStartY = outerFrameStartY + INFO_FRAME_HIGH_DISTANCE;
	// 右側的技術指標左上角座標
	private double indexFrameStartX = 0; // 視窗寬度 - OUTER_FRAME_RIGHT_DISTANCE - INDEX_FRAME_WIDTH_DISTANCE
	private double indexFrameStartY = 0;
	// 技術指標區第一個方框左上角座標
	private double subFrameStartX = 0;
	private double subFrameStartY = 0;

	private double kBarWidth = 10; // K線圖的K-Bar預設寬度
	private int startDisplayRecord = 0; // 預設顯示於畫面上資料的起始位置
	private int endDisplayRecord = 0; // 預設顯示於畫面上資料的結束位置
	public int frameCount = 5; // (暫定…) 定義最能開5個frame
	public FrameData frameData[] = new FrameData[MAX_FRAME_COUNT]; // 儲存開啟frame的相關資料
	StocksData[] stocksData = null; // 股票資料
	IndexData indexsData = null; // 股票技術指標資料
	private int totalStocksCount = 0; // 股票資料總筆數
	private boolean showPriceVisibled = false;

	private JPanel toolbar = new JPanel();
	private JButton moveFirst;
	private JButton movePrev;
	private JButton moveNext;
	private JButton moveLast;
	private JButton showPrice;
	private JLabel frameCounts;
	private JComboBox selectFrameCounts = new JComboBox();

	private Shape mShapeOne, mShapeTwo;
	private JComboBox mOptions;

	private Point2D cp1;
	private Point2D cp2;

	public DisplayStocksForm2() {
		setBackground(Color.white);
		setLayout(new BorderLayout());

		// <2018.03.21> *** FOR TEST ***
		stocksData = TestReadTxtFile.getStocksData();
		indexsData = TestReadTxtFile.getIndexsData(stocksData);

		totalStocksCount = stocksData.length;
		endDisplayRecord = totalStocksCount - 1;
		// mainFrameWidthDistance = 1200 - OUTER_FRAME_LEFT_DISTANCE -
		// INDEX_FRAME_WIDTH_DISTANCE - OUTER_FRAME_RIGHT_DISTANCE;
		// startDisplayRecord = endDisplayRecord - (int) (mainFrameWidthDistance /
		// kBarWidth);

		initToolbar(toolbar);
		add(toolbar, BorderLayout.NORTH);

		addMouseMotionListener(this);
	}

	public void mouseMoved(MouseEvent e) {
		// eventOutput("Mouse moved", e);
		// location應該是以畫面左上角開始計算，會有問題~~
		// Point p = MouseInfo.getPointerInfo().getLocation();
		// System.out.println(p.getX() + ", " + p.getY());
		cp1 = new Point2D.Double(e.getX(), mainFrameStartY);
		double tradeDateEnd = mainFrameStartY
				+ (this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - OUTER_FRAME_BOTTOM_DISTANCE - INFO_FRAME_HIGH_DISTANCE);
		cp2 = new Point2D.Double(e.getX(), tradeDateEnd);
		System.out.println(cp1.getX() + ", " + cp1.getY() + " <--> " + cp2.getX() + ", " + cp2.getY());
		repaint();
	}

	// 目前未使用拖拉功能
	public void mouseDragged(MouseEvent e) {
		// eventOutput("Mouse dragged", e);
	}

	private void initToolbar(JPanel controls) {
		moveFirst = new JButton("第一筆");
		movePrev = new JButton("前五筆");
		moveNext = new JButton("後五筆");
		moveLast = new JButton("最後一筆");
		showPrice = new JButton("查價");

		// 設定移動資料筆數
		ActionListener movePosition = evt -> {
			if (evt.getActionCommand().equals("第一筆")) {
				endDisplayRecord = (int) (mainFrameWidthDistance / kBarWidth);
			} else if (evt.getActionCommand().equals("前五筆")) {
				if ((endDisplayRecord - 5) > 0) {
					endDisplayRecord -= 5;
				}
			} else if (evt.getActionCommand().equals("後五筆")) {
				if ((endDisplayRecord + 5) <= (totalStocksCount - 1)) {
					endDisplayRecord += 5;
				}
			} else if (evt.getActionCommand().equals("最後一筆")) {
				endDisplayRecord = 0;
			} else if (evt.getActionCommand().equals("查價")) {
				showPriceVisibled = !showPriceVisibled;
			}
			repaint();
		};

		moveFirst.addActionListener(movePosition);
		movePrev.addActionListener(movePosition);
		moveNext.addActionListener(movePosition);
		moveLast.addActionListener(movePosition);
		showPrice.addActionListener(movePosition);

		frameCounts = new JLabel("選擇方框數：");
		selectFrameCounts = new JComboBox();
		for (int i = 1; i < MAX_FRAME_COUNT; i++) {
			selectFrameCounts.addItem(i);
		}
		selectFrameCounts.setSelectedIndex(frameCount);
		selectFrameCounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCount = (int) selectFrameCounts.getSelectedItem();
				repaint();
			}
		});
		selectFrameCounts.setSelectedIndex(frameCount); // 預設6個subFrame

		controls.add(moveFirst);
		controls.add(movePrev);
		controls.add(moveNext);
		controls.add(moveLast);
		controls.add(showPrice);
		controls.add(frameCounts);
		controls.add(selectFrameCounts);
	}

	public void paintComponent(Graphics g) {
		// System.out.println("startDisplayRecord= " + startDisplayRecord + ",
		// endDisplayRecord= " + endDisplayRecord + ", mainFrameWidthDistance= " +
		// mainFrameWidthDistance + ", kBarWidth= " + kBarWidth);
		// 計算要顯示的資料起、迄位置
		/*
		 * if (startDisplayRecord > 0) { startDisplayRecord = endDisplayRecord - (int)
		 * (mainFrameWidthDistance / kBarWidth); } else { endDisplayRecord =
		 * startDisplayRecord + (int) (mainFrameWidthDistance / kBarWidth); }
		 */
		DrawStockForm(g, endDisplayRecord, "中鋼");
	}

	// 繪出股票圖
	// endIndex 結束日期
	// stockName 股票中文名稱
	public void DrawStockForm(Graphics g, int endIndex, String stockName) {
		DrawOutlineOfFrames2D(g, frameCount); // 繪製frame外框
		SetDisplayStartIndex();
		DrawStockFormByStockType(g, "日線", endDisplayRecord);
	}

	// 繪製frame外框 (Write OK, 20180119)
	// frameCount frame數量
	public void DrawOutlineOfFrames2D(Graphics g, int frameCount) {
		Graphics2D g2 = (Graphics2D) g;

		// 決定各個數值
		mainFrameWidthDistance = this.getWidth() - OUTER_FRAME_LEFT_DISTANCE - INDEX_FRAME_WIDTH_DISTANCE - OUTER_FRAME_RIGHT_DISTANCE;
		// 顯示主要股票K線圖(佔可使用之Frame高度4/9)
		mainFrameHighDistance = (this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - OUTER_FRAME_BOTTOM_DISTANCE - INFO_FRAME_HIGH_DISTANCE) * 4 / 9;
		// 其它技術視窗(分享可使用之Frame高度 5/9)
		subFramesTotalHighDistance = this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - INFO_FRAME_HIGH_DISTANCE - mainFrameHighDistance
				- OUTER_FRAME_BOTTOM_DISTANCE;

		// 儲存Main Frame相關資訊
		if (frameData[0] == null) {
			frameData[0] = new FrameData();
		}
		frameData[0].setX(mainFrameStartX);
		frameData[0].setY(mainFrameStartY);
		frameData[0].setHeight(mainFrameHighDistance);

		indexFrameStartX = OUTER_FRAME_LEFT_DISTANCE + mainFrameWidthDistance;
		indexFrameStartY = OUTER_FRAME_UPPER_DISTANCE + INFO_FRAME_HIGH_DISTANCE;
		subFrameStartX = mainFrameStartX;

		// 設定每一個subFrame(frameData索引值由1開始)的左上角座標
		// subFrameStartY = mainFrameStartY + mainFrameHighDistance;
		for (int i = 1; i < frameCount; i++) {
			subFrameStartY = mainFrameStartY + mainFrameHighDistance + subFramesTotalHighDistance / (frameCount - 1) * (i - 1);
			if (frameData[i] == null) {
				frameData[i] = new FrameData();
			}
			frameData[i].setX(subFrameStartX);
			frameData[i].setY(subFrameStartY);
		}

		// 外框(起點座標：outerFrameStartX，outerFrameStartY)
		Shape outerFrame = new Rectangle2D.Double(outerFrameStartX, outerFrameStartY,
				this.getWidth() - OUTER_FRAME_LEFT_DISTANCE - OUTER_FRAME_RIGHT_DISTANCE,
				this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - OUTER_FRAME_BOTTOM_DISTANCE);
		g2.draw(outerFrame);
		// 上方股市資訊顯示區(起點座標：同外框)
		Shape infoFrame = new Rectangle2D.Double(infoFrameStartX, infoFrameStartY,
				(this.getWidth() - OUTER_FRAME_LEFT_DISTANCE - OUTER_FRAME_RIGHT_DISTANCE), INFO_FRAME_HIGH_DISTANCE);
		g2.draw(infoFrame);
		// 右側邊框(顯示各Frame的技術指標資訊)
		Shape indexFrame = new Rectangle2D.Double(indexFrameStartX, indexFrameStartY, INDEX_FRAME_WIDTH_DISTANCE,
				(this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - OUTER_FRAME_BOTTOM_DISTANCE - INFO_FRAME_HIGH_DISTANCE));
		g2.draw(indexFrame);
		// 主要股票k線圖的底線 (即為第一個subFrame的左上角座標)
		Point2D p1 = new Point2D.Double(frameData[1].getX(), frameData[1].getY());
		Point2D p2 = new Point2D.Double(indexFrameStartX, frameData[1].getY());
		Line2D KBottomLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.BLUE);
		g2.draw(KBottomLine);

		// 其它指標視窗的底線(最後一個frame的底線不用畫)
		// System.out.println("[DrawOutlineOfFrames2D()] -- indexFrameStartX= " +
		// indexFrameStartX);
		for (int i = 1; i < frameCount; i++) {
			// System.out.println("[DrawOutlineOfFrames2D()] -- Frame[" + i + "]= " +
			// frameData[i].getX() + ", " + frameData[i].getY() + ", "
			// + frameData[i].getHeight());
			frameData[i].setHeight(subFramesTotalHighDistance / (frameCount - 1));

			Point2D p3 = new Point2D.Double(frameData[i].getX(), frameData[i].getY());
			Point2D p4 = new Point2D.Double(indexFrameStartX, frameData[i].getY());
			Line2D FramesBottomLine = new Line2D.Double(p3, p4);
			g2.setPaint(Color.BLUE);
			g2.draw(FramesBottomLine);
		}
	}

	// 記錄要顯示的資料起始位置(startDisplayRecord)及結束位置(endDisplayRecord)
	public void SetDisplayStartIndex() {
		// System.out.println("[SetDisplayStartIndex()] -- BEFORE : startDisplayRecord=
		// " + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord);
		if (endDisplayRecord == 0) {
			endDisplayRecord = stocksData.length - 1;
		}
		if ((endDisplayRecord - (mainFrameWidthDistance / kBarWidth)) >= 0) {
			startDisplayRecord = endDisplayRecord - (int) (mainFrameWidthDistance / kBarWidth) + 1;
		} else {
			startDisplayRecord = 0;
		}
		// System.out.println("[SetDisplayStartIndex()] -- AFTER : startDisplayRecord= "
		// + startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord);
	}

	// 繪製要顯示的線圖是 日線 OR 週線 OR 月線
	// stockType 日線 OR 週線 OR 月線
	// displayEndIndex
	public void DrawStockFormByStockType(Graphics g, String stockType, int displayEndIndex) {
		/*
		 * If stockType = "日線" Then // 根據每個frame所選擇要顯示的指標來繪製圖形 Call
		 * DrawFrameData(gudtStockDay, gudtIndexDay) // Display each index values on
		 * right side Call DrawStockIndexes(gudtStockDay, gudtIndexDay, gintDayIndex,
		 * displayEndIndex) ElseIf stockType = "週線" Then Call
		 * DrawFrameData(gudtStockWeek, gudtIndexWeek) Call
		 * DrawStockIndexes(gudtStockWeek, gudtIndexWeek, gintWeekIndex,
		 * displayEndIndex) ElseIf stockType = "月線" Then Call
		 * DrawFrameData(gudtStockMonth, gudtIndexMonth) Call
		 * DrawStockIndexes(gudtStockMonth, gudtIndexMonth, gintMonthIndex,
		 * displayEndIndex) End If
		 */
		if (stockType.equals("日線")) {
			DrawFrameData(g, stocksData);
			// DrawStockIndexes(gudtStockDay, gudtIndexDay, gintDayIndex, displayEndIndex)
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
		double highestPrice = LOWEST_VALUE;
		double lowestPrice = HIGHEST_VALUE;
		// System.out.println("[DrawFrameData()] -- startDisplayRecord= " +
		// startDisplayRecord + ", endDisplayRecord= " + endDisplayRecord);
		for (int i = startDisplayRecord; i <= endDisplayRecord; i++) {
			if (highestPrice < sd[i].getHighPrice())
				highestPrice = sd[i].getHighPrice();
			if (lowestPrice > sd[i].getLowPrice())
				lowestPrice = sd[i].getLowPrice();
		}
		// System.out.println("[DrawFrameData()] -- highestPrice= " + highestPrice + ",
		// lowestPrice= " + lowestPrice);

		// -- MainFrame(主要股票視窗< K-window> 視窗內畫 5 條虛線) 橫線(虛線) --//
		// 虛線起、迄點(p1, p2) : 距離外框上緣下移 10 pixels
		Point2D p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + 10);
		Point2D p2 = new Point2D.Double(mainFrameStartX + mainFrameWidthDistance, mainFrameStartY + 10);

		// 畫線
		Line2D KFrameLine = new Line2D.Double(p1, p2);
		g2.setStroke(strokeDottedLine);
		g2.setPaint(Color.red);
		g2.draw(KFrameLine);

		// Draw K圖中間的3條虛線
		double eachKFrameLineDistance = (mainFrameHighDistance - 20) / 4.0;
		for (int i = 1; i < 4; i++) {
			p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + 10 + (eachKFrameLineDistance * i));
			p2 = new Point2D.Double(mainFrameStartX + mainFrameWidthDistance, mainFrameStartY + 10 + (eachKFrameLineDistance * i));
			KFrameLine = new Line2D.Double(p1, p2);
			g2.setPaint(Color.darkGray);
			g2.draw(KFrameLine);
		}

		// 距離外框底線上移 10 pixels
		p1 = new Point2D.Double(mainFrameStartX, mainFrameStartY + mainFrameHighDistance - 10);
		// 距離外框底線上移 10 pixels
		p2 = new Point2D.Double(mainFrameStartX + mainFrameWidthDistance, mainFrameStartY + mainFrameHighDistance - 10);

		KFrameLine = new Line2D.Double(p1, p2);
		g2.setPaint(Color.red);
		g2.draw(KFrameLine);

		// -----------------------------------------------------------------------------------------

		// -- MainFrame : Draw K-Bar (2018.02.05) -- //
		System.out.println("[DrawFrameData()] -- Draw K-Bar START");
		g2.setPaint(Color.DARK_GRAY);
		for (int i = startDisplayRecord; i <= endDisplayRecord; i++) {
			double KBarStartX = 0; // K-Bar左上角的x座標
			double KBarStartY = 0; // K-Bar左上角的y座標
			double KBarHigh = 0; // 當天的K-Bar的高度
			double barHigh2 = 0; // (當天的開盤價 - 收盤價) 轉換成像素的值
			double barHigh3 = 0;
			double barHigh4 = 0;
			double eachPricePixels = (mainFrameHighDistance) / (highestPrice - lowestPrice); // 指數(or 價格)轉換成像素的值

			// -- draw K-Bar START -- //
			g2.setStroke(new BasicStroke(1));
			if (sd[i].getStartPrice() == sd[i].getEndPrice()) {
				g2.setPaint(Color.RED);
				barHigh2 = (highestPrice - sd[i].getStartPrice()) * eachPricePixels;
				// KBarStartX = mainFrameStartX + kBarWidth * (i - startDisplayRecord + 1);
				KBarStartX = mainFrameStartX + kBarWidth * (i - startDisplayRecord);
				KBarStartY = mainFrameStartY + barHigh2;
				p1 = new Point2D.Double(KBarStartX, KBarStartY);
				p2 = new Point2D.Double(KBarStartX + kBarWidth, KBarStartY);
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
					barHigh2 = (highestPrice - sd[i].getStartPrice()) * eachPricePixels;
				} else {
					g2.setPaint(Color.RED);
					KBarHigh = (sd[i].getEndPrice() - sd[i].getStartPrice()) * eachPricePixels;
					barHigh2 = (highestPrice - sd[i].getEndPrice()) * eachPricePixels;
				}
				KBarStartX = mainFrameStartX + kBarWidth * (i - startDisplayRecord);
				// KBarStartX = mainFrameStartX + kBarWidth * (i - startDisplayRecord + 1);

				KBarStartY = mainFrameStartY + barHigh2;
				Shape kFrame = new Rectangle2D.Double(KBarStartX, KBarStartY, kBarWidth, KBarHigh);
				// System.out.println("[DrawFrameData()] -- Draw K-Bar, mainFrameStartX= " +
				// (mainFrameStartX + kBarWidth * i) + ", mainFrameStartY= "
				// + (mainFrameStartY + eachPricePixels));
				// g2.draw(kFrame); // 空心
				g2.fill(kFrame); // 填滿
				// 垂直線
				barHigh3 = (highestPrice - sd[i].getHighPrice()) * eachPricePixels;
				Point2D p3 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh3);
				barHigh4 = (highestPrice - sd[i].getLowPrice()) * eachPricePixels;
				Point2D p4 = new Point2D.Double(KBarStartX + kBarWidth / 2, mainFrameStartY + barHigh4);
				Line2D KLine2 = new Line2D.Double(p3, p4);
				g2.draw(KLine2);
			}
			// System.out.println("[DrawFrameData()] -- Draw K-Bar: pos= " + i + ", X= " +
			// KBarStartX + ", value= " + sd[i].printData());

			// -- draw K-Bar END --//

			// -- 20180223 畫K線圖的垂直線(以月份來畫線) START -- //
			if (i > 0 && i < endDisplayRecord) {
				String d1 = String.valueOf(sd[i].getDate());
				String d2 = String.valueOf(sd[i - 1].getDate());
				d1 = d1.substring(d1.length() - 4, d1.length() - 2);
				d2 = d2.substring(d2.length() - 4, d2.length() - 2);
				// System.out.println("d1= " + d1 + ", d2= " + d2);
				if (!d1.equals(d2)) {
					double tradeDateStart = KBarStartX;
					double tradeDateEnd = mainFrameStartY;
					Point2D p5 = new Point2D.Double(tradeDateStart, mainFrameStartY);
					tradeDateEnd = mainFrameStartY
							+ (this.getHeight() - OUTER_FRAME_UPPER_DISTANCE - OUTER_FRAME_BOTTOM_DISTANCE - INFO_FRAME_HIGH_DISTANCE);
					Point2D p6 = new Point2D.Double(tradeDateStart, tradeDateEnd);
					Line2D KLine3 = new Line2D.Double(p5, p6);
					g2.setPaint(Color.GRAY);
					g2.setStroke(strokeDottedLine);
					g2.draw(KLine3);
					// 顯示交易日期
					g2.setPaint(Color.BLUE);
					String tradeDate = String.valueOf(sd[i].getDate());
					g2.drawString(tradeDate.substring(0, tradeDate.length() - 2), (float) tradeDateStart, (float) tradeDateEnd + 10);
					// g2.drawString(tradeDate.substring(0, tradeDate.length()), (float)
					// tradeDateStart, (float) tradeDateEnd + 10);
				}
			}
			// -- 20180223 畫K線圖的垂直線(以月份來畫線) END --//

			// 顯示最高價、最低價 (本來想用JLabel，但是不熟，而且可能影響到寫法，先用繪製文字處理) -- 20180319 --
			g2.setPaint(Color.BLUE);
			if (sd[i].getHighPrice() == highestPrice) {
				g2.drawString(Double.toString(highestPrice), (float) KBarStartX, (float) (mainFrameStartY + barHigh3 + 10));
			} else if (sd[i].getLowPrice() == lowestPrice) {
				g2.drawString(Double.toString(lowestPrice), (float) KBarStartX, (float) (mainFrameStartY + barHigh4));
			}
		}
		// 20180221 顯示K圖旁邊的指數值
		double indexValue = 0;
		DecimalFormat df = new DecimalFormat(".##");
		for (int j = 0; j < 5; j++) {
			indexValue = highestPrice - (10 + eachKFrameLineDistance * j) * (highestPrice - lowestPrice) / mainFrameHighDistance;
			g2.drawString(df.format(indexValue), 0, (float) ((mainFrameStartY + 10 + eachKFrameLineDistance * j)));
		}

		// 20180315 顯示股票資訊
		String stockInfo = "日期 " + sd[endDisplayRecord].getDate() + " 股票代號 " + sd[endDisplayRecord].getStockNo() + " 開盤價 "
				+ sd[endDisplayRecord].getStartPrice() + " 最高價 " + sd[endDisplayRecord].getHighPrice() + " 最低價 " + sd[endDisplayRecord].getLowPrice()
				+ " 收盤價 " + sd[endDisplayRecord].getEndPrice();
		g2.drawString(stockInfo, (float) (infoFrameStartX + 10), (float) (infoFrameStartY + 20));

		// 20180314 若點選查價，則顯示查價線
		if (showPriceVisibled) {
			// 20180315 calculate needs to display stock-data // 應該要用label
			int position = (int) ((cp1.getX() - mainFrameStartX) / kBarWidth);
			position += startDisplayRecord;
			if (position > endDisplayRecord) {
				position = endDisplayRecord;
			}
			stockInfo = "日期 " + sd[position].getDate() + " 股票代號 " + sd[position].getStockNo() + " 開盤價 " + sd[position].getStartPrice() + " 最高價 "
					+ sd[position].getHighPrice() + " 最低價 " + sd[position].getLowPrice() + " 收盤價 " + sd[position].getEndPrice();

			// System.out.println("===============> Draw Line: " + cp1.getX() + ", " +
			// cp1.getY() + " <> " + cp2.getX() + ", " + cp2.getY());
			// System.out.println("===============> Draw Line: mainFrameStartX= " +
			// mainFrameStartX + ", kBarWidth= " + kBarWidth + ", position= " + position);
			g2.setPaint(Color.GRAY);
			g2.fill(new Rectangle2D.Double(infoFrameStartX, infoFrameStartY,
					(this.getWidth() - OUTER_FRAME_LEFT_DISTANCE - OUTER_FRAME_RIGHT_DISTANCE), INFO_FRAME_HIGH_DISTANCE));
			g2.setPaint(Color.BLUE);
			g2.drawString(stockInfo, (float) (infoFrameStartX + 10), (float) (infoFrameStartY + 20));

			Line2D CrossLine = new Line2D.Double(cp1, cp2);
			g2.setPaint(Color.DARK_GRAY);
			g2.setStroke(strokeDottedLine);
			g2.draw(CrossLine);
		}
	}

	// 在顯示畫面的右方顯示一些技術指標的值
	// 輸入參數: gudtStock 股價資料
	// gudtIndex 技術指標資料
	// intTotalIndex 資料總筆數
	// sngIndex 目前畫面所在位置(第??筆)
	public void DrawStockIndexes() {
		/** 未實作 **/
	}

	public static void main(String[] args) {
		ApplicationFrame f = new ApplicationFrame();
		f.add(new DisplayStocksForm2());
		f.setSize(1200, 800);
		f.center();
		f.setVisible(true);
	}
}
