
public class Ponto  implements Comparable<Ponto>{
	
	private int label;
	private double distancia;
	
	public Ponto(int label, double distancia) {
		this.label = label;
		this.distancia = distancia;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	@Override
	public int compareTo(Ponto ponto) {
		if(this.distancia < ponto.getDistancia()) {
			return -1;
		} else if(this.distancia == ponto.getDistancia()) {
			return 0;
		} 
		return 1;
	}
	
}
