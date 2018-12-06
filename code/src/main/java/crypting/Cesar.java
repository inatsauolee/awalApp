package crypting;

public class Cesar {
	
	public static String crypter (String text, int key_value) {

		char alphabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };

		int i, j, value;

		int[] element = new int[text.length()];
		char[] dechiffre = new char[text.length()];
		String cryptedText="";
		//		
		//		for (j = 0; j < alphabet.length; j++) {
		//			if (cle == alphabet[j]) {
		//				key_value = j;
		//			}
		//		}

		System.out.println("La clef de déchiffrement vaut " + key_value);

		for (i = 0; i < text.length(); i++) {
			for (j = 0; j < alphabet.length; j++) {
				if (text.charAt(i) == alphabet[j]) {
					value = j - key_value;
					if (value < 0) {
						value = 26 + value;
					} else {
						value = value;
					}
					if (value >= 26) {
						element[i] = value % 26;
					} else {
						element[i] = value;
					}
				}
			}
		}

		for (i = 0; i < text.length(); i++) {
			if (text.charAt(i) == ' ') {
				dechiffre[i] = text.charAt(i);
			}
			for (j = 0; j < alphabet.length; j++) {
				if (element[i] == j) {
					dechiffre[i] = alphabet[j];
					System.out.print(dechiffre[i]);
					cryptedText+=dechiffre[i];
				}
			}
		}

		return cryptedText;
	}
	
	public static String decrypter (String text, int key_value) {

		char alphabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		
		int i, j, value;
		
		int[] element = new int[text.length()];
		char[] dechiffre = new char[text.length()];
		String decryptedText="";
//		
//		for (j = 0; j < alphabet.length; j++) {
//			if (cle == alphabet[j]) {
//				key_value = j;
//			}
//		}
		
		System.out.println("La clef de déchiffrement vaut " + key_value);
		
		for (i = 0; i < text.length(); i++) {
			for (j = 0; j < alphabet.length; j++) {
				if (text.charAt(i) == alphabet[j]) {
					value = j - key_value;
					if (value < 0) {
						value = 26 - value;
					} else {
						value = value;
					}
					if (value >= 26) {
						element[i] = value % 26;
					} else {
						element[i] = value;
					}
				}
			}
		}
		
		for (i = 0; i < text.length(); i++) {
			if (text.charAt(i) == ' ') {
				dechiffre[i] = text.charAt(i);
			}
			for (j = 0; j < alphabet.length; j++) {
				if (element[i] == j) {
					dechiffre[i] = alphabet[j];
					System.out.print(dechiffre[i]);
					decryptedText+=dechiffre[i];
				}
			}
		}
		
		return decryptedText;
	}
}
