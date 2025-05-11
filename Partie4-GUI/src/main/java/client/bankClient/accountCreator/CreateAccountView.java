package client.bankClient.accountCreator;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import bus.model.account.BankAccount;
import bus.model.account.BankAccountType;
import bus.model.account.CheckingAccount;
import client.bankClient.BankAccountActionView;

public class CreateAccountView extends JPanel {

	private static final long serialVersionUID = -3639977753174761212L;
	
	private BankAccountActionView parent;

	public CreateAccountView(BankAccountActionView parent) {
		this.parent = parent;
		setLayout(null);
		
		
		
		
		var panelSwitcher = new CardLayout();
		JPanel panel = new JPanel(panelSwitcher);
		panel.setBounds(10, 69, 430, 220);
		
		var currencyCreator = new CurrencyAccountCreatorView();
		panel.add(currencyCreator, BankAccountType.Currency.name());
		
		var savingCreator = new SavingAccountCreatorView();
		panel.add(savingCreator, BankAccountType.Savings.name());
		
		var creditCreator = new CreditAccountCreatorView();
		panel.add(creditCreator, BankAccountType.Credit.name());
		
		panel.add(new JPanel(), BankAccountType.Checking.name());
		
		JComboBox<BankAccountType> comboBox = new JComboBox<BankAccountType>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSwitcher.show(panel, comboBox.getItemAt(comboBox.getSelectedIndex()).name());
			}
		});
		comboBox.setBounds(182, 35, 70, 20);
		comboBox.setModel(new DefaultComboBoxModel<BankAccountType>(BankAccountType.values()));
		add(comboBox);

		JButton createBtn = new JButton("Create");
		createBtn.setBounds(182, 311, 102, 32);
		add(createBtn, "name_175709989958200");
		createBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BankAccount account = null; 

				if (comboBox.getItemAt(comboBox.getSelectedIndex()) == BankAccountType.Currency) {
					account = currencyCreator.generateAccount();
				} else if (comboBox.getItemAt(comboBox.getSelectedIndex()) == BankAccountType.Savings) {
					account = savingCreator.generateSavingAccount();
				} else if (comboBox.getItemAt(comboBox.getSelectedIndex()) == BankAccountType.Credit) {
					account = creditCreator.generateAccount();
				} else {
					account = new CheckingAccount();
				}
				
				createBankAccount(account);
				
				parent.navBack();
			}
		});

		panelSwitcher.show(panel, comboBox.getItemAt(comboBox.getSelectedIndex()).name());
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.navBack();
			}
		});
		backBtn.setBounds(10, 21, 89, 23);
		add(backBtn);
		
		add(panel);
	}
	
	void createBankAccount(BankAccount account) {
		parent.createAccount(account);
	}
}
