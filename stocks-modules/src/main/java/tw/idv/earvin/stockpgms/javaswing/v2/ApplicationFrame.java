package tw.idv.earvin.stockpgms.javaswing.v2;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ApplicationFrame extends JFrame {
	final public int APP_FRAME_WIDTH = 1200;
	final public int APP_FRAME_HIGHT = 800;
	
	public ApplicationFrame() {
		this("ApplicationFrame v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public ApplicationFrame(String title) {
		super(title);
		createUI();
	}

	protected void createUI() {
		setSize(APP_FRAME_WIDTH, APP_FRAME_HIGHT);
		center();
	}

	public void center() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;
		setLocation(x, y);
	}
}