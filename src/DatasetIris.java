import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class DatasetIris {

	public static void main(String[] args) {
		
		// Lista de flores já rotuladas e que serão utilizadas para o treinamento 
		List<Flor> listaTreinamento = carregarFlores("arquivos/treinamento.csv");
		
		// Lista de flores que precisam ser rotuladas
		List<Flor> floresNaoRotuladas = carregarFlores("arquivos/teste.csv"); 
		
		int k = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe o valor de K"));
		
		// Executa o knn para classificar as flores não rotuladas
		List<Integer> classificacao = knn(listaTreinamento, floresNaoRotuladas, k);
		
		gravarResultado(classificacao, "resultado.txt");
		
	}
	
	/**
	 * Realiza a classificação das flores contidas no arquivo de teste a partir dos dados da lista de treinamento
	 */
	public static List<Integer> knn(List<Flor> listaTreinamento, List<Flor> floresNaoRotuladas, int k){
		List<Integer> classificacao = new ArrayList<Integer>();
		
		// Cada flor da lista de teste deve ser classificada
		for (Flor florNaoRotulada : floresNaoRotuladas) {
			
			List<Ponto> pontos = new ArrayList<Ponto>();
			
			for (Flor flor : listaTreinamento) { 
				pontos.add(new Ponto(flor, calcularDistancia(florNaoRotulada, flor)));
			}
			
			// Ordena a lista de pontos a partir da menor distância para a maior
			Collections.sort(pontos); 

			// Armazena a quantidade de ocorrências para os labels dos K pontos mais próximos
			Map<Integer, Integer> contadorDeOcorrencias = new HashMap<Integer, Integer>(); // chave = label, valor = quantidade de ocorrências do label
			
			// Varre os k pontos mais próximos
			for (int i = 0; i < k; i++) { 
				Integer label = pontos.get(i).getFlorVizinha().getLabel();
				
				// Se o label já estiver contido no map
				if (contadorDeOcorrencias.containsKey(label)) { 
					// Incrementa a quantidade de ocorrências do label
					contadorDeOcorrencias.put(label, contadorDeOcorrencias.get(label) + 1); 
				} else {
					// Adiciona um novo label no map
					contadorDeOcorrencias.put(label, 1); 
				}
			}

			int labelMaisFrequente = -1;
			Integer maiorNumeroOcorrencias = 0;
			
			// Representa o map como um conjunto e faz a iteração sobre ele
			for (Entry<Integer, Integer> ocorrencias : contadorDeOcorrencias.entrySet()) {
				// Se o número de ocorrências do label for maior do que o maior número de ocorrências atual  
				if (maiorNumeroOcorrencias < ocorrencias.getValue()) { 
					// Atualiza o número de ocorrências
					maiorNumeroOcorrencias = ocorrencias.getValue(); 
					// Atualiza o label mais frequente
					labelMaisFrequente = ocorrencias.getKey(); 
				}
			}
			
			classificacao.add(labelMaisFrequente);
		}
		
		return classificacao;
		
	}
	
	/**
	 * Realiza o cálculo da distância Euclidiana entre duas iris no gráfico
	 * 
	 * O cálculo da distância entre os pontos A e B é dado por: d (a,b) = √ (a1 − b1)² + (a2 − b2)² + ...+ (an − bn)²  
	 * 
	 * Math.sqrt(double) : retorna o valor da raiz quadrada do número
	 * Math.pow(double, double) : retorna o valor do primeiro argumento elevado ao segundo
	 * 
	 */
	public static double calcularDistancia(Flor irisA, Flor irisB){
		return Math.sqrt(Math.pow(irisA.getSepallength() - irisB.getSepallength(), 2) + Math.pow(irisA.getSepalwidth() - irisB.getSepalwidth(), 2)
						+ Math.pow(irisA.getPetallength() - irisB.getPetallength() , 2) + Math.pow(irisA.getPetalwidth() - irisB.getPetalwidth(), 2));
	}
	
	/**
	 * Cria flores de acordo com os dados contidos em um arquivo
	 */
	public static List<Flor> carregarFlores(String nomeArquivo){
		
		List<Flor> flores = new ArrayList<Flor>();
		
		Scanner leitor = null;
		try {
			leitor = new Scanner(new FileReader(nomeArquivo));
			
			// Pula a primeira linha
			leitor.nextLine();
			
			while (leitor.hasNextLine()) {
				
				// Adiciona uma nova flor criada a partir dos dados contidos na linha atual do arquivo
				flores.add(criarFlor(leitor.nextLine()));
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			leitor.close();
		}
		
		return flores;

	}
	
	/**
	 * Cria uma flor de acordo com os dados contidos em uma linha
	 */
	public static Flor criarFlor(String linha) {
		Scanner leitor = null;
		Flor flor = null;
		
		try {
			leitor = new Scanner(linha).useDelimiter("\\s*,\\s*");
			
			// Cria a flor com os dados contido no arquivo
			flor = new Flor(Double.parseDouble(leitor.next()), Double.parseDouble(leitor.next()), 
											Double.parseDouble(leitor.next()), Double.parseDouble(leitor.next()));

			// Verifica se existe um rótulo. Se existir, a flor é rotulada
			if (leitor.hasNextInt()) {
				flor.setLabel(Integer.parseInt(leitor.next()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			leitor.close();
		}

		return flor;
	}
	
	/**
	 * Grava o resultado da classificação do KNN e a porcentagem de acertos em um arquivo 
	 * 
	 */
	public static void gravarResultado(List<Integer> classificacao, String nomeArquivo) {
		FileWriter gravador = null;
		try {
			gravador = new FileWriter(new File(nomeArquivo));
			
			double acertos = 0.0;
			int cont = 0;
			
			// Carrega os rótulos do arquivo de teste 
			List<Integer> rotulosTeste = carregarRotulos("arquivos/rotulos-teste.txt");
			
			// Grava o resultado da classificação das iris no arquivo
			for (Integer i : classificacao) {
				gravador.write(i.toString()+"\n");
				
				if(i.equals(rotulosTeste.get(cont))) {
					acertos++;
				}
				cont++;
			}
			
			// Valor da porcentagem de um rótulo acertado
			double porcentagemPorAcerto = 100.0 / (double) classificacao.size();
			
			// Calcula o valor da porcentagem de acertos do algoritmo
			Double porcentagemDeAcertos = porcentagemPorAcerto * acertos;
			
			// Formata e grava o valor da porcentagem de acertos do algoritmo
			gravador.write(new DecimalFormat("0.##").format(porcentagemDeAcertos).toString());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				gravador.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Carrega os rótulos contidos no arquivo com o nome passado como parâmetro
	 */
	public static List<Integer> carregarRotulos(String nomeArquivo){
		
		List<Integer> rotulosTeste = new ArrayList<Integer>();
		Scanner leitor = null;
		try{
			leitor = new Scanner(new FileReader(nomeArquivo));
						
			while(leitor.hasNextLine()){
				rotulosTeste.add(Integer.parseInt(leitor.nextLine()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			leitor.close();
		}
		
		return rotulosTeste;
	}
				
}
