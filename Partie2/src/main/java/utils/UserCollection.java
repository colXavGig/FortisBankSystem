package utils;

import java.util.ArrayList;
import java.util.List;

import bus.model.user.BankClient;
import bus.model.user.BankManager;
import bus.model.user.User;

public class UserCollection extends DataCollection<User> {

	private static UserCollection instance = null;

	public UserCollection() {
		super(new CollectionFileManager<>(User.class));
	}

	public static UserCollection getInstance() {
		if (instance == null) {
			instance = new UserCollection();
		}
		return instance;
	}

	public List<BankClient> getBankClients() {
		List<BankClient> bankClients = new ArrayList<>();
		for (User user : getData()) {
			if (user instanceof BankClient) {
				bankClients.add((BankClient) user);
			}
		}
		return bankClients;
	}

	public List<BankManager> getBankManagers() {
		List<BankManager> bankManagers = new ArrayList<>();
		for (User user : getData()) {
			if (user instanceof BankManager) {
				bankManagers.add((BankManager) user);
			}
		}
		return bankManagers;
	}


}
