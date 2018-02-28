package tw.idv.earvin.stockpgms.javaswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EarvinStocksFormTest2 {
	JFrame stockFrame = new JFrame("Earvin's Stock Form");
	JButton testButton1 = new JButton("Test1");
	JButton testButton2 = new JButton("Test2");
	JButton testButton3 = new JButton("Test3");
	JButton testButton4 = new JButton("Test4");
	JLabel FrameCounts = new JLabel("選擇方框數：");
	JComboBox SelectFrameCounts = new JComboBox();
	DisplayStocksForm mainForm = null;

	public void init() {
		// 視窗事件 (20180107 事件的寫法有3種，但是我有點忘了~~")
		stockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		stockFrame.getContentPane().setLayout(new BorderLayout());
		// 設定視窗的大小
		stockFrame.setSize(1200, 800);
		// 取得螢幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println("[EarvinStocksFormTest()] screen Height= " + screenSize.getHeight() + ", screen width= " + screenSize.getWidth());
		// 取得視窗大小
		Dimension frameSize = stockFrame.getSize();
//		System.out.println("[EarvinStocksFormTest()] Frame Height= " + frameSize.getHeight() + ", Frame width= " + frameSize.getWidth());
		// 比較螢幕與視窗的高度
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		// 比較螢幕與視窗的寬度
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		// 將視窗定位於螢幕中央
		stockFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		JPanel toolbar = new JPanel();
		toolbar.setBackground(Color.YELLOW);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(testButton1);
		toolbar.add(testButton2);
		toolbar.add(testButton3);
		toolbar.add(testButton4);
		toolbar.add(FrameCounts);
			
		for (int i = 1; i <= 10; i++) {
			SelectFrameCounts.addItem(i);
		}
		SelectFrameCounts.setSelectedIndex(5);	// 預設6個subFrame
		
		SelectFrameCounts.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
//	          System.out.println("Selected index=" + SelectFrameCounts.getSelectedIndex() + " Selected item=" + SelectFrameCounts.getSelectedItem());
	        	mainForm.frameCount = (int) SelectFrameCounts.getSelectedItem();
	          SwingUtilities.updateComponentTreeUI(mainForm);
	        }
	      });			
		toolbar.add(SelectFrameCounts);
		
		stockFrame.add(toolbar, BorderLayout.NORTH);
		mainForm = new DisplayStocksForm(SelectFrameCounts.getSelectedItem());
		stockFrame.add(mainForm, BorderLayout.CENTER);
//		stockFrame.pack();
		stockFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new EarvinStocksFormTest2().init();
	}
}
