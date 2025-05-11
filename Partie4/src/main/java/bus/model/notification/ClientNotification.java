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

	private Integer ID;
	private BankClient client;
	private BankAccount account;
	public BankClient getClient() {
		return client;
	}

	public void setClient(BankClient client) {
		this.client = client;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	public ClientNotification(BankClient client, BankAccount account, String message) {
		this.client = client;
		this.account = account;
		this.message = message;
	}

	public String getMessage() {
		return " Bonjour " + client.getFirstname() + " " + client.getLastname()  + ", "
				+ "vous avez une nouvelle notification sur votre compte " + account.getClass().getName() + ""
						+ "#" + account.getID() +".%n" + message;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public boolean hasID() {
		return ID != null;
	}

}
