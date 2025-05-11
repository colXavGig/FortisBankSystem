package utils;

import bus.model.transaction.TransactionRecord;

public class TransactionRecordCollection
extends DataCollection<TransactionRecord> {

	private static TransactionRecordCollection instance = null;

	private TransactionRecordCollection() {
		super(new CollectionFileManager<>(TransactionRecord.class));
	}

	public static TransactionRecordCollection getInstance() {
		if (instance == null) {
			instance = new TransactionRecordCollection();
		}
		return instance;
	}

	public void refresh() {
		var instance = getInstance();
		instance.clear();
		UserCollection.getInstance().getBankClients().forEach(client -> {
			client.getBankAccounts().forEach(account -> {
				account.getTransactionsHistory().forEach(record -> {
					instance.add(record);
				});
			});
		});
	}
}
