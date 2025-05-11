package bus.model.transaction;

import bus.BusinessException;
import bus.model.account.BankAccount;

public class Transfer extends Transaction {

	private static final long serialVersionUID = 8994320345258149975L;

	private BankAccount destinationAccount;

	public Transfer(BankAccount destination) {
		super("Transfer");
		destinationAccount = destination;
	}


	@Override
	public void applyTo(BankAccount account) throws BusinessException {
		super.applyTo(account);
		super.applyTo(destinationAccount);
	}

	@Override
	protected void updateBalance(BankAccount account) {
		if (account == destinationAccount) {
			account.setBalance(
					account.getBalance() + getAmount()
					);
		} else {
			account.setBalance(
					account.getBalance() - getAmount()
					);
		}
	}

	// TODO: modify transaction record to include destination account
}
