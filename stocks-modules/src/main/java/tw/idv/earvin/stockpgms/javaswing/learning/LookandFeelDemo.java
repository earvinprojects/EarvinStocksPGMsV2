package tw.idv.earvin.stockpgms.javaswing.learning;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

public class LookandFeelDemo extends javax.swing.JFrame implements ActionListener {
	JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[7];

	String item[] = { "Metal", "CDE/Motif", "Windows XP", "Windows Classic", "Nimbus", "GTK+", "Mac" };

	String classname[] = { "javax.swing.plaf.metal.MetalLookAndFeel", "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
			"com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
			"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel",
			"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
			"com.sun.java.swing.plaf.mac.MacLookAndFeel" };

	// Main method
	public static void main(String[] args) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		try {
			// 設定Metal Look and Feel
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		new LookandFeelDemo();
	}

	// 建構函式
	// 測試用
	public LookandFeelDemo() {
		super("Look and Feel Demo");

		// 建立選單列
		JMenuBar jmenubar = createJMenuBar();

		// 定義視窗使用者介面之選單列
		this.setJMenuBar(jmenubar);

		// 設定視窗的大小
		this.setSize(250, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

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

	private JMenuBar createJMenuBar() {
		// 建立選單列
		JMenuBar jmenubar = new JMenuBar();

		// 建立選單
		JMenu jmenuFile = new JMenu("File");

		// 建立選單項目
		JMenuItem jmenuFileExit = new JMenuItem("Exit");
		// 註冊ActionListener
		jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane joptionpane = new JOptionPane();
				int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (iResult == 0) {
					System.exit(0);
				}
			}
		});
		jmenuFile.add(jmenuFileExit);

		// 建立選單
		JMenu jmenuLF = new JMenu("L & F");

		// 建構群組
		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < item.length; i++) {
			// 設定選項按鈕選單項目
			jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);

			// 設定是否啟用選單項目
			jrbmenuitem[i].setEnabled(isLookAndFeelSupported(classname[i]));

			jmenuLF.add(jrbmenuitem[i]);
			group.add(jrbmenuitem[i]);

			// 註冊ActionListener
			jrbmenuitem[i].addActionListener(this);

			if (i == 0)
				jrbmenuitem[i].setSelected(true);
		}

		// 新增選單至選單列
		jmenubar.add(jmenuFile);
		jmenubar.add(jmenuLF);

		return jmenubar;
	}

	private boolean isLookAndFeelSupported(String lnfname) {
		try {
			Class lnfclass = Class.forName(lnfname);
			javax.swing.LookAndFeel lnf = (LookAndFeel) (lnfclass.newInstance());

			// 判斷作業系統是否支援Look and Feel
			return lnf.isSupportedLookAndFeel();
		} catch (Exception e) {
			return false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			for (int i = 0; i < item.length; i++) {
				if (e.getActionCommand().equals(item[i])) {
					// 設定Look and Feel
					UIManager.setLookAndFeel(classname[i]);

					this.setTitle(item[i] + " L & F");
				}
			}
		} catch (Exception ex) {
		}

		// 執行階段變更Look and Feel
		SwingUtilities.updateComponentTreeUI(this);
	}
}
