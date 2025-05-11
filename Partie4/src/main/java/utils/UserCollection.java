package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.user.BankClient;
import bus.model.user.BankManager;
import bus.model.user.User;
import data.dao.ClientDAO;
import data.dao.ManagerDAO;
import data.dao.UserDAO;
import dto.UserDTO;

public class UserCollection {

	private static UserCollection instance = null;
	private static final ClientDAO clientDAO = new ClientDAO();
	private static final ManagerDAO managerDAO = new ManagerDAO();

	public UserCollection() {
		super();
	}

	public static UserCollection getInstance() {
		if (instance == null) {
			instance = new UserCollection();
		}
		return instance;
	}

	public List<BankClient> getBankClients() throws SQLException {
		var list = new ArrayList<BankClient>();
		for (var item : clientDAO.getAll()) {
			list.add( fillBankAccounts(item) );
		}
		return list;
	}

	public List<BankManager> getBankManagers() throws SQLException {
		return managerDAO.getAll();
	}

	public void add(User item) throws SQLException {
		if (item instanceof BankClient) {
			clientDAO.add((BankClient) item);
		} else if (item instanceof BankManager) {
			managerDAO.add((BankManager) item);
		}
	}

	public void remove(User item) throws SQLException {
		if (item instanceof BankClient) {
			clientDAO.delete((BankClient) item);
		} else if (item instanceof BankManager) {
			managerDAO.delete((BankManager) item);
		}
	}

	public void update(User item) throws SQLException {
		if (item instanceof BankClient) {
			clientDAO.update((BankClient) item);
		} else if (item instanceof BankManager) {
			managerDAO.update((BankManager) item);
		}
		
	}

	public List<User> getData() throws SQLException {
		var list = new ArrayList<User>();
		list.addAll(getBankClients());
		list.addAll(getBankManagers());
		return list;
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(User item) throws SQLException {
		return getData().contains(item);
	}

	public BankClient getClientById(int id) throws SQLException {
		return fillBankAccounts( clientDAO.getById(id) );
	}
	
	public BankManager getManagerById(int id) throws SQLException {
		return managerDAO.getById(id);
	}
	
	public UserDTO getByEmail(String email) throws SQLException {
		var dao = new UserDAO();
		var user = dao.getByEmail(email);
		return user;
	}
	
	private BankClient fillBankAccounts(BankClient client) throws SQLException {
		for (var account : CurrencyAccountCollection.getInstance().getFor(client.getID())) {
			client.addBankAccount(account);
		}
		for (var account : SavingAccountCollection.getInstance().getFor(client.getID())) {
			client.addBankAccount(account);
		}
		for (var account : CheckingAccountCollection.getInstance().getFor(client.getID())) {
			client.addBankAccount(account);
		}
		for (var account : CreditAccountCollection.getInstance().getFor(client.getID())) {
			client.addBankAccount(account);
		}
		return client;
	}
}
