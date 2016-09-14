package maxmindispersion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grasp {
	private int tam;
  private double [][] grafo;
  private int[] solucion;
  private int[] utilizado;
  private double valor;
  private int sizeRCL;
  
  public Grasp (String fichero, int rcl) throws NumberFormatException, IOException, FileNotFoundException {
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
    
    utilizado = new int [getSize()];
    sizeRCL = rcl;
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
  
  public double[] calcularAfinidades(int[] S, int[] candidatos) {
  	double[] aux = new double [getSize()];
  	for(int i = 0; i < getSize(); i++) {
  		if(candidatos[i] == 1) {
  			aux[i] = md(union(S, i));
  		}
  		else {
  			aux[i] = Double.NEGATIVE_INFINITY;
  		}
  	}
  	return aux;
  }
  
  public int[] construirRCL (double[] aux) {
  	int[] rcl = new int [sizeRCL];
  	double[] aux2 = new  double [getSize()];
  	copiar(aux2, aux);
  	
  	int indice = 0;
  	for(int i = 0; i < sizeRCL; i++) {
  		double max = Double.NEGATIVE_INFINITY;
  		for(int j = 0; j < getSize(); j++) {
  			if(aux2[j] > max) {
  				max = aux2[j];
  				indice = j;
  			}
  		}
  		rcl[i] = indice;
  		aux2[indice] = Double.NEGATIVE_INFINITY;
  	}
  	return rcl;
  }
  
  public int[] ejecutar () {
  	double mejorDispersion = Double.NEGATIVE_INFINITY;
  	int[] solucion = new int [getSize()];
  	for(int i = 0; i < (getSize() + ((1/4) * getSize())); i++) {
  		int[] s = faseConstruccion();
  		busquedaLocal(s);
  		if(md(s) > mejorDispersion) {
  			mejorDispersion = md(s);
  			copiar(solucion, s);
  		}
  	}
  	return solucion;
  }
  
  public int[] faseConstruccion () {
  	Random rand = new Random ();
  	int i = rand.nextInt(getSize());
  	int[] S = new int [getSize()];
  	S[i] = 1; int k = 1;
  	int m = rand.nextInt((getSize() - 2) + 1) + 2;
  	while(k < m) {
  		int[] candidatos = obtenerCandidatos(S);
  		double[] dispersiones = calcularAfinidades(S, candidatos);
  		int[] rcl = construirRCL(dispersiones);
  		int n = rcl[rand.nextInt(sizeRCL)];
  		copiar(S, union(S, n));
  		valor = dispersiones[n];
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
  
  public void mostrarR(int[] s) {
  	System.out.print("RCL: ");
  	for(int i = 0; i < getSize(); i++) {
  			System.out.print(s[i] + " ");
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
