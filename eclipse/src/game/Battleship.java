package game;

public class Battleship extends Ship {
	protected int SIZE;
	protected String CLASS;
	
	public Battleship(int id) {
		super(id);
		this.SIZE = 4;
		this.CLASS = "Battleship";
	}

	public String getShipClass() {
		return this.CLASS;
	}
	
	public int getSize() {
		return this.SIZE;
	}

}
