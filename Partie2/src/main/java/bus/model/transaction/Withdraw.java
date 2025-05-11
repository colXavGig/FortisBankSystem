package bus.model.transaction;

import bus.BusinessException;
import bus.model.account.BankAccount;

public class Withdraw extends Transaction {

	private static final long serialVersionUID = -4520018792360739988L;

    public Withdraw() {
    	super("Withdraw");
    }

	@Override
	protected void updateBalance(BankAccount account) throws BusinessException {
		if (account.getBalance() < getAmount()) {
            throw new BusinessException("Insufficient funds to complete the withdrawal.");
        }

        account.setBalance(account.getBalance() - getAmount());

	}
}
