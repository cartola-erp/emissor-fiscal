package net.cartola.emissorfiscal.util;

import java.util.HashSet;
import java.util.Set;

public class StringUtil {

	private StringUtil() {}

	public static String prepareString(String aux) {
		if (aux == null) {
			aux = "";
		} else {
			if (aux.contains("”")) {
				aux = replace(aux, "”", "\"");
			}
			aux = replace(aux, "\\", "\\\\");
			aux = replace(aux, "\"", "\\\"");
		}
		return aux.trim();
	}

	public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuilder result = new StringBuilder();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static boolean isNull(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	public static String somenteLetras(String string) {
		if (isNull(string)) {
			return string;
		}
		char[] characters = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char character : characters) {
			if (Character.isLetter(character)) {
				sb.append(character);
			}
		}
		return sb.toString();
	}

	//para nome
	public static String somenteLetrasEspaco(String string) {
		if (isNull(string)) {
			return string;
		}
		char[] characters = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		int aux = 0;
		int ultimaLetra = 0;
		int contagemLetras = 0;
		for (char character : characters) {
			if (Character.isLetter(character)) {
				ultimaLetra = string.indexOf(character, aux);
			}
			aux++;
		}
		aux = 0;
		for (char character : characters) {
			if (Character.isLetter(character) || character == '\'' && !sb.toString().contains("'") && contagemLetras > 0
					|| (Character.isSpaceChar(character) && contagemLetras > 0 && aux + 1 <= ultimaLetra
							&& Character.isLetter(string.charAt(aux + 1)))) {
				sb.append(character);
				contagemLetras++;
			}
			aux++;
		}
		return sb.toString();
	}

	public static String somenteNumeros(String string) {
		return somenteNumeros(string, null);
	}

	public static String somenteNumeros(String string, char... ignorados) {
		if (isNull(string)) {
			return string;
		}

		Set<Character> ignoradosSet = new HashSet<Character>();
		if (ignorados != null) {
			for (char c : ignorados) {
				ignoradosSet.add(c);
			}
		}
		char[] characters = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char character : characters) {
			if (ignorados != null) {
				if (ignoradosSet.contains(character)) {
					sb.append(character);
				}
			}
			if (Character.isDigit(character)) {
				sb.append(character);
			}
		}
		return sb.toString();
	}

	public static String somenteNumerosELetras(String string) {
		return somenteNumerosELetras(string, false);
	}

	public static String somenteNumerosELetras(String string, boolean ignoraEspacos, char... ignorados) {
		if (isNull(string)) {
			return string;
		}
		Set<Character> ignoradosSet = new HashSet<Character>();
		if (ignorados != null) {
			for (char c : ignorados) {
				ignoradosSet.add(c);
			}
		}
		char[] characters = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char character : characters) {
			if (Character.isLetterOrDigit(character) || (ignoradosSet.contains(character))
					|| (ignoraEspacos && Character.isSpaceChar(character))) {
				sb.append(character);
			}
		}
		return sb.toString();
	}

	public static String somenteNumerosComX(String string) {
		if (isNull(string)) {
			return string;
		}
		char[] characters = string.toCharArray();
		StringBuilder sb = new StringBuilder();

		for (char character : characters) {
			if ((Character.isLetter(character) && (character == 'X' || character == 'x'))
					|| (Character.isDigit(character))) {
				if (character == 'x') {
					character = 'X';
				}
				sb.append(character);
			}
		}
		return sb.toString();
	}

	public static boolean isCpf(String cpf) {
		cpf = somenteNumerosComX(cpf);
		if (cpf != null && !cpf.isEmpty() && cpf.length() == 11) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isCnpj(String cnpj) {
		cnpj = somenteNumeros(cnpj);
		if (cnpj != null && !cnpj.isEmpty() && cnpj.length() == 14) {
			return true;
		} else {
			return false;
		}
	}

	public static String limitaTamanho(String string, int tamanhoMaximo) {
        if (isNull(string)) {
            return string;
        }
        if (string.length() > tamanhoMaximo) {
            string = string.substring(0, tamanhoMaximo);
        }
        return string;
    }
	
    public static String notNull(String str) {
        return (str == null ? "" : str);
    }
	
}
