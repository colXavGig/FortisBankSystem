package bus.model.notification;

import bus.model.account.BankAccount;
import bus.model.user.BankClient;

public class BankAccountTask extends Task {

	private static final long serialVersionUID = 1819235165661054804L;
	private BankClient client;
	private BankAccount account;
	private int accountID;
	private TaskAction action;

	public BankAccountTask(BankClient client, BankAccount account, TaskAction action) {
		super();
		setClient(client);
		setAccount(account);
		setAction(action);
	}

	public BankAccountTask() {
		// TODO Auto-generated constructor stub
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
		setStatus(TaskStatus.accepted);
	}

	public int getAccountID() {
		if (account != null) {
			return account.getID();
		}
		return accountID;
	}

	public void setAccountID(int accountID) {
		if (account != null) {
			account.setID(accountID);
		} else {
			this.accountID = accountID;
		}
	}

}
