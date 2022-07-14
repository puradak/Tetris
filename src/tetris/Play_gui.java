package tetris;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play_gui {
	static private Block blocks[] = new Block[4];
	// �� ��ġ

	static private int blockColumn = 2;        // ���� �����ϴ� n*m ������ ȸ�� �� ������ ���ΰ� 2, 4 �� �ϳ��� ����ǹǷ� �� ������ ����
	static private int blockStart[] = {1, 2};  // ���� �����ϴ� n*m �»�� ��ǥ�� �׻� ���
	
	static private int location[][] = new int[20][10];
	// �� ��ġ ����  : �޼��尡 �ʿ�
	
	static private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}/*,{0,0,0,0},{0,0,0,0}*/}
	}; 
	// 7��
	
	static private Color color[] = {
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.BLUE,
			Color.BLACK, //����
			Color.WHITE  //�����
	};
	// 7��
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
	
	// 2���� �迭�� �ð�������� 90�� ȸ���ϱ�
	// ȸ���� �Ǵ� ��: blockStart[0], blockStart[1]
	// blocks[]�� ���� ���õ� ���� ��ü�� ������� -> ��ü�� �ڸ��ϴ� ��ġ�� n*m �迭�� ��Ÿ�� ��, ȸ�����Ѽ� m*n �迭�� ����
	// ����� m*n �迭���� �ڸ��� �����ϰ� �ִ� �κ��� ��ü�� ��ġ�̹Ƿ� ��ü�� x, y ��ǥ ������ ȸ�� ���� ��ǥ�� ����
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
    	blockColumn = (blockColumn % 4) + 2; // ȸ�� �Ǿ����Ƿ� ���� ���� ����
    	
    	for(int i = 0; i < 4; i++) {
    		System.out.println(change[i][0] + " " + change[i][1]);
    	}
    	
    	/*
        int[][] ret = new int[key.length][key[0].length];
        for(int i=0; i<key[0].length; i++) {
            for(int j=0; j<key.length; j++) {
                // ���������� 90�� ȸ��
                ret[i][j] = key[key.length-1-j][i];
            }
        }*/
    }
    
    
	private void setBlock(int shapeNum) {
		blockColumn = 2; // ���� ���� ������ 2�� �ʱⰪ�� ��. �� ���� �ø��� �ʱ�ȭ
    	
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
    // ��Ʈ���� ���� �����Ѵ�.
    
	private void setLocate() {
    	
    }
    // ��� ���� ��ġ�� �����Ѵ�.
    
	private void printBlock(/*blocks �̿�*/) {
    	
    }
}
