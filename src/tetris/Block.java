package tetris;

import java.awt.Color;

public class Block {
	
	
	public Block(int x, int y, Color c) {
		this.X = x;
		this.Y = y;
		this.color = c;
	}
	private int X;
	private int Y;
	// 중심 좌표 : 사각형 생성 위치는 중심-20
	private Color color;
	
	
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}