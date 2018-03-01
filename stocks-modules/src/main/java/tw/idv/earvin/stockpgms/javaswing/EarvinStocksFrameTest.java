package tw.idv.earvin.stockpgms.javaswing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class EarvinStocksFrameTest extends JFrame {


	public EarvinStocksFrameTest(String title) {
		super(title);
		createUI();
	}

	protected void createUI() {
		setSize(1200, 800);
		center();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void center() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);
	}
}
