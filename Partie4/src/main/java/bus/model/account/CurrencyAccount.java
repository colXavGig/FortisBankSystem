package bus.model.account;

import java.time.LocalDate;

import bus.BusinessException;
import bus.Currency;
import bus.model.transaction.Transaction;
import bus.model.transaction.TransactionRecord;

public class CurrencyAccount extends BankAccount {

	private static final long serialVersionUID = 3894528021858570189L;

	private Currency devise;
	private Double exchangeRate;


	public CurrencyAccount(Currency devise) {
		switch (devise) {
			case USD: setExchangeRate(0.7); break;
			case CAD: setExchangeRate(1.0); break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + devise.name());
		}
	}

	public Currency getDevise() {
		return devise;
	}

	public void setDevise(Currency devise) {
		this.devise = devise;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public void doTransaction(Transaction transaction) throws BusinessException {
		transaction.setAmount(
				transaction.getAmount() * exchangeRate
				);
		super.doTransaction(transaction);
	}

	public void doSameDeviseTransaction(Transaction transaction)
			throws Exception {
		super.doTransaction(transaction);
	}

	public boolean isActive() {
		var mostRecentTransaction = getMostRecentTransaction();

		return mostRecentTransaction.date().isBefore(
				LocalDate.now().minusYears(1)
				);
	}

	private TransactionRecord getMostRecentTransaction() {
		var transactions = getTransactionsHistory();
		transactions.sort((t1, t2) -> -t2.date().compareTo(t1.date())); // negate for descending order
		return transactions.get(0);
	}

}
