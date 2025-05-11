package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.BusinessException;
import bus.model.account.CurrencyAccount;
import data.dao.CurrencyAccountDAO;

public class CurrencyAccountCollection {

	private static CurrencyAccountCollection instance = null;
	private static final CurrencyAccountDAO dao = new CurrencyAccountDAO();
	
	private CurrencyAccountCollection() {
		super();
	}
	
	public static CurrencyAccountCollection getInstance() {
		if (instance == null) {
			instance = new CurrencyAccountCollection();
		}
		return instance;
	}
	
	public void add(CurrencyAccount item, int userID) throws SQLException {
		dao.add(item, userID);
	}

	public void remove(CurrencyAccount item) throws SQLException {
		dao.delete(item);
	}

	public void update(CurrencyAccount item) throws SQLException {
		dao.update(item);
	}

	public List<CurrencyAccount> getData() throws SQLException {
		var list = new ArrayList<CurrencyAccount>();
		for (var item : dao.getAll()) {
			list.add(fillTransctionHistory(item));
		}
		return list;
	}
	
	public List<CurrencyAccount> getFor(int userID) throws SQLException {
		return getData().stream()
				.filter(item -> item.getUserID() == userID)
				.toList();
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(CurrencyAccount item) throws SQLException {
		return getData().contains(item);
	}

	public CurrencyAccount get(int id) throws SQLException {
		return fillTransctionHistory(dao.getById(id));
	}

	public void remove(int id) throws UtilException, SQLException {
		var item = get(id);
		if (item == null) {
			throw new UtilException("Item not found");
		}
		remove(item);
		
	}
	
	private CurrencyAccount fillTransctionHistory(CurrencyAccount acc) throws SQLException {
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
