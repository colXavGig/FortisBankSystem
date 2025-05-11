package client.bankClient.accountCreator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bus.model.account.CreditAccount;

public class CreditAccountCreatorView extends JPanel {

	private static final long serialVersionUID = 7237429870737462582L;
	private JTextField creditLimit_textField;
	private JTextField interestRate_textField;

	public CreditAccountCreatorView() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Credit limit:");
		lblNewLabel.setBounds(51, 54, 88, 14);
		add(lblNewLabel);
		
		JLabel lblInterestRate = new JLabel("Interest rate");
		lblInterestRate.setBounds(51, 93, 88, 14);
		add(lblInterestRate);
		
		creditLimit_textField = new JTextField();
		creditLimit_textField.setBounds(205, 51, 86, 20);
		add(creditLimit_textField);
		creditLimit_textField.setColumns(10);
		
		interestRate_textField = new JTextField();
		interestRate_textField.setColumns(10);
		interestRate_textField.setBounds(205, 90, 86, 20);
		add(interestRate_textField);
	}
	
	public CreditAccount generateAccount() {
		var creditLimit = Double.parseDouble(interestRate_textField.getText());
		var interestRate = Double.parseDouble(interestRate_textField.getText());
		
		return new CreditAccount(creditLimit, interestRate);
	}


}
