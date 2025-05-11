package client.bankClient;

import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;

import bus.model.account.BankAccount;
import bus.model.user.BankClient;

public class ClientDashboard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720142767536763478L;
	
	private BankClient client;
	private CardLayout panelSwitcher;
	
	private JPanel selectorJPanel;
	
	
	Stack<String> navHistory;
	
	private ViewNotificationPanel notifPanel;
	private BankAccountActionView actionView;
	private JButton notifBtn;
	private JButton actionBtn;
	
	public ClientDashboard() {
		init();
	}


	public ClientDashboard(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public ClientDashboard(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}


	public void setClient(BankClient client) {
		this.client = client;
		actionView.setClient(client);
		notifPanel.setNotifs(client.getNotifications());
	}
	
	private void init() {
		navHistory = new Stack<String>();
		
		panelSwitcher = new CardLayout();
		setLayout(panelSwitcher);
		
		notifPanel = new ViewNotificationPanel(this);
		add(notifPanel, "Notif panel");

		actionView = new BankAccountActionView(this);
		add(actionView, "Action view");
		
		selectorJPanel = new JPanel();
		add(selectorJPanel, "Selection view");
		selectorJPanel.setLayout(null);
		
		notifBtn = new JButton("View notifications");
		notifBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNotifs();
			}
		});
		notifBtn.setBounds(52, 103, 130, 23);
		selectorJPanel.add(notifBtn);
		
		actionBtn = new JButton("Bank account management");
		actionBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showActionView();
			}
		});
		actionBtn.setBounds(221, 103, 174, 23);
		selectorJPanel.add(actionBtn);
		
		
		showSelector();
	}

	void showNotifs() {
		assert(client != null);
		var panelName = "Notif panel";
		navHistory.add(panelName);
		panelSwitcher.show(this, panelName);
	}
	
	void showActionView() {
		var panelName = "Action view";
		navHistory.push(panelName);
		panelSwitcher.show(this, panelName);
	}
	
	void showSelector() {
		var panelName = "Selection view";
		navHistory.push(panelName);
		panelSwitcher.show(this, panelName);
	}
	
	void navBack() {
		var backPanel = navHistory.pop();
		panelSwitcher.show(this, backPanel);
	}
	
	void createBankAccount(BankAccount account) {
		client.addBankAccount(account);
	}
	
}
