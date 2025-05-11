package bus.model.user;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import bus.BusinessException;
import bus.model.account.BankAccount;
import bus.model.notification.BankAccountTask;
import bus.model.notification.ClientNotification;
import bus.model.notification.Task;
import bus.model.notification.TaskAction;
import bus.model.transaction.Transaction;
import bus.model.transaction.TransactionRecord;
import utils.TaskCollection;

public class BankClient extends User
	implements IBankClient {

	/**
	 *
	 */
	private static final long serialVersionUID = 4968800577699797953L;
	private List<BankAccount> comptes;
	private List<ClientNotification> notifications;

	/**
	 * {@summary constructor for the Bank client}
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param nip
	 * @param adresse
	 * @param email
	 * @param telephone
	 */
    public BankClient(
    		Integer id, String nom, String prenom, String nip, String adresse, String email, String telephone
    		) {
        super(id, nom, prenom, nip, adresse, email, telephone);
        this.comptes = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void requestNewAccount(BankAccount account) {
		Task task = new BankAccountTask(this, account, TaskAction.CREATION);
		TaskCollection.getInstance().add(task);
	}

    public void requestDeleteAccount(BankAccount account) {
    	assert(getBankAccounts().contains(account));
		Task task = new BankAccountTask(this, account, TaskAction.DELETION);
		TaskCollection.getInstance().add(task);
    }



    @Override
    public String getInfos() {
        return "Client: " + getFirstname() + " " + getLastname() + ", Email: " + getEmail() +
			   ", Telephone: " + getTelephoneNumber() + ", Adresse: " + getAddress();

    }

	@Override
	public List<BankAccount> getBankAccounts() {
		return comptes;
	}

	@Override
	public void setBankAccounts(List<BankAccount> list) {
		this.comptes = list;
	}

	@Override
	public void addBankAccount(BankAccount account) {
		this.comptes.add(account);
	}

	@Override
	public void removeBankAccount(BankAccount account) {
		this.comptes.remove(account);
	}

	@Override
	public BankAccount getBankAccount(int accountID) {
		return comptes.stream()
				.filter(account -> account.getID() == accountID)
				.findFirst()
				.orElse(null);
	}

	@Override
	public void makeTransaction(Transaction transact, BankAccount account) throws BusinessException {
		if (! comptes.contains(account)) {
			throw new BusinessException("Account not found in client's accounts."); // TODO: Handle this exception properly with custom exception
		}
		account.doTransaction(transact);
		account.applyNotification(this);
	}

	@Override
	public Double checkBalance(BankAccount account) {
		if (! comptes.contains(account)) {
			throw new IllegalArgumentException("Account not found in client's accounts."); // TODO: Handle this exception properly with custom exception
		}
		return account.getBalance();
	}

	@Override
	public List<TransactionRecord> checkTransactionsHistory() {
		List<TransactionRecord> transactions = new ArrayList<>();
		for (BankAccount account : comptes) {
			transactions.addAll(account.getTransactionsHistory());
		}
		return transactions;
	}

	@Override
	public List<TransactionRecord> checkTransactionsHistory(BankAccount account) {
		if (! comptes.contains(account)) {
			throw new IllegalArgumentException("Account not found in client's accounts."); // TODO: Handle this exception properly with custom exception
		}
		return account.getTransactionsHistory();
	}

	public void addNotification(ClientNotification notif) {
		notifications.add(notif);
	}

	public boolean printNotifications(PrintStream printStream) {
		if (notifications.isEmpty()) {
			return false;
		}
		for (ClientNotification notif : notifications) {
			printStream.println(notif.getMessage());
			notifications.remove(notif);
		}
		return true;
	}

	public int getNumberOfNotifications() {
		return notifications.size();
	}





}

