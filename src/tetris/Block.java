package tetris;

public class Block {
	private int X;
	private int Y;
	
	public void setXY(int x, int y) {
		this.X = x;
		this.Y = y;
		
		return;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
}