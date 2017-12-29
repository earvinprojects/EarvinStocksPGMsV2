package tw.idv.earvin.stockpgms.javaswing;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

import javax.swing.*;

public class EarvinStocksFormTest extends javax.swing.JFrame {

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
		f.setVisible(true);
	}

	public EarvinStocksFormTest() {
		super("Earvin's Stocks Form");

		// 設定視窗的大小
		// this.setSize(1200, 800);

		// 取得螢幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// 取得視窗大小
		Dimension frameSize = this.getSize();

		// 比較螢幕與視窗的高度
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;

		// 比較螢幕與視窗的寬度
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;

		// 將視窗定位於螢幕中央
//		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		this.setLocation(200, 100);

		// JDK 5.0以後，使用swing的寫法~~
		// Add Toolbar
		JPanel toolbar = new JPanel();
		toolbar.setBackground(Color.GRAY);
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
		add(toolbar, BorderLayout.NORTH);
		// 顯示股票線圖
		add(new DisplayStocksForm(), BorderLayout.CENTER);

		// 視窗事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}


	class DisplayStocksForm extends Canvas {
		public void paint(Graphics g) {
			g.drawString("Hello", 140, 140);
			setBackground(Color.WHITE);
			g.fillRect(130, 130, 100, 80);
			g.drawOval(130, 130, 50, 60);
			setForeground(Color.RED);
			g.fillOval(130, 130, 50, 60);
			g.drawArc(310, 200, 40, 50, 90, 60);
			g.fillArc(310, 130, 40, 50, 180, 40);

		}
	}
}
