package data.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.user.BankClient;
import data.DbConnection;

public class ClientDAO implements DAO<BankClient> {

	public ClientDAO() {}

	public int add(BankClient client) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateClient(?,?,?,?,?,?)}");
			
			stmt.setString(1, client.getFirstname());
			stmt.setString(2, client.getLastname());
			stmt.setString(3, client.getPIN());
			stmt.setString(4, client.getAddress());
			stmt.setString(5, client.getTelephoneNumber());
			stmt.setString(6, client.getEmail());
			
			return stmt.executeUpdate();
		}
	}

	public int update(BankClient client) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateClient(?,?,?,?,?,?,?)}");

			stmt.setInt(1, client.getID());
			stmt.setString(2, client.getFirstname());
			stmt.setString(3, client.getLastname());
			stmt.setString(4, client.getPIN());
			stmt.setString(5, client.getAddress());
			stmt.setString(6, client.getTelephoneNumber());
			stmt.setString(7, client.getEmail());
			
			return stmt.executeUpdate();
		}
	}

	public int delete(BankClient client) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteClient(?)}");

			stmt.setInt(1, client.getID());
			
			return stmt.executeUpdate();
		}
	}

	public BankClient getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadClientByID(?)}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.setInt(2, id);
			stmt.execute();
			var res = stmt.getObject(1, java.sql.ResultSet.class);
			if (res.next()) {
				var client = new BankClient(
						res.getInt(1),
						res.getString(3),
						res.getString(2),
						res.getString(4),
						res.getString(5),
						res.getString(7),
						res.getString(6)
						);
				
				return client;
			}
			return null;
		}
	}

	public List<BankClient> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllClients}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, java.sql.ResultSet.class);
			var clients = new ArrayList<BankClient>();
			while (res.next()) {
			
				clients.add(new BankClient(
					res.getInt(1),
					res.getString(3),
					res.getString(2),
					res.getString(4),
					res.getString(5),
					res.getString(7),
					res.getString(6)
					)
				);
			}
			
			return clients;
		}
	}

	public List<BankClient> getWhere(String clauses) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareStatement("SELECT * FROM \"ClientView\" WHERE " + clauses);
			var res = stmt.executeQuery();
			var clients = new ArrayList<BankClient>();
			while (res.next()) {
			
				clients.add(new BankClient(
					res.getInt(1),
					res.getString(3),
					res.getString(2),
					res.getString(4),
					res.getString(5),
					res.getString(7),
					res.getString(6)
					)
				);
			}
			
			return clients;
		}
	}
	

}
