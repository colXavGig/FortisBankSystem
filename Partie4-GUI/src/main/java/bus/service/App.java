package bus.service;

import java.sql.SQLException;
import java.time.LocalDate;

import bus.BusinessException;
import bus.model.account.CreditAccount;
import bus.model.account.CurrencyAccount;
import bus.model.account.SavingAccount;
import bus.model.notification.BankAccountTask;
import bus.model.notification.Task;
import bus.model.notification.TaskAction;
import utils.CurrencyAccountCollection;
import utils.TaskCollection;
import utils.UserCollection;

public class App extends Thread {

	private static App instance = null;
	
	private App() {}
	
	@Override
	public void run() {
		try {
			removeInactiveCurrencyAccounts();
			applyInterest();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	 * initialize the collections of the app, remove the inactive
	 * currency accounts and apply the interests on credit and 
	 * saving accounts
	 */
	public void init() {
		start();
	}
	
	/**
	 * save the current state of the application and exit with the exit code supplied
	 * @param exitCode The code the app should exit with
	 * @throws BusinessException  occurs when the file manager is not able to serialize
	 */
	public void exit(Integer exitCode) throws BusinessException {
		System.exit(exitCode);
	}

	/**
	 * create an account deletion task for every inactive currency account 
	 * @throws BusinessException 
	 */
	private void removeInactiveCurrencyAccounts() throws BusinessException {
		try {
			for (var account : CurrencyAccountCollection.getInstance().getData()) {
				if (!((CurrencyAccount) account).isActive()) {
					Task task = new BankAccountTask(
							UserCollection.getInstance().getClientById(account.getUserID()), 
							account, 
							TaskAction.DELETION
							);
					TaskCollection.getInstance().add(task);
				}
			}
		} catch (SQLException e) {
			throw new BusinessException("Error while removing inactive currency accounts", e);
		}
	}

	/**
	 * find the credit or saving account that needs interest to be applied  and applied them
	 * @throws BusinessException 
	 */
	void applyInterest() throws BusinessException {
		
		try {
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

					if(!interestApplied && account.getBalance() > 0) {
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
		} catch (SQLException e) {
			throw new BusinessException("Error applying interest", e);
		}
	}
	
	
}
