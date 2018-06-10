package tw.idv.earvin.stockpgms.javaswing.learning;

import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class FlowLayoutDemo extends javax.swing.JFrame {
	// 建構函式
	public FlowLayoutDemo() {
		init();
	}

	public void init() {
		// 直接定義Layout Manager為FlowLayout
		this.setLayout(new FlowLayout());

		JButton jbutton1 = new JButton("OK");
		JButton jbutton2 = new JButton("Cancel");
		JButton jbutton3 = new JButton("Yes");
		JButton jbutton4 = new JButton("No");

		// 將物件直接加至JApplet中
		this.add(jbutton1);
		this.add(jbutton2);
		this.add(jbutton3);
		this.add(jbutton4);
	}

	static {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		// JDialog.setDefaultLookAndFeelDecorated(true);
		// JFrame.setDefaultLookAndFeelDecorated(true);
		// Toolkit.getDefaultToolkit().setDynamicLayout(true);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
		}
	}

	// Main method
	public static void main(String[] args) {
		new FlowLayoutDemo();
	}
}
