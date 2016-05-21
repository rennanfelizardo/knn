
public class Iris {

	double sepallength;
	double sepalwidth;
	double petallength;
	double petalwidth;
	int label;


	public Iris (double sepallength, double sepalwidth, double petallength, double petalwidth, int label){
		this.sepallength = sepallength;
		this.sepalwidth = sepalwidth;
		this.petallength = petallength;
		this.petalwidth = petalwidth;
		this.label = label;
	}

	public Iris(double sepallength, double sepalwidth, double petallength, double petalwidth) {
		this.sepallength = sepallength;
		this.sepalwidth = sepalwidth;
		this.petallength = petallength;
		this.petalwidth = petalwidth;
	}

	//cálculo da distância euclidiana
	public double distancia(Iris outra){
		return Math.sqrt(Math.pow(this.sepallength - outra.sepallength, 2) + Math.pow(this.sepalwidth - outra.sepalwidth, 2)
						+ Math.pow(this.petallength - outra.petallength, 2) + Math.pow(this.petalwidth - outra.sepalwidth, 2));
	}
	
	@Override
	public String toString(){
		return "Iris - sepallenght: "+sepallength+ ", spealwidht: "+sepalwidth+ ", petallenght: "+petallength +
				", petalwidth: "+petalwidth+ ", label:"+label;
	}
}
