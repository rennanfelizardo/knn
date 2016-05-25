
public class Ponto  implements Comparable<Ponto>{
	
	private Iris irisVizinha;
	//private int label;
	private double distancia;
	
	public Ponto(Iris irisVizinha, double distancia) {
		this.irisVizinha = irisVizinha;
		this.distancia = distancia;
	}

	public Iris getIrisVizinha() {
		return irisVizinha;
	}

	public void setIrisVizinha(Iris irisVizinha) {
		this.irisVizinha = irisVizinha;
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
