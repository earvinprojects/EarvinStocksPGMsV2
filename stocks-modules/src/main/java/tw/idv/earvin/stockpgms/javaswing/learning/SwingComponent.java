package tw.idv.earvin.stockpgms.javaswing.learning;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Description: <br/>
 * 網站: <a href="http://www.crazyit.org">瘋狂Java聯盟</a> <br/>
 * Copyright (C), 2001-2016, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class SwingComponent {
	JFrame f = new JFrame("測試");
	// 定義一個按鈕,並為之指定圖示
	Icon okIcon = new ImageIcon("ico/ok.png");
	JButton ok = new JButton("確認", okIcon);
	// 定義一個單選按鈕，初始處於選取狀態
	JRadioButton male = new JRadioButton("男", true);
	// 定義一個單選按鈕，初始處於沒有選取狀態
	JRadioButton female = new JRadioButton("女", false);
	// 定義一個ButtonGroup，用於將上面兩個JRadioButton組合在一起
	ButtonGroup bg = new ButtonGroup();
	// 定義一個核取方塊，初始處於沒有選取狀態。
	JCheckBox married = new JCheckBox("是否已婚？", false);
	String[] colors = new String[] { "紅色", "綠色", "藍色" };
	// 定義一個下拉選擇框
	JComboBox<String> colorChooser = new JComboBox<>(colors);
	// 定義一個列表選擇框
	JList<String> colorList = new JList<>(colors);
	// 定義一個8列、20欄的多行文字區域
	JTextArea ta = new JTextArea(8, 20);
	// 定義一個40欄的單行文字區域
	JTextField name = new JTextField(40);
	JMenuBar mb = new JMenuBar();
	JMenu file = new JMenu("檔案");
	JMenu edit = new JMenu("編輯");
	// 建立「新增」選單項目，並為之指定圖示
	Icon newIcon = new ImageIcon("ico/new.png");
	JMenuItem newItem = new JMenuItem("新增", newIcon);
	// 建立「儲存」選單項目，並為之指定圖示
	Icon saveIcon = new ImageIcon("ico/save.png");
	JMenuItem saveItem = new JMenuItem("儲存", saveIcon);
	// 建立「結束」選單項目，並為之指定圖示
	Icon exitIcon = new ImageIcon("ico/exit.png");
	JMenuItem exitItem = new JMenuItem("結束", exitIcon);
	JCheckBoxMenuItem autoWrap = new JCheckBoxMenuItem("自動換行");
	// 建立「複製」選單項目，並為之指定圖示
	JMenuItem copyItem = new JMenuItem("複製", new ImageIcon("ico/copy.png"));
	// 建立「貼上」選單項目，並為之指定圖示
	JMenuItem pasteItem = new JMenuItem("貼上", new ImageIcon("ico/paste.png"));
	JMenu format = new JMenu("格式");
	JMenuItem commentItem = new JMenuItem("註解");
	JMenuItem cancelItem = new JMenuItem("取消註解");
	// 定義一個右鍵選單用於設置程式風格
	JPopupMenu pop = new JPopupMenu();
	// 用於組合三個風格選單項目的ButtonGroup
	ButtonGroup flavorGroup = new ButtonGroup();
	// 建立五個單選框按鈕，用於設定程式的外觀風格
	JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("Metal風格", true);
	JRadioButtonMenuItem nimbusItem = new JRadioButtonMenuItem("Nimbus風格");
	JRadioButtonMenuItem windowsItem = new JRadioButtonMenuItem("Windows風格");
	JRadioButtonMenuItem classicItem = new JRadioButtonMenuItem("Windows經典風格");
	JRadioButtonMenuItem motifItem = new JRadioButtonMenuItem("Motif風格");

	// -----------------用於執行介面初始化的init方法---------------------
	public void init() {
		// 建立一個裝載了文字方塊、按鈕的JPanel
		JPanel bottom = new JPanel();
		bottom.add(name);
		bottom.add(ok);
		f.add(bottom, BorderLayout.SOUTH);
		// 建立一個裝載了下拉選擇框、三個JCheckBox的JPanel
		JPanel checkPanel = new JPanel();
		checkPanel.add(colorChooser);
		bg.add(male);
		bg.add(female);
		checkPanel.add(male);
		checkPanel.add(female);
		checkPanel.add(married);
		// 建立一個垂直排列元件的Box，盛裝多行文字區域JPanel
		Box topLeft = Box.createVerticalBox();
		// 使用JScrollPane作為普通元件的JViewPort
		JScrollPane taJsp = new JScrollPane(ta); // ⑤
		topLeft.add(taJsp);
		topLeft.add(checkPanel);
		// 建立一個水平排列元件的Box，盛裝topLeft、colorList
		Box top = Box.createHorizontalBox();
		top.add(topLeft);
		top.add(colorList);
		// 將top Box容器添加到視窗的中間
		f.add(top);
		// -----------下面開始組合選單、並為選單添加監聽器----------
		// 為newItem設置快捷鍵，設置快捷鍵時要使用大寫字母
		newItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK)); // ①
		newItem.addActionListener(e -> ta.append("使用者單擊了「新增」選單\n"));
		// 為file選單添加選單項目
		file.add(newItem);
		file.add(saveItem);
		file.add(exitItem);
		// 為edit選單添加選單項目
		edit.add(autoWrap);
		// 使用addSeparator方法來添加選單分隔線
		edit.addSeparator();
		edit.add(copyItem);
		edit.add(pasteItem);
		// 為commentItem元件添加提示資訊
		commentItem.setToolTipText("將程式碼註解起來！");
		// 為format選單添加選單項目
		format.add(commentItem);
		format.add(cancelItem);
		// 使用添加new JMenuItem("-")的方式不能添加選單分隔符
		edit.add(new JMenuItem("-"));
		// 將format選單組合到edit選單中，從而形成二級選單
		edit.add(format);
		// 將file、edit選單添加到mb選單列中
		mb.add(file);
		mb.add(edit);
		// 為f視窗設置選單列
		f.setJMenuBar(mb);
		// -----------下面開始組合右鍵選單、並安裝右鍵選單----------
		flavorGroup.add(metalItem);
		flavorGroup.add(nimbusItem);
		flavorGroup.add(windowsItem);
		flavorGroup.add(classicItem);
		flavorGroup.add(motifItem);
		pop.add(metalItem);
		pop.add(nimbusItem);
		pop.add(windowsItem);
		pop.add(classicItem);
		pop.add(motifItem);
		// 為5個風格選單建立事件監聽器
		ActionListener flavorListener = e -> {
			try {
				switch (e.getActionCommand()) {
				case "Metal風格":
					changeFlavor(1);
					break;
				case "Nimbus風格":
					changeFlavor(2);
					break;
				case "Windows風格":
					changeFlavor(3);
					break;
				case "Windows經典風格":
					changeFlavor(4);
					break;
				case "Motif風格":
					changeFlavor(5);
					break;
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		};
		// 為五個風格選單項目添加事件監聽器
		metalItem.addActionListener(flavorListener);
		nimbusItem.addActionListener(flavorListener);
		windowsItem.addActionListener(flavorListener);
		classicItem.addActionListener(flavorListener);
		motifItem.addActionListener(flavorListener);
		// 呼叫該方法即可設置右鍵選單，無須使用事件機制
		ta.setComponentPopupMenu(pop); // ④
		// 設置關閉視窗時，結束程式
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	// 定義一個方法，用於改變介面風格
	private void changeFlavor(int flavor) throws Exception {
		switch (flavor) {
		// 設置Metal風格
		case 1:
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			break;
		// 設置Nimbus風格
		case 2:
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			break;
		// 設置Windows風格
		case 3:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			break;
		// 設置Windows經典風格
		case 4:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			break;
		// 設置Motif風格
		case 5:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			break;
		}
		// 更新f視窗內頂級容器以及內部所有元件的UI
		SwingUtilities.updateComponentTreeUI(f.getContentPane()); // ②
		// 更新mb選單列以及內部所有元件的UI
		SwingUtilities.updateComponentTreeUI(mb);
		// 更新pop右鍵選單以及內部所有元件的UI
		SwingUtilities.updateComponentTreeUI(pop);
	}

	public static void main(String[] args) {
		// 設置Swing視窗使用Java風格
		// JFrame.setDefaultLookAndFeelDecorated(true); //③
		new SwingComponent().init();
	}
}
