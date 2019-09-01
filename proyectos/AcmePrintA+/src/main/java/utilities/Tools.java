package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Tools {
	/*
	 * This method generates an alphanumeric code of 6 digits
	 */
     
	private static String generateAlphaNumericCode() {
		// Create an empty string
		String res = "";

		// Create abecedario variblae with international capital letters
		final String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Create numeros variable
		final String numbers = "0123456789";

		// Combining abecedario and numbers into alphanumeric
		final String alphanumeric = abecedario + numbers;

		// Keep length of alphanumeric
		final int top_alphanumeric = alphanumeric.length() - 1;

		// Variable to obtain the character of th alphanumeric string
		char c;

		// Method to generate 6 alphanumeric random characters
		for (int i = 0; i < 6; i++) {
			// Generate a random number
			final int randomNumber = (int) Math.floor(Math.random()
					* top_alphanumeric);
			// Keeping the char value for randomNumber of the alphanumeric
			c = alphanumeric.charAt(randomNumber);
			// Save in res actual value + char of c value generated
			res += c;
		}
		// Return the result
		return res;
	}

	/*
	 * This is a method to generate a unique Ticker for the system
	 */
	public static String generateTicker() {
		// Create result variable
		String res = null;

		// Create date instance
		final Date fecha = new Date();
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final String formattedDate = df.format(fecha);

		// Generating alphanumeric code
		final String alphanumericCode = Tools.generateAlphaNumericCode();

		// Combining date + alphanumeric code
		res = formattedDate + "-" + alphanumericCode;

		// Returning ticker
		return res;
	}
	
	// Method to convert github url set by user to raw file access on github.
	public static String fromGithubToRaw(String url) {
		// input -> https://dominio.com/user/repo/blob/branch/file.stl
		// output -> https://dominio.com/user/repo/branch/file.stl
		String res;
		String in = url;
		String aux = in.replace("https://github.com/",
				"https://raw.githubusercontent.com/");
		res = aux.replace("blob/", "");
		res = res.trim();

		return res;
	}
}
