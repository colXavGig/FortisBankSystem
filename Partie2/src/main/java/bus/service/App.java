package bus.service;

import java.time.LocalDate;

import bus.BusinessException;
import bus.model.account.CreditAccount;
import bus.model.account.CurrencyAccount;
import bus.model.account.SavingAccount;
import bus.model.notification.BankAccountTask;
import bus.model.notification.Task;
import bus.model.notification.TaskAction;
import utils.BankAccountCollection;
import utils.TaskCollection;
import utils.TransactionRecordCollection;
import utils.UserCollection;
import utils.UtilException;

public class App {

	private static App instance = null;
	
	private App() {}
	
	/**
	 * get the instance of the App singleton and initialize the instance if it is null
	 * @return the instance of the App singleton
	 */
	public static App getInstance() {
		if (instance == null) {
			instance = new App();
		}
		return instance;
	}
	
	/**
	 * refresh the data for all collections
	 */
	public void refreshCollections() {
		BankAccountCollection.getInstance().refresh();
		TransactionRecordCollection.getInstance().refresh();
	}

	/**
	 * initialize the collections of the app, remove the inactive
	 * currency accounts and apply the interests on credit and 
	 * saving accounts
	 */
	public void init() {
		loadCollections();
		removeInactiveCurrencyAccounts();
		applyInterest();
	}
	
	/**
	 * save the current state of the application and exit with the exit code supplied
	 * @param exitCode The code the app should exit with
	 * @throws BusinessException  occurs when the file manager is not able to serialize
	 */
	public void exit(Integer exitCode) throws BusinessException {
		saveCollections();
		IdentityInitiator.save();
		System.exit(exitCode);
	}

	/**
	 * create an account deletion task for every inactive currency account 
	 */
	private void removeInactiveCurrencyAccounts() {
		var taskCollection = TaskCollection.getInstance();
		UserCollection.getInstance().getBankClients().forEach(client -> {
			client.getBankAccounts().stream()
			.filter((account) -> account instanceof CurrencyAccount)
			.forEach(account -> {
				if (!((CurrencyAccount) account).isActive()) {
					Task task = new BankAccountTask(client, account, TaskAction.DELETION);
					taskCollection.add(task);
				}
			});
		});
	}

	/**
	 * find the credit or saving account that needs interest to be applied  and applied them
	 */
	void applyInterest() {
		UserCollection.getInstance().getBankClients().stream()
		.forEach(client -> {
			client.getBankAccounts().stream()
			.filter((account) -> account instanceof CreditAccount || account instanceof SavingAccount)
			.forEach(account -> {
				boolean interestApplied =
				account.getTransactionsHistory().stream()
				.filter(record -> record.type().contains("Interest"))
				.filter(record -> record.date().isAfter(LocalDate.now().minusMonths(1)))
				.count() > 0 ;

				if(!interestApplied) {
					if (account instanceof CreditAccount) {
						try {
							((CreditAccount) account).applyInterest();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (account instanceof SavingAccount) {
						try {
							((SavingAccount) account).appliquerInterets();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		});
	}
	
	/**
	 * save the state of all the app collections
	 * @throws BusinessException occurs when the file manager is not able to serialize 
	 * the data for reason like file not found or IO exception 
	 */
	public void saveCollections() throws BusinessException  {
		try {
			TaskCollection.getInstance().save();
			UserCollection.getInstance().save();
			BankAccountCollection.getInstance().save();
			TransactionRecordCollection.getInstance().save();
		} catch (UtilException e) {
			throw new BusinessException("Error saving collections", e);
		}
	}
	
	/**
	 * load data from the serialized file
	 */
	public void loadCollections() {
		TaskCollection.getInstance().load();
		UserCollection.getInstance().load();
		BankAccountCollection.getInstance().load();
		TransactionRecordCollection.getInstance().load();
	}
}
