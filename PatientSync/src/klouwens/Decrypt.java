package klouwens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Decrypt {

	public static Date decryptDate(String license, String activationKey) throws ParseException {
        StringBuilder decryptedString = new StringBuilder();

        for (int i = 0; i < license.length(); i++) {
            decryptedString.append((char) (license.charAt(i) ^ activationKey.charAt(i % activationKey.length())));
        }
        System.out.println("Decrypted String: " + decryptedString);
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.parse(decryptedString.toString());
    }
	public static Date decryptDate(String license) throws ParseException  {
        StringBuilder decryptedString = new StringBuilder();
        String activationKey = UserPreferences.getActivationKey();
        for (int i = 0; i < license.length(); i++) {
            decryptedString.append((char) (license.charAt(i) ^ activationKey.charAt(i % activationKey.length())));
        }
        System.out.println("Decrypted String: " + decryptedString);
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.parse(decryptedString.toString());
    }

	public static boolean validateDate() throws ParseException {
		Date expiryDate = decryptDate(UserPreferences.getLicense());
		Date now = new Date();
		if (expiryDate.after(now))
		{
			return true;
		}
		else
		{
			return false;
		}
	
	
	}

	
}
