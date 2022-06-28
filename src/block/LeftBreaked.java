package block;

public class LeftBreaked extends Block{
	
	private boolean[][] formation = {
			{true, false, false, false},
			{true, true, true, false}
	};
	
	public boolean[][] getFormation() {
		return this.formation;
	}
	
}
