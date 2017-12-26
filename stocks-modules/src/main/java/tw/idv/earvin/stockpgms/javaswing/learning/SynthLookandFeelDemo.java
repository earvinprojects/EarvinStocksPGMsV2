package tw.idv.earvin.stockpgms.javaswing.learning;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Synth Look and Feel
import javax.swing.plaf.synth.*;

public class SynthLookandFeelDemo extends javax.swing.JFrame {
	// Main method
	public static void main(String[] args) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		Toolkit.getDefaultToolkit().setDynamicLayout(true);

		// try {
		// UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
		// }
		// catch(Exception e) {
		// e.printStackTrace();
		// }

		new SynthLookandFeelDemo();
	}

	// 建構函式
	public SynthLookandFeelDemo() {
		super("Synth Look and Feel Demo");

		// 建立SynthLookAndFeel
		SynthLookAndFeel laf = new SynthLookAndFeel();

		try {
			// 載入Synth Look and Feel的XML檔案至Java程式
//			laf.load(new java.net.URL("file:///C:/synth.xml"));
			laf.load(new java.net.URL("file:///C:/WORKSPACES/EarvinStocksPGMsV2/stocks-modules/src/main/java/tw/idv/earvin/stockpgms/javaswing/synth.xml"));

			// 設定Synth Look and Feel
			UIManager.setLookAndFeel(laf);
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println("不支援Synth Look and Feel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 取得目前之Class Loader
		ClassLoader cl = this.getClass().getClassLoader();
//		System.out.println(cl.getClass().getResource("tw/idv/earvin/stockpgms/javaswing/images/").getPath());

		// 設定視窗圖示
		this.setIconImage(new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/java.png")).getImage());

		// 定義 Layout Manager 為 FlowLayout
		this.setLayout(new FlowLayout());

		JButton[] jbutton = new JButton[6];

		// 純文字標籤
		jbutton[0] = new JButton("Plain Text");
		// 以字元設定按鍵助記碼
		jbutton[0].setMnemonic('l');

		// HTML標籤
		jbutton[1] = new JButton("<html><font face=Verdana size=2>HTML Text</font></html>");

		// 純文字標籤
		jbutton[2] = new JButton("Default Button");
		// 以字元設定按鍵助記碼
		jbutton[2].setMnemonic('B');

		// 設定預設按鈕
		this.getRootPane().setDefaultButton(jbutton[2]);

		// 設定Normal Icon之按鈕
		jbutton[3] = new JButton("Image Button", new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/duke.gif")));
		// 以字元設定按鍵助記碼
		jbutton[3].setMnemonic('I');

		// 設定Pressed Icon之按鈕
		jbutton[4] = new JButton("Pressed Button", new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/duke.gif")));
		// 以字元設定按鍵助記碼
		jbutton[4].setMnemonic('P');
		// 設定標籤字串相對於圖像的水平位置
		jbutton[4].setHorizontalTextPosition(SwingConstants.CENTER);
		// 設定標籤字串相對於圖像的垂直位置
		jbutton[4].setVerticalTextPosition(SwingConstants.BOTTOM);
		// 設定按下時的圖像
		jbutton[4].setPressedIcon(new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/press.gif")));

		// 設Rollover Icon之按鈕
		jbutton[5] = new JButton("Rollover Button");
		// 以字元設定按鍵助記碼
		jbutton[5].setMnemonic('R');
		// 設定標籤字串相對於圖像的水平位置
		jbutton[5].setHorizontalTextPosition(SwingConstants.CENTER);
		// 設定標籤字串相對於圖像的垂直位置
		jbutton[5].setVerticalTextPosition(SwingConstants.BOTTOM);
		// 設定是否啟用滑鼠移至按鈕上方時的Rollover效果
		jbutton[5].setRolloverEnabled(true);
		// 設定按鈕正常時的圖像
		jbutton[5].setIcon(new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/swing.gif")));
		// 設定滑鼠移至按鈕上方時的圖像
		jbutton[5].setRolloverIcon(new ImageIcon(cl.getResource("tw/idv/earvin/stockpgms/javaswing/images/rollover.gif")));

		for (int i = 0; i < jbutton.length; i++)
			this.add(jbutton[i]);

		// 設定視窗的大小
		this.setSize(300, 300);

		// Center the frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();

		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;

		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;

		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		// 顯示視窗
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
