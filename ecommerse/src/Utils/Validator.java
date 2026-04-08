package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	// Email validation through regex patterns.
	public static boolean isValidEmail(String email) {
		 final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		 final Pattern pattern = Pattern.compile(EMAIL_REGEX);
		 if (email == null) {
			 return false;
		 }else {
			 Matcher matcher = pattern.matcher(email);
			 return matcher.matches();
		 }
	}  

	// Password validation through regex
	// Password must be of length 8-12 characters and must contain
	// at least one uppercase character, at least one special character and 
	// one special symbol.
	public static boolean isValidPassword(String password) {
		final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,12}$";

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	    return pattern.matcher(password).matches();
	}
	
	// Number validation to ensure no negative data should be inserted to database.
	public static boolean isPositiveNumber(int number) {
		if(number>=0) {
			return true;
		} else {
			return false;
		}
	}
	
	// Validating empty strings to ensure no empty data showed up to the database.
	public static boolean isNotEmpty(String string) {
		if(string.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
