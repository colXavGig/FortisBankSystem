package bus.model.account;

import java.time.LocalDate;
import java.time.LocalDateTime;

import bus.model.transaction.Transaction;

public class SavingAccount extends BankAccount {

	/**
	 *
	 */
	private static final long serialVersionUID = 6800993492860456511L;

	private Double tauxInteret;

	public SavingAccount(double tauxInteret) {
		super();
		this.tauxInteret = tauxInteret;
	}

	public double getTauxInteret(){
		return tauxInteret;
	}
	public void setTauxInteret(double tauxInteret )
	{
		this.tauxInteret=tauxInteret;
	}



    public void appliquerInterets() throws Exception {
    	if (needInterest()) {
    		var interest = new SavingInterest(getTauxInteret());
    		interest.setAmount(this.getBalance() * tauxInteret);
			// Save the transaction
    		doTransaction(interest);
        }
    }


    @Override
    public String toString() {
        return "CompteEpargne [ID=" + getID() + ", Solde=" + getBalance() +
               ", Taux d'intérêt=" + tauxInteret + "%]";
    }

    public boolean needInterest() {
		var noInterestCurrentYear = (getTransactionsHistory().isEmpty() && getCreationDate().isBefore(LocalDateTime.now().minusYears(1))) ||
				getTransactionsHistory().stream()
				.filter(t -> {
					return t.type().contains("Interest") || t.date().isAfter(LocalDate.now().minusYears(1));
				}).count() == 0;
		return noInterestCurrentYear && getBalance() > 0;
	}


    private class SavingInterest extends Transaction {


    	/**
    	 *
    	 */
    	private static final long serialVersionUID = 5704760491537701602L;

    	private Double interestRate;

    	public SavingInterest(double tauxInteret) {
    		super("Saving Interest");
    		setInterestRate(tauxInteret);
    	}

    	@Override
    	protected void updateBalance(BankAccount account) {
    		// TODO Auto-generated method stub
    		setAmount(account.getBalance() * getInterestRate());
            account.setBalance(
            		account.getBalance() + getAmount()
            		);

    	}

    	public Double getInterestRate() {
    		return interestRate;
    	}

    	public void setInterestRate(Double interestRate) {
    		this.interestRate = interestRate;
    	}

    }



}

