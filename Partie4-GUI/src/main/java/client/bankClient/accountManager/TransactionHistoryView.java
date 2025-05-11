package client.bankClient.accountManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import bus.model.transaction.TransactionRecord;

public class TransactionHistoryView extends JPanel {

	private static final long serialVersionUID = 5241904740849819011L;
	
	private JTable table;

	public TransactionHistoryView(BankAccountManagerView parent) {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 51, 420, 238);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"HistoricID", "Type", "New balance", "Operation", "Date"
			}
		));
		
		setVisible(true);
	}

	public void setHistoryList(List<TransactionRecord> historyList) {
		table.setModel(new AbstractTableModel() {
			
			private static final long serialVersionUID = -5220282170655754381L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				var record = historyList.get(rowIndex);
				
				switch (columnIndex) {
				case 0: return record.historicID();
				case 1: return record.type();
				case 2: return record.newBalance();
				case 3: return record.operation();
				case 4: return record.date();
				default: return record.getClass();
				}
			}
			
			@Override
			public String getColumnName(int column) {
				switch (column) {

				case 0: return "Historic ID";
				case 1: return "Type";
				case 2: return "New Balance";
				case 3: return "Operation";
				case 4: return "Date";
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + column);
				}
			}
			
			@Override
			public int getRowCount() {
				
				return historyList.size();
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 5;
			}
		});
	}
}
