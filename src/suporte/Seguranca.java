package suporte;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguranca {
	
	public static String criptografia(String senha){
		String senhahex = null;
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
			byte messageDigestSenhaAdmin[] = algorithm.digest(senha.getBytes("UTF-8"));
			StringBuilder hexStringSenha = new StringBuilder();
			for (byte b : messageDigestSenhaAdmin) {
				hexStringSenha.append(String.format("%02X", 0xFF & b));
				}
			senhahex = hexStringSenha.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senhahex;
	}	
}
