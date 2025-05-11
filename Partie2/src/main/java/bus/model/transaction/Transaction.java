package bus.model.transaction;

import java.io.Serializable;
import java.time.LocalDate;

import bus.BusinessException;
import bus.model.account.BankAccount;

public abstract class Transaction implements Serializable {

	private static final long serialVersionUID = 2923352023714175172L;

	private Integer ID;
	private double amount;
	private String type;

	public Transaction(String type) {
		this.setType(type);
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	protected abstract void updateBalance(BankAccount account) throws BusinessException;

	public void applyTo(BankAccount account) throws BusinessException {
		if (getAmount() <= 0) {
			throw new BusinessException("Invalide amount. Cannot deposit %f $ into account".formatted(getAmount()));
		}
		var oldBalance = account.getBalance();
		updateBalance(account);

		account.addTransactionToHistory(
				generateTransactionRecord(
						account.getBalance(),
						account.getBalance() - oldBalance
						)
				);

	}

	public TransactionRecord generateTransactionRecord(Double newBalance, Double operationAmount) {
		return new TransactionRecord(
				getType(),
				newBalance,
				operationAmount,
				LocalDate.now()
				);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
