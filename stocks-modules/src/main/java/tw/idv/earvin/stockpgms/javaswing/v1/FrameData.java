package tw.idv.earvin.stockpgms.javaswing.v1;

public class FrameData {
	private double height;
	private int attribute;
	private double x;
	private double y;
	
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
	
	public double getX() {
		return x;
	}
	
	public void setX(double v) {
		x = v;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double v) {
		y = v;
	}
}
