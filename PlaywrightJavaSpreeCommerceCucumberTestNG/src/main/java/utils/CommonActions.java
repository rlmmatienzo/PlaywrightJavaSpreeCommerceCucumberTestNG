package utils;
import java.util.List;
import java.util.Random;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CommonActions {
	protected Browser browser;
	protected Page page;
	public static String extractValueFromAListOfListOfString(String key, List<List<String>> listName) {
        Integer keyColumnNumber = 0;

        for (int listNum = 0; listNum < listName.get(0).size() && !key.toLowerCase().contentEquals(listName.get(0).get(listNum).toLowerCase()); listNum++) {
            keyColumnNumber = listNum + 1;
        }
        return listName.get(1).get(keyColumnNumber);
    }

    public String extractValueFromAListOfListOfString(String key, List<List<String>> listName,Integer row) {
        Integer keyColumnNumber = 0;
        for (int listNum = 0; listNum < listName.get(0).size() && !key.toLowerCase().contentEquals(listName.get(0).get(listNum).toLowerCase()); listNum++) {
            keyColumnNumber = listNum + 1;
        }
        return listName.get(row).get(keyColumnNumber);
    }
    
    protected String getRandomPwd() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
