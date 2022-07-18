package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Play_gui {
	
	final private int SIZE_X = 40;					// 블럭의 x길이
	final private int SIZE_Y = 40;					// 블럭의 y길이
    final private int BACKSIZE_X = 400/SIZE_X;		// 배경 격자 가로 한 줄의 길이
	final private int BACKSIZE_Y = 800/SIZE_Y;		// 배경 격자 세로 한 줄의 길이
	final private int LEFT = 0;						// 왼쪽 이동을 0이라고 지정함
	final private int RIGHT = 1;					// 오른쪽 이동을 1이라고 지정함
	final private int LEFT_LIMIT = 0;				// 화면의 왼쪽 경계
	final private int RIGHT_LIMIT = 360;			// 화면의 오른쪽 경계
	
	private int current_coord_X = 160;				// 최초 생성 x위치 및 블럭의 x위치 기록
	private int current_coord_Y = 80;				// 최초 생성 y위치 및 블럭의 y위치 기록
	
	private Block blocks[] = new Block[4];			// 블럭 뭉치 배열
	private int blockIndex;							// 몇 번째 블럭인지를 나타내는 인덱스 값
	
	private Graphics painter;						// playPanel에 그림을 그릴 객체
	
	private Random rand = new Random();				// 랜덤 값을 반환할 객체
	
	private int location[][] = new int[BACKSIZE_Y+1][BACKSIZE_X];	// 배경 배열
	// 블럭 위치 저장  : 메서드가 필요
	
	private int shape[][][] = {						// 블럭 모양 정보를 담은 배열
			{{1,1,1,1},{0,0,0,0}},	// 일자
			{{1,1,0},{0,1,1}},	// ㄹ자
			{{0,1,1},{1,1,0}},	// 역 ㄹ자
			{{1,1},{1,1}},	// ㅁ자
			{{1,1,1},{0,0,1}},	// ㄱ자
			{{1,1,1},{1,0,0}},	// 역 ㄱ자
			{{0,1,0},{1,1,1}}	// ㅗ자
	}; 
	// 7개
	
	 private int selectedShape[][];					// blockIndex에 따라 선택된 배열 저장, 회전할 때 이용됨
	
	 private Color color[] = {						// 색 정보를 담은 배열
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.CYAN,
			Color.BLUE, //남색
			new Color(153,102,255)  //보라색
	};
	// 7개
	private JFrame frame = new JFrame("Tetris");	// gui 프레임
	private JPanel playPanel = new JPanel();		// 테트리스 게임이 진행되는 패널
	private JPanel scorePanel = new JPanel();		// 점수 등 기타 정보가 출력되는 패널
	private JLabel scoreLabel = new JLabel();
	private int score = 0;								// 점수
	
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
		
		scorePanel.add(scoreLabel);
		scoreLabel.setBounds(20,100,200,20);
		scoreLabel.setText("점수 : "+score+"점");
		
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
						current_coord_Y = 80;
						break;
					}
				}
				// 구조물이나 땅에 닿은 경우, location에 위치정보 기록
				
				if(hitFlag) {
					initBlock();
					checkLineFull();
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
	
	private void setBlockShape() {
    	int row = -1; int col = -1;
    	int count = 0;
    	for(int[] hor : selectedShape) {
    		col++;
    		row = -1;
    		for(int check : hor) {
    			row++;
    			if(check == 1) {
    				blocks[count] = new Block(SIZE_X*row+current_coord_X, SIZE_Y*col, color[blockIndex]);
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
		int[][] rotated = new int[selectedShape[0].length][selectedShape.length];
		for(int i=0; i<selectedShape[0].length; i++) {
			for(int j=0; j<selectedShape.length; j++) {
				rotated[i][j] = selectedShape[selectedShape.length-1-j][i];
			}
		}
		
		int[][] temp = selectedShape;
		
		selectedShape = rotated;
		for(Block block : blocks) {
			if((block.getX() < LEFT_LIMIT || block.getX() > RIGHT_LIMIT)||(block.getY()>800)) {
				selectedShape = temp;
				return;
			}
		}
		
		current_coord_Y = blocks[0].getY();
		setBlockShape();
		for(Block block : blocks) {
			block.setXY(block.getX(), block.getY()+current_coord_Y);
		}
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
		
		current_coord_X += x;
		for(Block block : blocks) {
			block.addXY(x, 0);
		}
	}
	// blocks의 모든 블럭에 대해 x좌표를 조정한다.

	private void checkLineFull() {
		int lineCount = 0;
		for(int i=0; i<location.length-1; i++) {
			lineCount = 0;
			for(int j=0; j<location[i].length;j++) {
				if(location[i][j] == 1) lineCount ++;
			}
			if(lineCount ==  10) {
				LineClear(i);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// location 배열 중 한 줄이 다 찼는지 확인
	
	private void LineClear(int row) {
		for(int i = row; i>0; i--) {
			location[i] = location[i-1];
		}
		printBackground();
		score += 100;
		scoreLabel.setText("점수 : "+score+"점");
	}
}
