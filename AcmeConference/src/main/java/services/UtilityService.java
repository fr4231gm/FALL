package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtilityService {

	// Return true if at least one of the pictures is not an URL
	public boolean checkUrls(String urls) {
		boolean res = false;
		String[] aux = urls.split(", ");
		for (int i = 0; i < aux.length; i++) {
			if (!(aux[i]
					.matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
					&& aux[i].length() > 0) {
				res = true;
			}
		}
		return res;
	}
}
