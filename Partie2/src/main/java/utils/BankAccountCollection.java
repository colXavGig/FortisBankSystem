package utils;

import bus.model.account.BankAccount;

public class BankAccountCollection extends IdentifiableCollection<BankAccount> {

	private static BankAccountCollection instance = null;

	protected BankAccountCollection() {
		super(new CollectionFileManager<BankAccount>(BankAccount.class));
	}
	public static BankAccountCollection getInstance() {
		if (instance == null) {
			instance = new BankAccountCollection();
		}
		return instance;
	}

	public void refresh() {
		var instance = getInstance();
		instance.clear();
		UserCollection.getInstance().getBankClients().forEach(client -> {
			client.getBankAccounts().forEach(account -> {
				instance.add(account);
			});
		});
	}
}
