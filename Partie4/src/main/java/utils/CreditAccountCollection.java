package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.BusinessException;
import bus.model.account.CreditAccount;
import data.dao.CreditAccountDAO;

public class CreditAccountCollection {
	
	private static CreditAccountCollection instance = null;
	private static final CreditAccountDAO dao = new CreditAccountDAO();
	
	private CreditAccountCollection() {
		super();
	}
	
	public static CreditAccountCollection getInstance() {
		if (instance == null) {
			instance = new CreditAccountCollection();
		}
		return instance;
	}
	

	public void add(CreditAccount item, int userID) throws SQLException {
		dao.add(item, userID);
	}

	public void remove(CreditAccount item) throws SQLException {
		dao.delete(item);
	}
	
	public void update(CreditAccount item) throws SQLException {
		dao.update(item);
	}

	public List<CreditAccount> getData() throws SQLException {
		var list = new ArrayList<CreditAccount>();
		for (var item : dao.getAll()) {
			list.add(fillTransctionHistory(item));
		}
		return list;
	}
	
	public List<CreditAccount> getFor(int userID) throws SQLException {
		return getData().stream()
				.filter(i -> i.getUserID() == userID)
				.toList();
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(CreditAccount item) throws SQLException {
		return getData().contains(item);
	}

	public CreditAccount get(int id) throws SQLException {
		return fillTransctionHistory(dao.getById(id));
	}

	public void remove(int id) throws SQLException, UtilException {
		var item = get(id);
		if (item != null) {
			dao.delete(item);
		} else {
			throw new UtilException("Item not found");
		}
		
	}
	
	private CreditAccount fillTransctionHistory(CreditAccount acc) throws SQLException {
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
