import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
		
		
		List<Iris> listaTreinamento = lerDadosTreinamento();
		List<Iris> listaTeste = lerDadosTeste(); 
		
		int k = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe o valor de K"));
		
		// Executa o método knn para classificar as iris
		List<Integer> classificacao = knn(listaTreinamento, listaTeste, k);
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("resultado.txt"));
			
			double acertos = 0.0;
			int cont = 0;
			
			List<Integer> rotulosTeste = lerRotulosTeste();
			
			// Grava o resultado da classificação das iris no arquivo resultado.txt
			for (Integer i : classificacao) {
				fw.write(i.toString()+"\n");
				
				if(i.equals(rotulosTeste.get(cont))) {
					acertos++;
				}
				cont++;
			}
			
			// Valor da porcentagem de um rotulo acertado
			double porcentagemPorAcerto = 100.0 / (double) classificacao.size();
			
			// Calcula o valor da porcentagem de acertos do algoritmo
			Double porcentagemDeAcertos = porcentagemPorAcerto * acertos;
			
			// Formata e grava o valor da porcentagem de acertos do algoritmo
			fw.write(new DecimalFormat("0.##").format(porcentagemDeAcertos).toString());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static List<Integer> knn(List<Iris> listaTreinamento, List<Iris> listaTeste, int k){
		List<Integer> classificacao = new ArrayList<Integer>();
		
		// Cada iris da lista de teste deve ser classificada
		for (Iris irisIndefinida : listaTeste) {
			
			List<Ponto> pontos = new ArrayList<Ponto>();
			
			for (Iris iris : listaTreinamento) { 
				pontos.add(new Ponto(iris.getLabel(), calcularDistancia(iris, irisIndefinida)));
			}
			
			// Ordena a lista de pontos a partir da menor distância para a maior
			Collections.sort(pontos); 

			// Armazena a quantidade de ocorrências para os labels dos K pontos mais próximos
			Map<Integer, Integer> contadorDeOcorrencias = new HashMap<Integer, Integer>(); // chave = label, valor = quantidade de ocorrências do label
			
			// Varre os k pontos mais próximos
			for (int i = 0; i < k; i++) { 
				Integer label = pontos.get(i).getLabel();
				
				// Se o label já estiver contido no map
				if (contadorDeOcorrencias.containsKey(label)) { 
					contadorDeOcorrencias.put(label, contadorDeOcorrencias.get(label) + 1); // Incrementa a quantidade de ocorrências do label
				} else {
					contadorDeOcorrencias.put(label, 1); // Adiciona um novo label no map
				}
			}

			// Obtém a ocorrência mais frequente
			int labelMaisFrequente = -1;
			Integer maiorNumeroOcorrencias = 0;
			
			// Representa o map como um conjunto e faz a iteração sobre ele
			for (Entry<Integer, Integer> ocorrencias : contadorDeOcorrencias.entrySet()) {
				// Se o número de ocorrências do label for maior do que o maior número de ocorrências atual  
				if (maiorNumeroOcorrencias < ocorrencias.getValue()) { 
					maiorNumeroOcorrencias = ocorrencias.getValue(); // Atualiza o número de ocorrências
					labelMaisFrequente = ocorrencias.getKey(); // Atualiza o label mais frequente
				}
			}
			
			/*
			int labelMaisFrequente = 0, maiorNumeroOcorrencias = 0;
			int cont = 0, indice = cont;
			// Varrer os valores do map para encontrar o label que possue o maior número de ocorrências 
			for(Integer numeroOcorrencias: occurenceCount.values()) {
				if(numeroOcorrencias > maiorNumeroOcorrencias) {
					maiorNumeroOcorrencias = numeroOcorrencias;
					idice = cont;
				}
				cont++;
			} 
			*/
			classificacao.add(labelMaisFrequente);
		}
		
		return classificacao;
		
	}
	
	/**
	 * Realiza o cálculo da distância Euclidiana entre duas Iris
	 * 
	 * O cálculo da distância entre A e B é dada por: d (a,b) = √ (a1 − b1)² + (a2 − b2)² + ...+ (an − bn)²  
	 * 
	 * Math.sqrt(double) : retorna o valor da raiz quadrada do número
	 * Math.pow(double, double) : retorna o valor do primeiro argumento elevado ao segundo
	 * 
	 */
	public static double calcularDistancia(Iris irisA, Iris irisB){
		return Math.sqrt(Math.pow(irisA.getSepallength() - irisB.getSepallength(), 2) + Math.pow(irisA.getSepalwidth() - irisB.getSepalwidth(), 2)
						+ Math.pow(irisA.getPetallength() - irisB.getPetallength() , 2) + Math.pow(irisA.getPetalwidth() - irisB.getPetalwidth(), 2));
	}
	
	public static List<Iris> lerDadosTreinamento(){
		
		List<Iris> listaTreinamento = new ArrayList<Iris>();
		
		try{
						
			BufferedReader arquivo = new BufferedReader(new FileReader("arquivos/treinamento.csv"));
			String linha = "";
			arquivo.readLine();
			int i = 0;
			
			while((linha = arquivo.readLine()) != null){
				
				String[] dado = linha.split(",");
				listaTreinamento.add(new Iris(Double.parseDouble(dado[0]), Double.parseDouble(dado[1]), Double.parseDouble(dado[2]),
									Double.parseDouble(dado[3]), Integer.parseInt(dado[4])));
				i++;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
			
		return listaTreinamento;
	}
	
	
	public static List<Iris> lerDadosTeste(){
		
		List<Iris> listaTeste = new ArrayList<Iris>();
		
		try{
			BufferedReader arquivo = new BufferedReader(new FileReader("arquivos/teste.csv"));
			
			String linha = "";
			arquivo.readLine();
			int i = 0;
			
			while((linha = arquivo.readLine()) != null){
				
				String[] dado = linha.split(",");
				listaTeste.add(new Iris(Double.parseDouble(dado[0]), Double.parseDouble(dado[1]), Double.parseDouble(dado[2]),
									Double.parseDouble(dado[3])));
				i++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listaTeste;
	}
	
public static List<Integer> lerRotulosTeste(){
		
		List<Integer> rotulosTeste = new ArrayList<Integer>();
		
		try{
			BufferedReader arquivo = new BufferedReader(new FileReader("arquivos/rotulos-teste.txt"));
			
			String linha = "";
			
			while((linha = arquivo.readLine()) != null){
				rotulosTeste.add(Integer.parseInt(linha));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rotulosTeste;
	}
				
}
