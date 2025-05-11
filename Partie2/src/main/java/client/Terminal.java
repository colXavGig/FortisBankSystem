package client;

import bus.model.user.BankClient;
import bus.model.user.BankManager;
import bus.model.user.User;
import bus.service.App;
import client.helper.CLI;

public class Terminal {

	public static void main(String[] args) {
		try {
			App.getInstance().init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			CLI.println("Choose action:");
			CLI.println("1. Login");
			CLI.println("2. register");

			var choice = CLI.getString("Enter your choice: ");
			CLI.clear();
			
			if (choice.equalsIgnoreCase("1")) {
				login();
				break;
			} else if (choice.equalsIgnoreCase("2")) {
				register();
				break;
			} else {
				continue;
			}
		}
		CLI.println("\n\nThank you for using our system!\n\n");
		CLI.exit(0);
	}

	private static void register() {
		AuthUI.register();
	}

	private static void login() {
		// TODO Auto-generated method stub
		User user = null;
		while (user == null) {
			try {
				user = AuthUI.login();
			} catch (ClientException e) {
				CLI.errPrint("Error: " + e.getMessage());
			}
		}

		if (user instanceof BankClient) {
			var clientDashboard = new ClientDashboard((BankClient) user);
			clientDashboard.display();
		} else if (user instanceof BankManager) {
			var managerDashboard = new ManagerDashboard((BankManager) user);
			managerDashboard.display();
		} else {
			CLI.println("Unknown user type.");
			CLI.exit(1);
		}
	}

}
