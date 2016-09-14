package maxmindispersion;

import java.io.*;
import java.util.Vector;

public class VorazDesc {
    
  private int tam;
  private double [][] grafo;
  private int[] solucion;
  private int[] utilizado;
  private double valor;
  
  public VorazDesc (String fichero) throws NumberFormatException, IOException, FileNotFoundException {
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
 
  public int[] vectorCompleto () {
  	int [] S = new int [getSize()];
  	for(int i = 0; i < getSize(); i++) {
  		S[i] = 1;
  	}
  	setValor(md(S));
  	return S;
  }
    
  public boolean equals (int[] S, int[] Sx) {
  	boolean igualdad = true;
  	for(int i = 0; i < getSize(); i++) {
  		if(S[i] != Sx[i])
  			igualdad = false;
  	}
  	return igualdad;
  }
  
  public void copiar (int[] S1, int[] S2) {
  	for(int i = 0; i < getSize(); i++) {
  		S1[i] = S2[i];
  	}
  }
  
  public int[] eliminar (int[] S, int k) {
  	int [] nuevo = new int [getSize()];
  	copiar(nuevo, S);
  	nuevo[k] = 0;
  	return nuevo;
  }
  
  public int[] ejecutar () {
  	int [] S = vectorCompleto ();
  	int [] Sx = new int [getSize()];
  	while(!equals(S, Sx)) {
  		copiar(Sx, S);
  		for(int k = 0; k < getSize(); k++) {
  			double dispersion = md(eliminar(S, k));
  			if(dispersion >= md(S)) {
  				copiar(S, eliminar(S, k));
  				setValor(dispersion);
  			}
  		}
  	}
  	return Sx;
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