package crypting;

public class XOR {
	
	public static String encode_using_xor(final byte[] input, final byte[] secret) {
        final byte[] output = new byte[input.length];
        if (secret.length == 0) {
            throw new IllegalArgumentException("empty security key");
        }
        int spos = 0;
        for (int pos = 0; pos < input.length; ++pos) {
            output[pos] = (byte) (input[pos] ^ secret[spos]);
            ++spos;
            if (spos >= secret.length) {
                spos = 0;
            }
        }
        return new sun.misc.BASE64Encoder().encode(output);
    }
	public static String decode_operation(String s,final byte[] key){
		int spos = 0;
		try{
			byte [] output=(new sun.misc.BASE64Decoder().decodeBuffer(s));
        for (int pos = 0; pos < output.length; ++pos) {
            output[pos] = (byte) (output[pos] ^ key[spos]);
            ++spos;
            if (spos >= key.length) {
                spos = 0;
            }
        }
        return new String(output, "UTF-8");
        }catch(Exception e){
        	
        }
		return null;
	}

	public static void test(String[] args) {
		try{
		 String orig = "encode me £µé#^^]@12&~#{[,?-yassinefadhlaoui-engeneer";
		 String s="Iw8HBwgETxgMEvGW9d6m0EUxLAUPQ1dIHVEiK1hWQhcnEhcBAgQJFA1aX1RYHgxUAwEVPSEXABw=";
	        String key="Fadhlaoui2357keyforXOrencrYption";
	        byte [] input= orig.getBytes("UTF-8");
	        byte [] keyb=key.getBytes();
	        System.out.println("Original String : "+orig);
	        System.out.println("String after encrytion :  "+encode_using_xor(input,keyb));
	        System.out.println("String after decrytion :  "+decode_operation(s,keyb));
		}catch(Exception e){
			
		}
	        

	}

}