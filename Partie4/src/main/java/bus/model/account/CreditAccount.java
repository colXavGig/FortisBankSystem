package bus.model.account;

import java.time.LocalDate;

import bus.BusinessException;
import bus.model.transaction.CreditInterest;
import bus.model.transaction.Transaction;
import bus.model.transaction.Transfer;
import bus.model.transaction.Withdraw;

public class CreditAccount extends BankAccount {
    /**
	 *
	 */
	private static final long serialVersionUID = 4445527086727120614L;
	private Double creditLimit;
    public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	private Double interestRate;

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}


    public CreditAccount(double creditLimit, double interestRate) {
        super();
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public void applyInterest() throws Exception {
        if (!interestApplied()) {
        	doTransaction(new CreditInterest(interestRate));
        }
    }

    @Override
    public void doTransaction(Transaction transaction) throws BusinessException {
    	if ((transaction instanceof Withdraw || transaction instanceof Transfer )&&
			getBalance() - transaction.getAmount() < -creditLimit) {
				throw new BusinessException("%s exceeds credit limit".formatted(transaction.getClass().getName()));
		}
    	super.doTransaction(transaction);
    }

    private boolean interestApplied() {
		return getTransactionsHistory().stream()
				.filter(t -> t.type().contains("Interest") && t.date().isAfter(LocalDate.now().minusMonths(1)))
				.count() > 0;
    }
}
