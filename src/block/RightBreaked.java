package block;

public class RightBreaked extends Block {
	
	private boolean[][] formation = {
			{false, false, true, false},
			{true, true, true, false}
	};
	
	public boolean[][] getFormation() {
		return this.formation;
	}
	
}
