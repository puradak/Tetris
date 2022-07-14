package tetris;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play_gui {
	private Block blocks[] = new Block[4];
	//�� ��ġ
	
	private int location[][] = new int[20][10];
	// �� ��ġ ����  : �޼��尡 �ʿ�
	
	private int shape[][][] = {
			{{1,1,1,1},{0,0,0,0}/*,{0,0,0,0},{0,0,0,0}*/}
	}; 
	// 7��
	
	private Color color[] = {
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
				
	}
	
	// 2���� �迭�� 90�� ȸ���ϱ�
    public static int[][] turn(int[][] key) {
        int[][] ret = new int[key.length][key[0].length];
        for(int i=0; i<key[0].length; i++) {
            for(int j=0; j<key.length; j++) {
                // ���������� 90�� ȸ��
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
    // ��Ʈ���� ���� �����Ѵ�.
    
    private void setLocate() {
    	
    }
    // ��� ���� ��ġ�� �����Ѵ�.
    
    private void printBlock(/*blocks �̿�*/) {
    	
    }
}
