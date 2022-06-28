package block;

public class Bar extends Block{
	
	private boolean[][] formation = {
			{true, true, true, true},
			{false, false, false, false}
	};
	
	public boolean[][] getFormation() {
		return this.formation;
	}
	
}
