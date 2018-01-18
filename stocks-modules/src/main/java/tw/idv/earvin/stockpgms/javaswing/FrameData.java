package tw.idv.earvin.stockpgms.javaswing;

public class FrameData {
	private int height;
	private int attribute;
	
	public FrameData() {
		height = 0;
		attribute = 0;
	}
	
	public FrameData(int h, int a) {
		setHeight(h);
		setAttribute(a);
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int v) {
		height = v;
	}
	
	public int getAttribute() {
		return attribute;
	}
	
	public void setAttribute(int v) {
		attribute = v;
	}
}
