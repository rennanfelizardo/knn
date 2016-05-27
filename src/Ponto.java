
public class Ponto  implements Comparable<Ponto>{
	
	private Flor FlorVizinha;
	//private int label;
	private double distancia;
	
	public Ponto(Flor irisVizinha, double distancia) {
		this.FlorVizinha = irisVizinha;
		this.distancia = distancia;
	}

	public Flor getFlorVizinha() {
		return FlorVizinha;
	}

	public void setIrisVizinha(Flor irisVizinha) {
		this.FlorVizinha = irisVizinha;
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
