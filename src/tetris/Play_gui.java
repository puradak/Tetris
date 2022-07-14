package tetris;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play_gui {
	static private Block blocks[] = new Block[4];
	// 블럭 뭉치

	static private int blockColumn = 2;        // 블럭이 차지하는 n*m 공간이 회전 될 때마다 세로가 2, 4 중 하나로 변경되므로 그 정보를 저장
	static private int blockStart[] = {1, 2};  // 블럭이 차지하는 n*m 좌상단 좌표를 항상 기억
	
	static private int location[][] = new int[20][10];
	// 블럭 위치 저장  : 메서드가 필요
	
	static private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}/*,{0,0,0,0},{0,0,0,0}*/}
	}; 
	// 7개
	
	static private Color color[] = {
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.BLUE,
			Color.BLACK, //남색
			Color.WHITE  //보라색
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
		
		rotationRight();
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
    	
    	for(int i = 0; i < 4; i++) {
    		System.out.println(change[i][0] + " " + change[i][1]);
    	}
    	
    	/*
        int[][] ret = new int[key.length][key[0].length];
        for(int i=0; i<key[0].length; i++) {
            for(int j=0; j<key.length; j++) {
                // 오른쪽으로 90도 회전
                ret[i][j] = key[key.length-1-j][i];
            }
        }*/
    }
    
    
	private void setBlock(int shapeNum) {
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
    				blocks[count] = new Block(40*row+20,40*col+20, color[shapeNum]);
    				count++;
    			}
    			else continue;
    		}
    	}
    }
    // 테트리스 블럭을 구성한다.
    
	private void setLocate() {
    	
    }
    // 모든 블럭의 위치를 저장한다.
    
	private void printBlock(/*blocks 이용*/) {
    	
    }
}
