package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.BusinessException;
import bus.model.account.CheckingAccount;
import data.dao.CheckingAccountDAO;

public class CheckingAccountCollection {
	
	private static CheckingAccountCollection instance = null;
	private static final CheckingAccountDAO dao = new CheckingAccountDAO();
	
	public CheckingAccountCollection() {
		super();
	}
	
	public static CheckingAccountCollection getInstance() {
		if (instance == null) {
			instance = new CheckingAccountCollection();
		}
		return instance;
	}
	

	public List<CheckingAccount> getData() throws SQLException {
		var list = new ArrayList<CheckingAccount>();
		for (var item : dao.getAll()) {
			list.add(fillTransctionHistory(item));
		}
		return list;
	}
	
	public List<CheckingAccount> getFor(int userID) throws SQLException {
		return getData().stream()
				.filter(i -> i.getUserID() == userID)
				.toList();
	}

	public boolean isEmpty() throws SQLException {
		return dao.getAll().isEmpty();
	}

	public boolean contains(CheckingAccount item) throws SQLException {
		return getData().stream()
				.filter(i -> i.getID() == item.getID())
				.count() != 0;
	}

	public CheckingAccount get(int id) throws UtilException, SQLException {
		return fillTransctionHistory( dao.getById(id) );
	}

	public void add(CheckingAccount item, int userID) throws SQLException {
		dao.add(item, userID);
	}

	public void remove(CheckingAccount item) throws SQLException {
		dao.delete(item);		
	}

	public void update(CheckingAccount item) throws SQLException {
		dao.update(item);
	}
	
	private CheckingAccount fillTransctionHistory(CheckingAccount acc) throws SQLException {
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
