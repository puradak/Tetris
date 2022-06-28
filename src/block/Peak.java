package block;

public class Peak extends Block {
	
	private boolean[][] formation = {
			{false, true, false, false},
			{true, true, true, false}
	};
	
	public boolean[][] getFormation() {
		return this.formation;
	}

}
