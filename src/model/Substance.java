package model;


/**
 *
 * @author kristersundlof
 */
public class Substance {
	private int id;
	private String name;
	private double halfLife;
	
	
	public Substance(String name, double halfLife) {
		super();
		this.name = name;
		this.halfLife = halfLife;
	}

	public Substance(int id, String name, double half_life) {
		super();
		this.id = id;
		this.name = name;
		this.halfLife = half_life;
	}

	public double getHalfLife() {
		return halfLife;
	}

	public void setHalfLife(double halfLife) {
		this.halfLife = halfLife;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void print() {
		System.out.println("ID:	" + getId() + "\tSubstance:	" + getName() + "\tHalt life:	"  +  getHalfLife());
	}
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.name;
		}
}
