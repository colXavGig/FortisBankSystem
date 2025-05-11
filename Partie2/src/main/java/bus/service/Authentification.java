package bus.service;

import bus.BusinessException;
import bus.model.notification.UserCreationTask;
import bus.model.user.BankClient;
import bus.model.user.User;
import bus.model.account.CheckingAccount;
import utils.TaskCollection;
import utils.UserCollection;

public class Authentification {

	private Authentification() {} // removing public constructor

	public static User Login(String email, String nip) throws BusinessException {

		var users = UserCollection.getInstance().getData().stream().filter(u -> {
			return u.getEmail().equalsIgnoreCase(email);
		}).toList();

		if (users.size() == 1 && users.get(0).getPIN().equals(nip)) {
			return users.get(0);
		} else {
			throw new BusinessException("Invalid credentials");
		}
	}
	
	public static void register(User user) throws BusinessException {
		if (UserCollection.getInstance().filter(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))
				.size() != 0) {
			throw new BusinessException("User already exists");
		}
		if (user instanceof BankClient) {
			var client = (BankClient) user;
			var checkAccount = new CheckingAccount();
			IdentityInitiator.initID(checkAccount);
			client.addBankAccount(checkAccount);
		}
		var task  = new UserCreationTask(user);
		
		
		TaskCollection.getInstance().add(task);
	}
}
 