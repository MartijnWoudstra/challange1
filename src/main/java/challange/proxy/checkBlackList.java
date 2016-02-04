package proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class checkBlackList {
	
	private static String listLocation = "/home/lucas/git/challange1/src/main/java/challange/proxy/blocklist.txt";
	
	public static boolean isOnBlocklist (String str) {
		Scanner file = null;
		try {
			file = new Scanner(new File(listLocation));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (file.hasNext()) {
			if (str.contains(file.next())) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("_webiq." + isOnBlocklist("123124biq."));
	}
}
