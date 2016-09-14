package maxmindispersion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Multiarranque {
	private int tam;
  private double [][] grafo;
  private int[] solucion;
  private double valor;
  
  public Multiarranque (String fichero) throws NumberFormatException, IOException, FileNotFoundException {
    String linea = new String ();
    String [] token;

    BufferedReader reader = new BufferedReader ( new FileReader(fichero) );
    
    if(reader.ready())
      tam = Integer.parseInt(reader.readLine());
    
    grafo = new double [tam][tam];
    
    while(reader.ready()){
      for(int i = 0; i < (tam - 1); i++) {
        for(int j = i + 1; j < tam; j++) {
          String datos = reader.readLine();
          datos = datos.replaceAll(",", ".");
          double afinidad = Double.parseDouble(datos);
          grafo[i][j] = afinidad;
          grafo[j][i] = afinidad;
        }
      }
    }
    
    for(int i = 0; i < tam; i++) {
      grafo[i][i] = 0;
    }
  }
  
  public void mostrarGrafo () {
  	for(int i = 0; i < getSize(); i++) {
  		for(int j = 0; j < getSize(); j++) {
  			System.out.print(grafo[i][j] + "\t");
  		}
  		System.out.println();
  	}
  }
  
  public double md (int[] S) {											// [0|1|1|1|0]
  	double dispersionMedia = 0;
  	int numNodos = 0;
  	for (int i = 0; i < getSize(); i++) {									
  		if (S[i] == 1) {
  			numNodos++;
	  		for (int j = i + 1; j < getSize(); j++) {
	  			if (S[j] == 1) {
	  				dispersionMedia += grafo[i][j];
	  			}
	  		}
  		}
  	}
  	return (dispersionMedia / numNodos);
  }
  
  public int[] obtenerCandidatos (int[] S) {
  	int[] cl = new int [getSize()];
  	for(int i=0; i < getSize(); i++) {
  		if(S[i] == 0)
  			cl[i] = 1;
  		else
  			cl[i] = 0;
  	}
  	return cl;
  }
    
  public int[] ejecutar () {
  	int[] solucion = generarSolucion ();
  	int[] mejorSolucion = new int [getSize()];
  	copiar(mejorSolucion, solucion);
  	int numIteracionesSinMejora = 0;
  	while(numIteracionesSinMejora < (getSize())) {
  		busquedaLocal(solucion);
  		if(md(mejorSolucion) < md(solucion)) {
  			 copiar(mejorSolucion, solucion);
  			 numIteracionesSinMejora = 0;
  		} else {
  			numIteracionesSinMejora++;
  		}
  		copiar(solucion, generarSolucion());
  	}
  	return mejorSolucion;
  }
  
  public int[] generarSolucion () {
  	Random rand = new Random ();
  	int i = rand.nextInt(getSize());
  	int[] S = new int [getSize()];
  	S[i] = 1; int k = 1;
  	int m = rand.nextInt((getSize() - 2) + 1) + 2;
  	while(k < m) {
  		int[] candidatos = obtenerCandidatos(S);
  		int x = rand.nextInt(getSize());
  		while(candidatos[x] != 1) {
  			x = rand.nextInt(getSize());
  		}
  		copiar(S, union(S, x));
  		k++;
  	}
  	return S;
  }
  
  public void busquedaLocal (int[] solucion) {
  	boolean mejora = true;
  	while(mejora) {
  		mejora = false;
  		int k = menorContribucion(solucion);
  		int [] noSeleccionados = obtenerCandidatos(solucion); 		
  		int[] aux = eliminar(solucion, k); 		
  		for(int i = 0; i < getSize(); i++) {
  			if(noSeleccionados[i] == 1) {		
  				int[] nuevo = union(aux, i);			
  				if(md(nuevo) > md(solucion)) {
  					copiar(solucion, nuevo);
  					mejora = true;
  					i = getSize();
  				}
  			}
  		}
  	}
  }
  
  public int menorContribucion (int[] S) {    // 1 0 0 1
  	double min = Double.POSITIVE_INFINITY;
  	int indice = 0;
  	for(int i = 0; i < getSize(); i++) {
  		if(S[i] == 1) {
	  		int[] aux = eliminar(S, i);
	  		if((md(S) - md(aux)) < min) {
	  			indice = i;
	  			min = md(S) - md(aux);
	  		}
  		}
  	}
  	return indice;
  }
  
  public void resolver () {
  	solucion = ejecutar();
  	valor = md(solucion);
  	//mostrarSolucion();
  }
  
  public void mostrarSolucion() {
  	System.out.print("S: ");
  	for(int i = 0; i < getSize(); i++) {
  		if(getSolucion()[i] == 1) {
  			System.out.print(i + " ");
  		}
  	}
  	System.out.println("\nValor: " + valor);
  }
  
  public void mostrar(int[] s) {
  	System.out.print("S: ");
  	for(int i = 0; i < getSize(); i++) {
  		if(s[i] == 1) {
  			System.out.print(i + " ");
  		}
  	}
  	System.out.println();
  }
    
  public void copiar (int[] S1, int[] S2) {
  	for(int i = 0; i < getSize(); i++) {
  		S1[i] = S2[i];
  	}
  }
  
  public void copiar (double[] S1, double[] S2) {
  	for(int i = 0; i < getSize(); i++) {
  		S1[i] = S2[i];
  	}
  }
  
  public int[] union (int[] S, int k) {
  	int [] nuevo = new int [getSize()];
  	copiar(nuevo, S);
  	nuevo[k] = 1;
  	return nuevo;
  }
  
  public int[] eliminar (int[] S, int k) {
  	int [] nuevo = new int [getSize()];
  	copiar(nuevo, S);
  	nuevo[k] = 0;
  	return nuevo;
  }
  
  public int getSize () {
  	return tam;
  }
  
  public int[] getSolucion() {
  	return solucion;
  }
  
  public void setValor (double v) {
  	valor = v;
  }
  
  public double getValor () {
  	return valor;
  }
}
