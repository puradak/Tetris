package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play_gui {
	
	final private int SIZE_X = 40;
	final private int SIZE_Y = 40;

	final private int LEFT = 0;
	final private int RIGHT = 1;
	
	final private int LEFT_LIMIT = 0;
	final private int RIGHT_LIMIT = 360;
	
	private int BACKSIZE_X = 400/SIZE_X;
	private int BACKSIZE_Y = 800/SIZE_Y;
	
	private int current_coord_X = 160;
	private int current_coord_Y = 0;
	
	private Block blocks[] = new Block[4];
	// 블럭 뭉치
	private int blockIndex;
	
	private Graphics painter;
	
	private Random rand = new Random();
	
	private int location[][] = new int[BACKSIZE_Y+1][BACKSIZE_X];
	// 블럭 위치 저장  : 메서드가 필요
	
	 private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}},	// 일자
			{{1,1,0},{0,1,1}},	// ㄹ자
			{{0,1,1},{1,1,0}},	// 역 ㄹ자
			{{1,1},{1,1}},	// ㅁ자
			{{1,1,1},{0,0,1}},	// ㄱ자
			{{1,1,1},{1,0,0}},	// 역 ㄱ자
			{{0,1,0},{1,1,1}}	// ㅗ자
	}; 
	// 7개
	
	 private int selectedShape[][];
	
	 private Color color[] = {
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.CYAN,
			Color.BLUE, //남색
			new Color(153,102,255)  //보라색
	};
	// 7개
	private JFrame frame = new JFrame("Tetris");
	private JPanel playPanel = new JPanel();
	private JPanel scorePanel = new JPanel();
	
	public Play_gui() {
		
		frame.setSize(600,800);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		playPanel.setLayout(null);
		playPanel.setBounds(0,0,400,800);
		playPanel.setBackground(Color.LIGHT_GRAY);
		frame.add(playPanel);
		
		scorePanel.setLayout(null);
		scorePanel.setBounds(400,0,200,800);
		scorePanel.setBackground(Color.GRAY);
		frame.add(scorePanel);
		
		painter = playPanel.getGraphics();
		
		//rotationRight();
		
		for(int i=0; i<location[20].length; i++) {
			location[20][i] = 1;
		}
		
		
		playPanel.paint(painter);
		initBlock();
		printBlock();
		
		TimerTask moveDown = new TimerTask() {
			@Override
			public void run() {
				
				boolean hitFlag = false;
				
				for(Block block : blocks) {
					if(location[block.getY()/40 + 2][block.getX()/40] == 1) {
						for(Block result : blocks) {
							location[result.getY()/40 + 1][result.getX()/40] = 1;
						}
						hitFlag = true;
						current_coord_X = 160;
						current_coord_Y = 0;
						break;
					}
				}
				// 구조물이나 땅에 닿은 경우, location에 위치정보 기록
				
				if(hitFlag) {
					initBlock();
					hitFlag = false;
				}
				// 구조물이나 땅에 닿은 경우, 블럭들의 좌표를 초기 위치로 설정함.
				for(Block block : blocks) {
					block.setXY(block.getX(), block.getY() + 10);
				}
				printBlocks();
				// 블럭과 구조물 출력
			}
		};
		
		Timer moveManager = new Timer("timer");
		moveManager.schedule(moveDown, 50, 100);
		
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				playPanel.paint(painter);
				printBlocks();
				
				if(e.getKeyChar() == 'a') {
					move(LEFT);
				}
				if(e.getKeyChar() == 'd') {
					move(RIGHT);
				}
				if(e.getKeyChar() == ' ') {
					rotateRight();
				}
			}
		});
	}
	
	private void addBlockCoord(int x, int y) {
		for(Block block : blocks) {
			block.addXY(x, y);
		}
	}
	// 테트리스 블럭의 위치를 조정한다.
	
	private void setBlockShape() {
    	int row = -1; int col = -1;
    	int count = 0;
    	for(int[] hor : selectedShape) {
    		col++;
    		row = -1;
    		for(int check : hor) {
    			row++;
    			if(check == 1) {
    				blocks[count] = new Block(SIZE_X*row+current_coord_X, SIZE_Y*col+current_coord_Y, color[blockIndex]);
    				count++;
    			}
    			else continue;
    		}
    	}
    }
    // 테트리스 블럭을 구성한다.
	
	private void initBlock() {
   		blockIndex = rand.nextInt(7);
		selectedShape = shape[blockIndex];
		setBlockShape();
	}
	
	
	// 테트리스 블럭의 모양을 랜덤으로 설정한다.
	// 블럭의 위치를 초기 위치로 설정한다.
	
	
	private void rotateRight() {
	// 2차원 배열을 90도 회전하기
		int[][] rotated = new int[selectedShape[0].length][selectedShape.length];
		for(int i=0; i<selectedShape[0].length; i++) {
			// 오른쪽으로 90도 회전
			for(int j=0; j<selectedShape.length; j++) {
				rotated[i][j] = selectedShape[selectedShape.length-1-j][i];
				System.out.print(rotated[i][j]);
			}
			System.out.println();
		}
		
		System.out.println();
		
		current_coord_X = blocks[0].getX();
		current_coord_Y = blocks[0].getY();
		
		selectedShape = rotated;

		addBlockCoord(current_coord_X,0);
		setBlockShape();
	}
	
	
	
	private void printBackground() {
		for(int i=0; i<location.length; i++) {
			for(int j=0; j<location[i].length; j++) {
				if(location[i][j] == 1) {
					painter.setColor(new Color(51,51,51));
					painter.fillRect(SIZE_X*j, SIZE_Y*(i-1), SIZE_X, SIZE_Y);
					painter.setColor(Color.BLACK);
					painter.drawRect(SIZE_X*j, SIZE_Y*(i-1), SIZE_X, SIZE_Y);
				}
				
			}
		}
	}
	// 배경에 등록된 블럭을 그린다.
	
	private void printBlock(/*blocks 이용*/) {
		for(Block block : blocks) {
			painter.setColor(block.getColor());
			painter.fillRect(block.getX(), block.getY(), SIZE_X, SIZE_Y);
		}
		for(Block block : blocks) {
			painter.setColor(Color.BLACK);
			painter.drawRect(block.getX(), block.getY(), SIZE_X, SIZE_Y);
		}
    }
	// 테트리스 블럭을 그린다.
		
	private void printBlocks(){
		playPanel.paint(painter);
		printBackground();
		printBlock();
	}
	// 배경과 테트리스 블럭을 그린다.
	
	private void move(int direction/*blocks 이용*/) {
		int x = SIZE_X;
		if(direction == LEFT) x *= -1;
		
		for(Block block : blocks) {
			if(block.getX()+x < LEFT_LIMIT || block.getX()+x > RIGHT_LIMIT) return;
			if(location[block.getY()/SIZE_Y][(block.getX()+x)/SIZE_X] == 1) return;
		}
		
		for(Block block : blocks) {
			block.addXY(x, 0);
		}
	}
	// blocks의 모든 블럭에 대해 x좌표를 조정한다.

}
