import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PastelesRicos {
 
	public static Integer cantPastelesRicos = 0;
	public static final Integer cantidadIngredientes = 4;
	public static Set<String> resultados = new HashSet<String>();
	public static final String DULCE = "D";
	public static final String FRUTA = "F";
	public static final String CONFITE = "C";
	public static final String MASITA = "M";
	
	/*
	 * Se tiene un molde (con forma de tablero 3x3) y 10 unidades de distintos ingredientes:
	 * 3 Dulces, 3 Frutas, 3 Confites y 1 Masita para hacer el pastel
	 * 
	 * Se considera que un pastel de 9 ingredientes es rico cuando tres mismos ingredientes est�n alineados vertical
	 * u horizontalmente en el molde. La Masita es especial, puede alinearse con cualquier ingrediente.
	 * 
	 * Calcular la cantidad m�xima de pasteles ricos distintos que pueden resalizarse.
	 * 
	 * Adjunta la receta del pastel en forma de algoritmo en github junto a una breve descripcion y enviala
	 * a hiringweek@waragon.com y participa por una orden de compra por $5000 en mercadolibre
	 */
	public static void main(String[] args) {

		
		for(Integer a=1; a<=cantidadIngredientes; a++) {
			recursiva(a.toString());
		}

		System.out.println("Cantidad de Pasteles Ricos: " + resultados.size());
		
	}

	
	/**
	 * Funcion recursiva que va armando todas las combinaciones posibles de pasteles
	 * @param ing
	 */
	private static void recursiva(String ing) {

		if (ing.length() <= 9) {
			for(Integer a=1; a<=cantidadIngredientes; a++) {
				recursiva(ing + a.toString());
		    }
		} else {
		    // validar y mostrar los pasteles
		    // sumar cant de pasteles validos
			if (esValido(ing)) {
				if (esPastelRico(ing)) {
					resultados.add(ing);
					
					//descomentar esta linea si se quieren ver en pantalla todas las combinaciones de pasteles ricos
//					imprimirPastel(ing);
				}
			}
		}
	}
	
	/**
	 * Metodo que decide si el pastel es rico
	 * @param resultado
	 * @return
	 */
	private static boolean esPastelRico(String pastel) {
		String[] rv = pastel.split("");
		Boolean resultado = false;
		//verifico los pasteles que tengan los 3 ingredientes iguales alineados
		if ((rv[1].equals(rv[2])) && (rv[2].equals(rv[3]))
				|| (rv[4].equals(rv[5])) && (rv[5].equals(rv[6]))
				|| (rv[7].equals(rv[8])) && (rv[8].equals(rv[9]))
				
				|| (rv[1].equals(rv[4])) && (rv[4].equals(rv[7]))
				|| (rv[2].equals(rv[5])) && (rv[5].equals(rv[8]))
				|| (rv[3].equals(rv[6])) && (rv[6].equals(rv[9]))) {
			resultado = true;
		} else {
			//verificar con prescencia de comodin
			
			//itero los ingredientes del pastel
			Map<String, Integer> cantidadesIngredientes = new HashMap<String, Integer>();
			Boolean hayDosAlineados = false;
			Integer posAux = null;
			
			//itero mirando HORIZONTALMENTE
			for (int a=0; a<cantidadIngredientes-1; a++) {
				cantidadesIngredientes.put("1", 0);
				cantidadesIngredientes.put("2", 0);
				cantidadesIngredientes.put("3", 0);
				cantidadesIngredientes.put("4", 0);
				hayDosAlineados = false;
				
				for (int c=1; c<=cantidadIngredientes-1; c++) {
					int pos = c+(a*(cantidadIngredientes-1));
					posAux = cantidadesIngredientes.get(rv[pos]);
					posAux++;
					cantidadesIngredientes.put(rv[pos], posAux);
					if(!hayDosAlineados) {
						hayDosAlineados = (posAux == 2);
					}
				}

				//si hay 2 ingredientes alineados me fijo si el tercero es el comidin
				if(hayDosAlineados && cantidadesIngredientes.get("4") == 1) {
					resultado = true;
					break;
				}				
			}


			if (!resultado) {
				//itero mirando VERTICALMENTE
				for (int c=1; c<=cantidadIngredientes-1; c++) {
					cantidadesIngredientes.put("1", 0);
					cantidadesIngredientes.put("2", 0);
					cantidadesIngredientes.put("3", 0);
					cantidadesIngredientes.put("4", 0);
					hayDosAlineados = false;
					
					for (int a=0; a<cantidadIngredientes-1; a++) {				
						int pos = c+(a*(cantidadIngredientes-1));
						posAux = cantidadesIngredientes.get(rv[pos]);
						posAux++;
						cantidadesIngredientes.put(rv[pos], posAux);
						if(!hayDosAlineados) {
							hayDosAlineados = (posAux == 2);
						}
					}
	
					//si hay 2 ingredientes alineados me fijo si el tercero es el comidin
					if(hayDosAlineados && cantidadesIngredientes.get("4") == 1) {
						resultado = true;
						break;
					}				
				}
			}	
		}
		return resultado;
	}

	/**
	 * Metodo que filtra los pasteles inv�lidos.
	 * Los que por ej tienen mas de 3 ingredientes de un mismo tipo
	 * @param pastel
	 * @return
	 */
	private static boolean esValido(String pastel) {
		String[] resultadoV = pastel.split("");
		boolean resultado = true;
		int contadorIngredientes = 0;
		
		for (Integer ing=1; ing<=4; ing++) {
			contadorIngredientes = 0;
			for (int a=0; a< resultadoV.length; a++) {
				if (ing.toString().equals(resultadoV[a])) {
					contadorIngredientes++;
				}
				if ((ing < 4 && contadorIngredientes > 3) || (ing == 4 && contadorIngredientes > 1)) {
					resultado = false;
					break;
				}
			}
			
			if (resultado == false) {
				break;
			}
		}
		
			
		
		return resultado;
	}
	
	
	/**
	 * Muestra por pantalla el pastel pasado por parametro
	 * @param pastel
	 */
	private static void imprimirPastel(String pastel) {
		String[] rv = pastel.split("");
		System.out.println("--------");
		System.out.println("  " + obtenerDescIng(rv[1]) + obtenerDescIng(rv[2]) + obtenerDescIng(rv[3]));
		System.out.println("  " + obtenerDescIng(rv[4]) + obtenerDescIng(rv[5]) + obtenerDescIng(rv[6]));
		System.out.println("  " + obtenerDescIng(rv[7]) + obtenerDescIng(rv[8]) + obtenerDescIng(rv[9]));
		
	}
	
	
	/**
	 * Obtiene la descripcion de los ingredientes
	 * 1 = dulces
	 * 2 = frutas
	 * 3 = confites
	 * 4 = masita
	 * @param nroIngrediente
	 */
	private static String obtenerDescIng(String nroIngrediente) {
		
		if ("1".equals(nroIngrediente)) {
			return DULCE;
		} else if ("2".equals(nroIngrediente)) {
			return FRUTA;
		} else if ("3".equals(nroIngrediente)) {
			return CONFITE;
		} else if ("4".equals(nroIngrediente)) {
			return MASITA;
		}
		return "";
	}
}
