package tw.idv.earvin.stockpgms.javaswing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class EarvinStocksFormTest extends javax.swing.JFrame {

	public static void main(String[] args) {
		new EarvinStocksFormTest();
	}

	public EarvinStocksFormTest() {
		super("Earvin's Stocks Form");

		// 設定視窗的大小
		this.setSize(1200, 800);

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
	    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	    // Add Toolbar
	    JPanel toolbar = new JPanel();
//	    toolbar.setBounds(0,0,200,200);    
	    toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JButton testButton1 = new JButton("Test1");
	    toolbar.add(testButton1);
	    JButton testButton2 = new JButton("Test2");
	    toolbar.add(testButton2);
	    JButton testButton3 = new JButton("Test3");
	    toolbar.add(testButton3);
//	    JButton testButton4 = new JButton("Test4");
//	    testButton4.addActionListener(l);
//	    toolbar.add(testButton4);
	    
	    this.add(toolbar);  
//	    this.setLayout(new FlowLayout(FlowLayout.RIGHT));  

	    // 顯示視窗
		this.setVisible(true);

		// 視窗事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	// 於視窗上繪製字串
	public void paint(Graphics g) {
		g.drawString("For Test!", 50, 50);
	}
}
