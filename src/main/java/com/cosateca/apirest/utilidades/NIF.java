package com.cosateca.apirest.utilidades;

public class NIF {

	public static boolean validar(String nif) {

		//String letraMayuscula = "";

		if (nif.length() != 9 || Character.isLetter(nif.charAt(8)) == false) {
			return false;
		}

		// Al superar la primera restricción, la letra la pasamos a mayúscula
		//letraMayuscula = (this.dni.substring(8)).toUpperCase();

		
		if (soloNumeros(nif) == true && letraDNI(nif).equals(nif.substring(8))) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean soloNumeros(String nif) {

		int i, j = 0;
		String numero = "";

		String miDNI = "";
		String[] unoNueve = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		for (i = 0; i < nif.length() - 1; i++) {
			numero = nif.substring(i, i + 1);

			for (j = 0; j < unoNueve.length; j++) {
				if (numero.equals(unoNueve[j])) {
					miDNI += unoNueve[j];
				}
			}
		}

		if (miDNI.length() != 8) {
			return false;
		} else {
			return true;
		}
	}

	private static String letraDNI(String nif) {
		
		int miDNI = Integer.parseInt(nif.substring(0, 8));
		int resto = 0;
		String miLetra = "";
		String[] asignacionLetra = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
				"Q", "V", "H", "L", "C", "K", "E" };

		resto = miDNI % 23;

		miLetra = asignacionLetra[resto];

		return miLetra;
	}

}
