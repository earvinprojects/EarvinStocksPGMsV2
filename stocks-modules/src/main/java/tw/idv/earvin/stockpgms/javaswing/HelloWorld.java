package tw.idv.earvin.stockpgms.javaswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//繼承javax.swing.JFrame
public class HelloWorld extends javax.swing.JFrame {

	public static void main(String args[]) {
		new HelloWorld();
	}

	// 建構函式
	public HelloWorld() {
		// 設定視窗標題
		super("Hello World");

		// 設定視窗的大小
		this.setSize(200, 200);

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

		// 顯示視窗
		this.setVisible(true);

		// 視窗事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}