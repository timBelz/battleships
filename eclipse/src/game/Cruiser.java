package game;

public class Cruiser extends Ship{
	protected int SIZE;
	protected String CLASS;
	
	public Cruiser(int id) {
		super(id);
		this.SIZE = 3;
		this.CLASS = "Carrier";
	}
	
	public String getShipClass() {
		return this.CLASS;
	}
	
	public int getSize() {
		return this.SIZE;
	}
}
