package utils;

import java.sql.SQLException;
import java.util.List;

import bus.model.account.BankAccount;
import bus.model.transaction.TransactionRecord;
import data.dao.TransactionRecordDAO;

public class TransactionRecordCollection {

	private static TransactionRecordCollection instance = null;
	private static final TransactionRecordDAO dao = new TransactionRecordDAO();
	
	private TransactionRecordCollection() {}

	public static TransactionRecordCollection getInstance() {
		if (instance == null) {
			instance = new TransactionRecordCollection();
		}
		return instance;
	}


	public void add(TransactionRecord item, int accountID) throws SQLException {
		dao.add(item, accountID);		
	}


	public List<TransactionRecord> getData() throws SQLException {
		return dao.getAll();
	}
	
	public List<TransactionRecord> getFor(BankAccount account) throws SQLException {
		return dao.getFor(account);
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(TransactionRecord item) throws SQLException {
		return getData().contains(item);
	}


}
