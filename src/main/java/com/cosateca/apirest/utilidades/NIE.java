package com.cosateca.apirest.utilidades;

public class NIE {
	
	private final static String letrasNif = "TRWAGMYFPDXBNJZSQVHLCKE";
	
	
	private static char calculaLetra(String aux) {
        return letrasNif.charAt(Integer.parseInt(aux) % 23);
    }

	
	public static String calculaNie(String nie) {
        String str = null;
        
        if(nie.length()==9){
            nie=nie.substring(0, nie.length()-1);
        }

        if (nie.startsWith("X")) {
            str = nie.replace('X', '0');
        } else if (nie.startsWith("Y")) {
            str = nie.replace('Y', '1');
        } else if (nie.startsWith("Z")) {
            str = nie.replace('Z', '2');
        }

        return nie + calculaLetra(str);
    }
	

	
//	public static boolean validarNIE(String nie) {
//
//		
//		boolean esValido = false;
//		int i = 1;
//		int caracterASCII = 0;
//		char letra = ' ';
//		int miNIE = 0;
//		int resto = 0;
//		char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X','B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
//
//
//		if(nie.length() == 9 && Character.isLetter(nie.charAt(8))
//			&& nie.substring(0,1).toUpperCase().equals("X")
//			|| nie.substring(0,1).toUpperCase().equals("Y")
//			|| nie.substring(0,1).toUpperCase().equals("Z")) {
//
//			do {
//				caracterASCII = nie.codePointAt(i);
//				esValido = (caracterASCII > 47 && caracterASCII < 58);
//				i++;
//			} while(i < nie.length() - 1 && esValido);
//		}
//
//		if(esValido && nie.substring(0,1).toUpperCase().equals("X")) {
//			nie = "0" + nie.substring(1,9);
//		} else if(esValido && nie.substring(0,1).toUpperCase().equals("Y")) {
//			nie = "1" + nie.substring(1,9);
//		} else if(esValido && nie.substring(0,1).toUpperCase().equals("Z")) {
//			nie = "2" + nie.substring(1,9);
//		}
//
//		if(esValido) {
//			letra = Character.toUpperCase(nie.charAt(8));
//			miNIE = Integer.parseInt(nie.substring(1,8));
//			resto = miNIE % 23;
//			esValido = (letra == asignacionLetra[resto]);
//		}
//
//		
//		return esValido;
//	}
	
}
