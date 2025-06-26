package game;

public class Carrier extends Ship{
	protected int SIZE;
	protected String CLASS;
	
	public Carrier(int id) {
		super(id);
		this.SIZE = 5;
		this.CLASS = "Carrier";
	}
	
	public String getShipClass() {
		return this.CLASS;
	}
	
	public int getSize() {
		return this.SIZE;
	}
}
