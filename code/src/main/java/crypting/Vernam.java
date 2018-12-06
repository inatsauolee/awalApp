package crypting;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

enum Bit {
	ZERO, ONE; 
	Bit xor(Bit bit) 
	{
		if (this == bit)
			return Bit.ZERO;
		return Bit.ONE; 
	}
};

public class Vernam {

	private Vernam(){ init(); }

	private void init()
	{

	}

	public static Bit[] encrypt(Bit[] key, Bit[] plaintext)
	{
		Bit[] ciphertext = new Bit[plaintext.length];

		for(int i = 0; i < plaintext.length; i++ )
		{
			ciphertext[i] = plaintext[i].xor(key[i]);
		}

		return ciphertext;
	}

	public static Bit[] decrypt(Bit[] key, Bit[] ciphertext)
	{
		Bit[] plaintext = new Bit[ciphertext.length];
		for(int i = 0; i < plaintext.length; i++ )
		{
			plaintext[i] = ciphertext[i].xor(key[i]);
		}
		return plaintext;
	}

	public static String Bits2String(Bit[] input)
	{
		String output = "";
		for(int i =0; i < input.length; i++ )
			output += input[i] == Bit.ZERO ? "0" : "1";

		return output;
	}

	public static void writeBitstoFile(String filename, Bit[] export)
	{
		try {
			FileOutputStream inputfis = new FileOutputStream(new File(filename));
			for(int i = 0; i < export.length; i++ )
			{
				inputfis.write( String.valueOf( export[i] == Bit.ZERO ? "0" : "1" ).getBytes());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bit[] readBitsFromFile(String filename)
	{
		int ch = -1;
		StringBuffer inputstr = new StringBuffer("");
		try {
			FileInputStream inputfis = new FileInputStream(new File(filename));
			while( (ch = inputfis.read()) != -1) inputstr.append((char) ch);
			inputfis.close();

			if( inputstr.length() > 0 )
			{
				Bit[] inputBits = new Bit[inputstr.length()];
				for(int i =0; i < inputstr.length(); i++) 
					inputBits[i] = inputstr.charAt(i) == '1' ? Bit.ONE : Bit.ZERO;

				return inputBits;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String operation = null, key = null, input = null, output = null;
		Bit[] inputBits = null, keyStream = null;
		if (args.length == 4) {

			if( (operation = args[0]) == "e" ||
					(operation = args[0]) == "d")
			{
				System.err.println("Operation Argument"
						+ " must be e for encrypt or d for decrypt");
				System.exit(1);
			}

			key = args[1];

			input = args[2];
			if( !( new File(input) ).exists())
			{
				System.err.println("Input Argument"
						+ " input file must exist");
				System.exit(1);
			}

			output = args[3];
			if( ( new File(output) ) == null )
			{
				System.err.println("Output Argument"
						+ " output file already exists");
				System.exit(1);
			}

		}
		else
		{
			System.err.println("");
			System.exit(1);
		}

		try{
			/*
			 * Read in the input 
			 */
			inputBits = readBitsFromFile(input);
			if( inputBits == null )
			{
				System.err.println("There was no input in the file.");
				System.exit(1);
			}
			/*
			 * Generate Random Key
			 */
			keyStream = readBitsFromFile(key);//check to see if there bits in the file
			if( keyStream == null )
			{
				//no key bits in file so generate
				keyStream = new Bit[inputBits.length];
				SecureRandom random = SecureRandom.getInstance ("SHA1PRNG");
				final int STREAM_SIZE = inputBits.length;
				for (int i=0; i < STREAM_SIZE; i++) {
					keyStream[i] = (random.nextBoolean()) ? Bit.ZERO : Bit.ONE;
				}
				//save key bits to file
				writeBitstoFile(key, keyStream);
			}
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 		

		Bit[] result = null;
		int case_op = (operation.equals("e") ? 1 : 2);
		switch( case_op )
		{
		case 1://encrypt
		{
			Bit[] ciphertext = result = Vernam.encrypt(keyStream, inputBits);
			System.out.println("Key: "+ Vernam.Bits2String(keyStream));
			System.out.println("PlainText: "+ Vernam.Bits2String(inputBits));
			System.out.println("CipherText: "+ Vernam.Bits2String(ciphertext));
			break;
		}
		case 2://decrypt
		{
			Bit[] plaintext = result = Vernam.decrypt(keyStream, inputBits);
			System.out.println("Key: "+ Vernam.Bits2String(keyStream));
			System.out.println("CipherText: "+ Vernam.Bits2String(inputBits));
			System.out.println("PlainText: "+ Vernam.Bits2String(plaintext));
			break;
		}
		}

		/*
		 * Write in the output
		 */
		writeBitstoFile(output, result);
	}
}