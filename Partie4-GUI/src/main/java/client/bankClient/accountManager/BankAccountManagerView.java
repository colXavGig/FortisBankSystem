package client.bankClient.accountManager;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bus.BusinessException;
import bus.model.account.BankAccount;
import bus.model.user.BankClient;
import client.bankClient.BankAccountActionView;

public class BankAccountManagerView extends JPanel {

	private static final long serialVersionUID = -140645226591956846L;
	
	private BankAccountActionView parentView;
	private BankAccount account;
	
	private Stack<String> navHistory;
	private CardLayout panelSwitcher;
	
	private TransactionHistoryView transactionHistory;
	private TransactionMakerView transactionMaker;
	private CheckBalanceView checkBalance;
	private JPanel accountRemoverJPanel;
	private JPanel selectorPanel;
	private JButton rmBtnButton;
	private JButton makeTransactBtn;
	private JButton transactHistoryBtn;
	private JButton checkBalanceBtn;
	private JButton rmAccountBtn;
	private JButton backBtn;
	private JPanel switchContainer;
	private JButton back_Button;

	public BankAccountManagerView(BankAccountActionView parent) {
		super();
		
		parentView = parent;
		
		navHistory = new Stack<String>();
		panelSwitcher = new CardLayout();
		switchContainer = new JPanel();
		switchContainer.setBounds(10, 40, 425, 250);
		switchContainer.setLayout(panelSwitcher);
		
		transactionHistory = new TransactionHistoryView(this);
		switchContainer.add(transactionHistory, "Transact history");
		
		transactionMaker = new TransactionMakerView(this);
		switchContainer.add(transactionMaker, "Transact maker");
		
		checkBalance = new CheckBalanceView(this);
		switchContainer.add(checkBalance, "Balancer checker");
		
		accountRemoverJPanel = new JPanel();
		accountRemoverJPanel.setLayout(null);
		rmBtnButton = new JButton();
		rmBtnButton.setText("Remove account");
		rmBtnButton.setBounds(137, 110, 162, 41);
		accountRemoverJPanel.add(rmBtnButton);
		switchContainer.add(accountRemoverJPanel, "Remover view");
		
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.navBack();
			}
		});
		backBtn.setBounds(10, 11, 89, 23);
		accountRemoverJPanel.add(backBtn);
		
		selectorPanel = new JPanel();
		switchContainer.add(selectorPanel, "Selector view");
		selectorPanel.setLayout(null);
		
		makeTransactBtn = new JButton("Make transaction");
		makeTransactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTransactionMaker();
			}
		});
		makeTransactBtn.setBounds(37, 70, 126, 23);
		selectorPanel.add(makeTransactBtn);
		
		transactHistoryBtn = new JButton("See transaction history");
		transactHistoryBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTransactionHistory();
			}
		});
		transactHistoryBtn.setBounds(210, 70, 177, 23);
		selectorPanel.add(transactHistoryBtn);
		
		checkBalanceBtn = new JButton("Check balance");
		checkBalanceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCheckBalance();
			}
		});
		checkBalanceBtn.setBounds(37, 154, 126, 23);
		selectorPanel.add(checkBalanceBtn);
		
		rmAccountBtn = new JButton("Remove account");
		rmAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAccountRemover();
			}
		});
		setLayout(null);
		rmAccountBtn.setBounds(210, 154, 177, 23);
		selectorPanel.add(rmAccountBtn);
		
		
		add(switchContainer);
		
		back_Button = new JButton("Back");
		back_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navBack();
			}
		});
		back_Button.setBounds(10, 11, 89, 23);
		add(back_Button);
		
		showSelector();
	}


	public void setAccount(BankAccount account) {
		this.account = account;
		transactionHistory.setHistoryList(account.getTransactionsHistory());
		transactionMaker.setAccount(account);
		checkBalance.setAccount(account);
	}
	
	public void setClient(BankClient client) {
		rmBtnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.requestDeleteAccount(account);
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(accountRemoverJPanel, "error while deleting the account");
					return;
				}
				
				parentView.navBack();
			}
		});

	}
	
	public void setAccountList(List<BankAccount> accounts) {
		transactionMaker.setAccountList(accounts);
	}
	
	public void showTransactionHistory() {
		showPanel("Transact history");
	}
	
	public void showTransactionMaker() {
		showPanel("Transact maker");
	}
	
	public void showCheckBalance() {
		showPanel("Balancer checker");
	}
	
	public void showAccountRemover() {
		showPanel("Remover view");
	}
	
	public void showSelector() {
		showPanel("Selector view");
	}
	
	private void showPanel(String name) {
		navHistory.push(name);
		panelSwitcher.show(switchContainer, name);
	}
	
	void navBack() {
		if (navHistory.size() <= 1) {
			parentView.navBack();
		}
		navHistory.pop();
		panelSwitcher.show(switchContainer, navHistory.peek());
	}
	

}
