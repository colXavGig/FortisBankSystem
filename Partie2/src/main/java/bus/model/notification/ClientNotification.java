package bus.model.notification;

import java.io.Serializable;

import bus.model.account.BankAccount;
import bus.model.user.BankClient;

public class ClientNotification
	implements INotification, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5383744527254846163L;

	private BankClient client;
	private BankAccount account;
	private String message;
	public ClientNotification(BankClient client, BankAccount account, String message) {
		this.client = client;
		this.account = account;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return " Bonjour " + client.getFirstname() + " " + client.getLastname()  + ", "
				+ "vous avez une nouvelle notification sur votre compte " + account.getClass().getName() + ""
						+ "#" + account.getID() +".%n" + message;
	}

}
