package bus.model.account;

import java.time.LocalDate;

import bus.BusinessException;
import bus.model.transaction.Deposit;
import bus.model.transaction.Transaction;

public class CheckingAccount extends BankAccount {



    /**
	 *
	 */
	private static final long serialVersionUID = -8821823035569504158L;
    private static final int LIMIT_TRANSACTIONS_GRATUITES = 2;
    private static final double FRAIS_TRANSACTION_SUPPLEMENTAIRE = 5.0;

    public CheckingAccount() {
        super();
    }

    @Override
    public void doTransaction(Transaction transaction) throws BusinessException {
    	boolean isFreeTransaction = numberOfTransactionsWithFeeInCurrentMonth() < LIMIT_TRANSACTIONS_GRATUITES;
    	if ((transaction instanceof Deposit)) {
    		isFreeTransaction = true;
    	}
        super.doTransaction(transaction);
        if (!isFreeTransaction) {
			Transaction frais = new TransactionFee();
			super.doTransaction(frais);
		}
    }

    private int numberOfTransactionsWithFeeInCurrentMonth() {
		return (int) getTransactionsHistory().stream()
				.filter(t -> t.date().getMonth() == LocalDate.now().getMonth())
				.filter(t -> (t.type().equalsIgnoreCase("withdraw")) || (t.type().equalsIgnoreCase("deposit")))
				.count();
	}
    
    public double getFraisTransactionSupplementaire() {
    	return FRAIS_TRANSACTION_SUPPLEMENTAIRE;
    }

    private class TransactionFee extends Transaction {

		private static final long serialVersionUID = 2955249538377451169L;

		public TransactionFee() {
			super("Frais de transaction");
			setAmount(FRAIS_TRANSACTION_SUPPLEMENTAIRE);
		}

		@Override
		protected void updateBalance(BankAccount account) {
			account.setBalance(account.getBalance() - getAmount());
		}
	}
}
