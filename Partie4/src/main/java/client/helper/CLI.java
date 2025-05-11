package client.helper;

import java.util.Scanner;

import bus.BusinessException;
import bus.service.App;

public class CLI {

	private static Scanner scanner = new Scanner(System.in);

	private CLI() {}
	
	public static String getString(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}
	
	public static void clear() {
		//implement clear console
		try {
			final String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e) {
			println(e.getMessage());
		}
	}

	public static void promptEnterToClear() {
		getString("Press enter to continue...");
		clear();
	}

	public static void exit(Integer exitCode) {
		scanner.close();
		try {
			App.getInstance().exit(exitCode);
		} catch (BusinessException e) {
			println("Error exiting application: " + e.getMessage());
			System.exit(exitCode);
		}
	}

	public static void println(String s) {
		System.out.println(s);
	}

	public static void errPrint(String err) {
		System.err.println(err);
	}

	public static int getInteger(String string) {
		while (true) {
			try {
				return Integer.parseInt(getString(string));
			} catch (NumberFormatException e) {
				errPrint("Invalid input. Please enter a number.");
			}
		}
	}

	public static double getDouble(String string) {
		while (true) {
			try {
				return Double.parseDouble(getString(string));
			} catch (NumberFormatException e) {
				errPrint("Invalid input. Please enter a number.");
			}
		}
	}
}
