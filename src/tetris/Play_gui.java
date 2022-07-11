package tetris;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play_gui {
	private Block blocks[] = new Block[4];
	//블럭 뭉치
	
	private int location[][] = new int[20][10];
	// 블럭 위치 저장  : 메서드가 필요
	
	private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}/*,{0,0,0,0},{0,0,0,0}*/}
	}; 
	// 7개
	
	private Color color[] = {
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
				
	}
	
	// 2차원 배열을 90도 회전하기
    public static int[][] turn(int[][] key) {
        int[][] ret = new int[key.length][key[0].length];
        for(int i=0; i<key[0].length; i++) {
            for(int j=0; j<key.length; j++) {
                // 오른쪽으로 90도 회전
                ret[i][j] = key[key.length-1-j][i];
            }
        }
        return ret;
    }
    
    
    private void setBlock(int shapeNum) {
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
