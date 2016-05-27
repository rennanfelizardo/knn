
public class Ponto  implements Comparable<Ponto>{
	
	private Flor florVizinha;
	//private int label;
	private double distancia;
	
	public Ponto(Flor florVizinha, double distancia) {
		this.florVizinha = florVizinha;
		this.distancia = distancia;
	}

	public Flor getFlorVizinha() {
		return florVizinha;
	}

	public void setFlorVizinha(Flor florVizinha) {
		this.florVizinha = florVizinha;
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
