package client;

import bus.model.user.BankClient;
import bus.model.user.BankManager;
import bus.model.user.User;
import bus.model.user.UserType;
import bus.service.Authentification;
import client.helper.CLI;
import dto.UserDTO;

public class AuthUI {

	private AuthUI() {}

	public static UserDTO login() throws ClientException {
		var email = CLI.getString("Enter your email: ");
		var password = CLI.getString("Enter your password: ");
		UserDTO user;
		try {
			user = Authentification.Login(email, password);
		} catch (Exception e) {
			throw new ClientException("Login failed: " + e.getMessage(), e);
		}
		return user;
	}
	
	public static void register() {
		User newUser = null;

		while (newUser == null) {
			UserType choice = null;
			while (choice == null) {
				CLI.println("Available types:");
				for (var t : UserType.values()) {
					CLI.println(t.name());
				}
				try {
					choice = UserType.valueOf(CLI.getString("Enter user type: "));
				} catch (IllegalArgumentException e) {
					CLI.errPrint("Invalid user type. Please try again.\n");
					CLI.promptEnterToClear();
					
				}
			}
			var firstName = CLI.getString("firstname: ");
			var lastName = CLI.getString("lastname: ");
			var nip = CLI.getString("nip: ");
			var address = CLI.getString("address: ");
			var email = CLI.getString("email: ");
			var telephone = CLI.getString("telephone: ");
			switch (choice.ordinal()) {
				case 0: newUser = new BankClient(1000,
						lastName, 
						firstName, 
						nip, 
						address, 
						email, 
						telephone 
						);
				break;
				case 1: newUser = new BankManager(0, 
						lastName, 
						firstName, 
						nip, 
						address, 
						email, 
						telephone, 
						CLI.getString("role: ") 
						);
				break;
				default: CLI.errPrint(choice + " invalide");
			}
			try {
				Authentification.register(newUser);
			} catch (Exception e) {
				CLI.getString("Error while registering: " + e.getMessage());
				newUser = null;
			}
		}
		CLI.println("User registered successfully!");
		CLI.promptEnterToClear();
	}

}
