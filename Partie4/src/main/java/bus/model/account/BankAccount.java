package bus.model.account;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bus.BusinessException;
import bus.model.Identifiable;
import bus.model.notification.ClientNotification;
import bus.model.transaction.Deposit;
import bus.model.transaction.Transaction;
import bus.model.transaction.TransactionRecord;
import bus.model.transaction.Transfer;
import bus.model.transaction.Withdraw;
import bus.model.user.BankClient;
import utils.TransactionRecordCollection;

public abstract class BankAccount 
implements Serializable, Identifiable<Integer> {

	private static final long serialVersionUID = 3753515648420331637L;

	private Integer ID;
	private Integer userID;
	private Double balance;
	private List<TransactionRecord> transactionsHistory;
	private LocalDateTime creationDate;

	public BankAccount() {
		setBalance(0.0);
		setTransactionsHistory(
				new ArrayList<>()
				);
		setCreationDate(LocalDateTime.now());
	}

	public Integer getID() {
		return ID;
	}
	public void setID(Integer id) {
		ID = id;
	}
	
	public boolean hasID() {
		return ID != null;
	}

	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public List<TransactionRecord> getTransactionsHistory() {
		return transactionsHistory;
	}
	public void setTransactionsHistory(List<TransactionRecord> transactionsHistory) {
		this.transactionsHistory = transactionsHistory;
	}
	public List<TransactionRecord> getSortedTransaction(Comparator<TransactionRecord> comparator) {
		transactionsHistory.sort(comparator);
		return transactionsHistory;
	}

	public void doTransaction(Transaction transaction) throws BusinessException {
		transaction.applyTo(this);
	}

	public void addTransactionToHistory(TransactionRecord transaction) throws BusinessException {
		this.transactionsHistory.add(transaction);
		try {
			TransactionRecordCollection.getInstance().add(transaction, this.ID);
		} catch (SQLException e) {
			throw new BusinessException("Error while updating transaction history", e);
		}
	}

	public void deposit(Double amount) throws BusinessException{
		var transaction = new Deposit();
		transaction.setAmount(amount);
		doTransaction(transaction);
	}
	public void withdraw(Double amount)
			throws BusinessException {
		Transaction transaction = new Withdraw();
		transaction.setAmount(amount);
		doTransaction(transaction);

	}
	public void transfer(BankAccount destination, Double amount)
			throws BusinessException {
		Transaction transaction = new Transfer(destination);
		transaction.setAmount(amount);
		doTransaction(transaction);
	}

	public ClientNotification generateNotification(BankClient client) {
		return new ClientNotification(
				client,
				this,
				"Your account balance is low: " + this.getBalance() + "$"
				);
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public void applyNotification(BankClient client) {
		if (this.getBalance() < 75) {
			var notif = generateNotification(client);
			client.addNotification(notif);
		}
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
