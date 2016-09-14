package maxmindispersion;

/**
 * Clase Clock
 * @author Alexis Daniel Fuentes Pérez
 */

public class Clock {
	long inicioT;
	long finalT;
	
	public Clock () {
		inicioT = 0;
		finalT = 0;
	}
	
	/**
	 * Inicia el cronometro
	 */
	
	public void start () {
		inicioT = System.nanoTime();
	}
	
	/**
	 * Detiene el cronometro
	 */
	
	public void stop () {
		finalT = System.nanoTime();
	}
	
	/**
	 * Obtiene lo cronometrado
	 * @return Tiempo cronometrado
	 */
	
	public long elapsedTime () {
		return (getFinal() - getInicio());
	}
	
	public long getInicio () {
		return inicioT;
	}
	
	public long getFinal () {
		return finalT;
	}
	
}
