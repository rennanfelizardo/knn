
public class Flor {

	private double sepallength;
	private double sepalwidth;
	private double petallength;
	private double petalwidth;
	private int label;

	public Flor (double sepallength, double sepalwidth, double petallength, double petalwidth, int label){
		this.sepallength = sepallength;
		this.sepalwidth = sepalwidth;
		this.petallength = petallength;
		this.petalwidth = petalwidth;
		this.label = label;
	}

	public Flor(double sepallength, double sepalwidth, double petallength, double petalwidth) {
		this.sepallength = sepallength;
		this.sepalwidth = sepalwidth;
		this.petallength = petallength;
		this.petalwidth = petalwidth;
	}
	
	public double getSepallength() {
		return sepallength;
	}

	public void setSepallength(double sepallength) {
		this.sepallength = sepallength;
	}

	public double getSepalwidth() {
		return sepalwidth;
	}

	public void setSepalwidth(double sepalwidth) {
		this.sepalwidth = sepalwidth;
	}

	public double getPetallength() {
		return petallength;
	}

	public void setPetallength(double petallength) {
		this.petallength = petallength;
	}

	public double getPetalwidth() {
		return petalwidth;
	}

	public void setPetalwidth(double petalwidth) {
		this.petalwidth = petalwidth;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	@Override
	public String toString(){
		return "Iris - sepallenght: "+sepallength+ ", spealwidht: "+sepalwidth+ ", petallenght: "+petallength +
				", petalwidth: "+petalwidth+ ", label:"+label;
	}

}
