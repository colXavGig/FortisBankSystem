package client;

import java.sql.SQLException;

import bus.model.user.BankClient;
import bus.model.user.BankManager;
import bus.service.App;
import client.helper.CLI;
import dto.UserDTO;
import utils.UserCollection;

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
		UserDTO userDTO = null;
		while (userDTO == null) {
			try {
				userDTO = AuthUI.login();
			} catch (ClientException e) {
				CLI.errPrint("Error: " + e.getMessage());
			}
		}
		BankClient client;
		BankManager manager;
		try {
			client = UserCollection.getInstance().getClientById(userDTO.id());
			manager = UserCollection.getInstance().getManagerById(userDTO.id());
		} catch (SQLException e) {
			CLI.errPrint("Error: " + e.getMessage());
			CLI.exit(1);
			return;
		}
		if (client != null) {
			var clientDashboard = new ClientDashboard(client);
			clientDashboard.display();
		} else if (manager != null) {
			var managerDashboard = new ManagerDashboard(manager);
			managerDashboard.display();
		} else {
			CLI.println("User not found.");
			CLI.exit(1);
		}
	}

}
