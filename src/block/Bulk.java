package block;

public class Bulk extends Block{

	private boolean[][] formation = {
			{true, true, false, false},
			{true, true, false, false}
	};
	
	public boolean[][] getFormation() {
		return this.formation;
	}
	
}
