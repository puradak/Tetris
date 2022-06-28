package block;

public class Block {

	private boolean[][] formation;
	private int centerX;
	private int centerY;
	
	public boolean[][] getFormation() {
		return formation;
	}
	public int getX() {
		return this.centerX;
	}
	public int getY() {
		return this.centerY;
	}
	
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	
}
