package bus.model.notification;

import bus.model.account.BankAccount;
import bus.model.user.BankClient;
import utils.UserCollection;

public class BankAccountTask extends Task {

	private static final long serialVersionUID = 1819235165661054804L;
	private BankClient client;
	private BankAccount account;
	private TaskAction action;

	public BankAccountTask(BankClient client, BankAccount account, TaskAction action) {
		super();
		setClient(client);
		setAccount(account);
		setAction(action);
	}

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

	public TaskAction getAction() {
		return action;
	}

	public void setAction(TaskAction action) {
		this.action = action;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approve() {
		if( action == TaskAction.CREATION) {
			createAccount();
		} else if (action == TaskAction.DELETION) {
			deleteAccount();
		}
	}

	public void createAccount() {
		int index = UserCollection.getInstance().indexOf(client);
		client.addBankAccount(account);
		UserCollection.getInstance().update(index, client);
	}
	
	public void deleteAccount() {
		int index = UserCollection.getInstance().indexOf(client);
		client.removeBankAccount(account);
		UserCollection.getInstance().update(index, client);
	}

}
