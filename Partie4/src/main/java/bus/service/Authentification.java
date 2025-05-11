package bus.service;

import java.sql.SQLException;

import bus.BusinessException;
import bus.model.account.CheckingAccount;
import bus.model.notification.TaskAction;
import bus.model.notification.UserTask;
import bus.model.user.BankClient;
import bus.model.user.User;
import dto.UserDTO;
import utils.TaskCollection;
import utils.UserCollection;

public class Authentification {

	private Authentification() {} // removing public constructor

	public static UserDTO Login(String email, String nip) throws BusinessException {

		UserDTO user = null;
		try {
			user = UserCollection.getInstance().getByEmail(email);
		} catch (SQLException e) {
			throw new BusinessException("Error while getting user: " + e.getMessage(), e);
		}
		
		if (user == null) {
			throw new BusinessException("User not found");
		}
		if (!user.pin().equals(nip)) {
			throw new BusinessException("Invalid NIP");
		}
		return user;
	}
	
	public static void register(User user) throws BusinessException {
		try {
			if (UserCollection.getInstance().getData().stream()
					.filter(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))
					.count() !=0) {
				throw new BusinessException("User already exists");
			}
		} catch (SQLException e) {
			throw new BusinessException("Error while checking user existence", e);
		}
		if (user instanceof BankClient) {
			var client = (BankClient) user;
			var checkAccount = new CheckingAccount();
			client.addBankAccount(checkAccount);
		}
		var task  = new UserTask(user, TaskAction.CREATION,"User Creation");
		
		
		try {
			UserCollection.getInstance().add(user);
			TaskCollection.getInstance().add(task);
		} catch (SQLException e) {
			throw new BusinessException("Error while creating user task", e);
		}
	}
}
 