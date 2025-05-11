package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.BusinessException;
import bus.model.account.SavingAccount;
import data.dao.SavingAccountDAO;

public class SavingAccountCollection {
	
	private static SavingAccountCollection instance = null;
	private static final SavingAccountDAO dao = new SavingAccountDAO();
	
	private SavingAccountCollection() {
		super();
	}
	
	public static SavingAccountCollection getInstance() {
		if (instance == null) {
			instance = new SavingAccountCollection();
		}
		return instance;
	}

	public void add(SavingAccount item, int userID) throws SQLException {
		dao.add(item, userID);		
	}

	public void remove(SavingAccount item) throws SQLException {
		dao.delete(item);					
	}

	public void update(SavingAccount item) throws SQLException {
		dao.update(item);							
	}

	public List<SavingAccount> getData() throws SQLException {
		var list = new ArrayList<SavingAccount>();
		for (var item : dao.getAll()) {
			list.add(fillTransctionHistory(item));
		}
		return list;
	}
	
	public List<SavingAccount> getFor(int userID) throws SQLException {
		return getData().stream()
				.filter(item -> item.getUserID() == userID)
				.toList();
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(SavingAccount item) throws SQLException {
		return getData().contains(item);
	}

	public SavingAccount get(int id) throws SQLException {
		return fillTransctionHistory(dao.getById(id));
	}
	
	private SavingAccount fillTransctionHistory(SavingAccount acc) throws SQLException {
		for (var item : TransactionRecordCollection.getInstance().getFor(acc)) {
			try {
				acc.addTransactionToHistory(item);
			} catch (BusinessException e) {
				throw (SQLException)e.getCause();
			}
		}
		return acc;
	}

}
