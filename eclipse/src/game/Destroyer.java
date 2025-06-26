package game;

public class Destroyer extends Ship{
	protected int SIZE;
	protected String CLASS;
	
	public Destroyer(int id) {
		super(id);
		this.SIZE = 2;
		this.CLASS = "Destroyer";
	}
	
	public String getShipClass() {
		return this.CLASS;
	}
	
	public int getSize() {
		return this.SIZE;
	}
}
