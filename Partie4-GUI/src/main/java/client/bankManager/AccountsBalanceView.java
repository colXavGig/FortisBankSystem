package client.bankManager;

import javax.swing.JPanel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bus.model.account.BankAccount;
import utils.CheckingAccountCollection;
import utils.CreditAccountCollection;
import utils.CurrencyAccountCollection;
import utils.SavingAccountCollection;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountsBalanceView extends JPanel {

	private static final long serialVersionUID = -6164817961623626697L;
	private JTable balance_table;
	private List<BankAccount> bankAccounts;

	public AccountsBalanceView(ManagerDashboard parent) {
		super();
		setLayout(null);
		
		bankAccounts = new ArrayList<BankAccount>();
		try {
			bankAccounts.addAll(CheckingAccountCollection.getInstance().getData());
			bankAccounts.addAll(SavingAccountCollection.getInstance().getData());
			bankAccounts.addAll(CreditAccountCollection.getInstance().getData());
			bankAccounts.addAll(CurrencyAccountCollection.getInstance().getData());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error loading accounts");
		}
		
		JButton back_Button = new JButton("Back");
		back_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.navBack();
			}
		});
		back_Button.setBounds(10, 11, 89, 23);
		add(back_Button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 45, 420, 244);
		add(scrollPane);
		
		balance_table = new JTable();
		balance_table.setModel(new AbstractTableModel() {
			
			private static final long serialVersionUID = -3482525068832029001L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				var account = bankAccounts.get(rowIndex);
				
				switch (columnIndex) {
				case 0: return account.getID();
				case 1: return account.getClass().getName();
				case 2: return account.getBalance(); 
				default: return account.getClass();
				}
			}
			
			@Override
			public String getColumnName(int column) {
				switch (column) {

				case 0: return "ID";
				case 1: return "Type";
				case 2: return "Balance";
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + column);
				}
			}
			
			@Override
			public int getRowCount() {
				return bankAccounts.size();
			}
			
			@Override
			public int getColumnCount() {
				return 3;
			}
		});
		scrollPane.setColumnHeaderView(balance_table);
		
	}
}
