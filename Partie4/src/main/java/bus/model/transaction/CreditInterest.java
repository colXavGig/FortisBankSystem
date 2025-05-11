package bus.model.transaction;

import bus.model.account.BankAccount;

public class CreditInterest extends Transaction {

	/**
	 *
	 */
	private static final long serialVersionUID = -5807902833115303200L;
	private Double interestRate;

	public CreditInterest(Double interestRate) {
		super("Credit Interest");
		this.interestRate = interestRate;
	}

	@Override
	protected void updateBalance(BankAccount account) {
            setAmount(Math.abs(account.getBalance()) * interestRate);
            account.setBalance(account.getBalance() - getAmount());
	}
}
