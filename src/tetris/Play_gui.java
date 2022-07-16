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
	
	static final private int SIZE_X = 40;
	static final private int SIZE_Y = 40;

	static final private int LEFT = 0;
	static final private int RIGHT = 1;
	
	static final private int LEFT_LIMIT = 0;
	static final private int RIGHT_LIMIT = 360;
	
	static private int BACKSIZE_X = 400/SIZE_X;
	static private int BACKSIZE_Y = 800/SIZE_Y;

	static private int init_coord_X = 4*SIZE_X;
	static private int init_coord_Y = 0;
	
	static private Block blocks[] = new Block[4];
	// 블럭 뭉치
	static private Graphics painter;
	
	static private Random rand = new Random();
	
	static private int blockColumn = 2;        // 블럭이 차지하는 n*m 공간이 회전 될 때마다 세로가 2, 4 중 하나로 변경되므로 그 정보를 저장
	static private int blockStart[] = {1, 2};  // 블럭이 차지하는 n*m 좌상단 좌표를 항상 기억
	
	static private int location[][] = new int[BACKSIZE_Y+1][BACKSIZE_X];
	// 블럭 위치 저장  : 메서드가 필요
	
	static private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}},	// 일자
			{{1,1,0,0},{0,1,1,0}},	// ㄹ자
			{{0,1,1,0},{1,1,0,0}},	// 역 ㄹ자
			{{1,1,0,0},{1,1,0,0}},	// ㅁ자
			{{1,1,1,0},{0,0,1,0}},	// ㄱ자
			{{1,1,1,0},{1,0,0,0}},	// 역 ㄱ자
			{{0,1,0,0},{1,1,1,0}}	// ㅗ자
	}; 
	// 7개
	
	static private Color color[] = {
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
		setBlockShape(rand.nextInt(7));
		printBlock();
		
		TimerTask moveDown = new TimerTask() {
			@Override
			public void run() {
				
				boolean hitFlag = false;
				
				for(Block block : blocks) {
					if(location[block.getY()/40 + 2][block.getX()/40] == 1) {
						for(Block result :blocks) {
							location[result.getY()/40 + 1][result.getX()/40] = 1;
						}
						hitFlag = true;
						break;
					}
				}
				// 구조물이나 땅에 닿은 경우, location에 위치정보 기록
				
				if(hitFlag) {
					setBlockShape(rand.nextInt(7));
					hitFlag = false;
				}
				// 구조물이나 땅에 닿은 경우, 블럭들의 좌표를 초기 위치로 설정함.
				for(Block block : blocks) {
					block.setXY(block.getX(), block.getY() + 8);
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
			}
		});
	}
	
	// 2차원 배열을 시계방향으로 90도 회전하기
	// 회전이 되는 축: blockStart[0], blockStart[1]
	// blocks[]에 현재 선택된 블럭의 객체가 들어있음 -> 객체가 자리하는 위치를 n*m 배열로 나타낸 뒤, 회전시켜서 m*n 배열로 변경
	// 변경됨 m*n 배열에서 자리를 차지하고 있는 부분이 객체의 위치이므로 객체의 x, y 좌표 정보를 회전 이후 좌표로 변경
	static public void rotationRight() {
		int testXY[][] = {{1, 2}, {1,3}, {2, 3}, {2, 4}};
		int change[][] = new int[(blockColumn % 4) + 2][blockColumn];
		
    	for(int i = 0; i < 4; i++) {
    		// int changeX = blocks[i].getY() + blockStart[0] - blockStart[1];
    		// int changeY = blockColumn - 1 - blocks[i].getX() + blockStart[0] + blockStart[1];
    		// blocks[i].setXY(changeX, changeY);
    		
    		change[i][0] = testXY[i][1] + blockStart[0] - blockStart[1];
    		change[i][1] = blockColumn - 1 - testXY[i][0] + blockStart[0] + blockStart[1];
    	}
    	blockColumn = (blockColumn % 4) + 2; // 회전 되었으므로 세로 길이 변경
    	
        /*int[][] ret = new int[key.length][key[0].length];
        for(int i=0; i<key[0].length; i++) {
            for(int j=0; j<key.length; j++) {
                // 오른쪽으로 90도 회전
                ret[i][j] = key[key.length-1-j][i];
            }
        }*/
    }
	
	private void setBlockShape(int shapeNum) {
		blockColumn = 2; // 블럭의 세로 범위는 2로 초기값을 줌. 블럭 생성 시마다 초기화
    	int[][] selected = shape[shapeNum];
    	int row = -1; int col = -1;
    	int count = 0;
    	for(int[] hor : selected) {
    		col++;
    		row = -1;
    		for(int check : hor) {
    			row++;
    			if(check == 1) {
    				blocks[count] = new Block(SIZE_X*row+init_coord_X,SIZE_Y*col+init_coord_Y, color[shapeNum]);
    				count++;
    			}
    			else continue;
    		}
    	}
    }
    // 테트리스 블럭을 구성한다.
	
	private void printBlocks(){
		playPanel.paint(painter);
		printBackground();
		printBlock();
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
		
	
	private void move(int direction/*blocks 이용*/) {
		int x = SIZE_X;
		if(direction == LEFT) x *= -1;
		
		for(Block block : blocks) {
			if(block.getX()+x < LEFT_LIMIT || block.getX()+x > RIGHT_LIMIT) return;
			if(location[block.getY()/SIZE_Y][(block.getX()+x)/SIZE_X] == 1) return;
		}
		
		for(Block block : blocks) {
			block.setXY(block.getX()+x, block.getY());
		}
	}
	// blocks의 모든 블럭에 대해 x좌표를 조정한다.
	
	private void move() {
		
	}
	// blocks의 모든 블럭에 대해 y좌표를 조정한다.
	// 최대한 내려갈 수 있는 데 까지 내려간다.
	// 미완

}
