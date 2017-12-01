package tw.idv.earvin.stockpgms.javaswing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

public class LookandFeelDemo_4_8 extends javax.swing.JFrame {
	  // Main method
	  public static void main(String[] args) {
	    new LookandFeelDemo_4_8();
	  }

	  // 建構函式
	  // 測試用
	  public LookandFeelDemo_4_8() {
	    super("Look and Feel Demo");

	    // 建立選單列
		JMenuBar jmenubar = createJMenuBar();

	    // 定義視窗使用者介面之選單列
	    this.setJMenuBar(jmenubar);

	    // 設定視窗的大小
	    this.setSize(800,500);
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
	        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        
	        if (iResult == 0) {
	          System.exit(0);
	        }
	      }
	    });
	    jmenuFile.add(jmenuFileExit);

	    // 新增選單至選單列
	    jmenubar.add(jmenuFile);

	    return jmenubar;
	  }
}
