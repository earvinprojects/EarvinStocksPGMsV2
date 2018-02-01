package tw.idv.earvin.stockpgms.javaswing;

public class FrameData {
	private double height;
	private int attribute;
	
	public FrameData() {
		height = 0;
		attribute = 0;
	}
	
	public FrameData(double h, int a) {
		setHeight(h);
		setAttribute(a);
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double v) {
		height = v;
	}
	
	public int getAttribute() {
		return attribute;
	}
	
	public void setAttribute(int v) {
		attribute = v;
	}
}
