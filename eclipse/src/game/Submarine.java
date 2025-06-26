package game;

public class Submarine extends Ship{
	protected int SIZE;
	protected String CLASS;
	
	public Submarine(int id) {
		super(id);
		this.SIZE = 3;
		this.CLASS = "Submarine";
	}
	
	public String getShipClass() {
		return this.CLASS;
	}
	
	public int getSize() {
		return this.SIZE;
	}
}