import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DatasetIris {

	public static void main(String[] args) {
		
		List<Iris> listaTreinamento = lerDadosTreinamento();
		List<Iris> listaTeste = lerDadosTeste(); 
		
		for (Iris i : listaTreinamento){
			System.out.println(i.toString());
		}
		
		
	}
	
	
	static List<Iris> lerDadosTreinamento(){
		
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
	
	
	static List<Iris> lerDadosTeste(){
		
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
				
}
