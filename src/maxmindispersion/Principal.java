package maxmindispersion;

import java.io.*;

public class Principal {
    
    public static void main (String[] args) throws NumberFormatException, FileNotFoundException, IOException {
    	System.out.println("Max Min Dispersion Problem");
    	System.out.println("Alexis Daniel Fuentes Perez");
    	
    	Clock temporizador = new Clock ();
    	
    	System.out.println("\nAlgoritmo Hibrido GRASP-VNS");
      for(int k = 0; k < 4; k++) {
         System.out.println("\nProblema\tn\tCPU\t\tmd");
         GraspHibrido gh = new GraspHibrido (args[k], 2);
         temporizador.start();
         gh.resolver();
         temporizador.stop();
         System.out.println("ID" + (k+1) + "\t\t" + gh.getSize() + "\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + gh.getValor());
         gh.mostrarSolucion();
      }
    	
      /*VorazCons vc = new VorazCons (args[0]);
      System.out.println("\nGrafo: ");
      vc.mostrarGrafo();
      
      System.out.println("\nAlgoritmo Voraz Constructivo:");
      vc.resolver();
      
      System.out.println("\nAlgoritmo Voraz Desconstructivo:");
      VorazDesc vd = new VorazDesc (args[0]);
      vd.resolver();
      
      System.out.println("\nAlgoritmo GRASP (LRC = 4):");
      Grasp g = new Grasp (args[0], 4);
      g.resolver();
      
      System.out.println("\nAlgoritmo Multiarranque:");
      Multiarranque ma = new Multiarranque (args[0]);
      ma.resolver();
      
      System.out.println("\nAlgoritmos VNS (V = 4):");
      VNS vns = new VNS (args[0], 4);
      vns.resolver();*/
      
    	//Clock temporizador = new Clock ();
    	     
    	
      /*System.out.println("\nAlgoritmo Voraz Constructivo");
      System.out.println("Problema\tn\tEjecucion\tCPU\t\tmd");
      for(int k = 0; k < 4; k++) {
      	 VorazCons vc = new VorazCons (args[k]);
    		 temporizador.start();
    		 vc.resolver();
    		 temporizador.stop();
    		 System.out.println("ID" + (k+1) + "\t\t" + vc.getSize() + "\t" + 1 + "\t\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + vc.getValor());
      }
      
      System.out.println("\nAlgoritmo Voraz Desconstructivo");
      System.out.println("Problema\tn\tEjecucion\tCPU\t\tmd");
      for(int k = 0; k < 4; k++) {
      	 VorazDesc vd = new VorazDesc (args[k]);
    		 temporizador.start();
    		 vd.resolver();
    		 temporizador.stop();
    		 System.out.println("ID" + (k+1) + "\t\t" + vd.getSize() + "\t" + 1 + "\t\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + vd.getValor());
      }
      
      System.out.println("\nAlgoritmo GRASP");
      System.out.println("Problema\tn\t|LRC|\tEjecucion\tCPU\t\tmd");
      for(int k = 0; k < 4; k++) {
	      for(int i = 3; i <= 4; i++) {
	      	for(int j = 1; j <= 5; j++) {
	      		 Grasp g = new Grasp (args[k], i);
	      		 temporizador.start();
	      		 g.resolver();
	      		 temporizador.stop();
	      		 System.out.println("ID" + (k+1) + "\t\t" + g.getSize() + "\t " + i + "\t" + j + "\t\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + g.getValor());
	      	}
	      }
      }
      
     System.out.println("\nAlgoritmo Multiarranque");
      System.out.println("Problema\tn\tEjecucion\tCPU\t\tmd");
      for(int k = 0; k < 4; k++) {
      	for(int j = 1; j <= 5; j++) {
      		 Multiarranque ma = new Multiarranque (args[k]);
      		 temporizador.start();
	    		 ma.resolver();
	    		 temporizador.stop();
	    		 System.out.println("ID" + (k+1) + "\t\t" + ma.getSize() + "\t" + j + "\t\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + ma.getValor());
      	}
      }
      
      System.out.println("\nAlgoritmo VNS");
      System.out.println("Problema\tn\tKmax\tEjecucion\tCPU\t\tmd");
      for(int k = 0; k < 4; k++) {
	      for(int i = 3; i <= 4; i++) {
	      	for(int j = 1; j <= 5; j++) {
	      		VNS vns = new VNS (args[k], i);
	      		temporizador.start();
	      		vns.resolver();
	      		temporizador.stop();
	      		System.out.println("ID" + (k+1) + "\t\t" + vns.getSize() + "\t " + i + "\t" + j + "\t\t" + (temporizador.elapsedTime()/Math.pow(10,  9)) + "s\t" + vns.getValor());
	      	}
	      }
      }*/
    }
}