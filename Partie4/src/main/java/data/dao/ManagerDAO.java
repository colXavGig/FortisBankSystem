package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.user.BankManager;
import data.DbConnection;

public class ManagerDAO {

	public ManagerDAO() {
		// TODO Auto-generated constructor stub
	}

	public int add(BankManager item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateManager(?,?,?,?,?,?,?)}");

			stmt.setString(1, item.getFirstname());
			stmt.setString(2, item.getLastname());
			stmt.setString(3, item.getPIN());
			stmt.setString(4, item.getAddress());
			stmt.setString(5, item.getTelephoneNumber());
			stmt.setString(6, item.getEmail());
			stmt.setString(7, item.getRole());

			return stmt.executeUpdate();
		}
	}

	public int update(BankManager item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateManager(?,?,?,?,?,?,?,?)}");

			stmt.setInt(1, item.getID());
			stmt.setString(2, item.getFirstname());
			stmt.setString(3, item.getLastname());
			stmt.setString(4, item.getPIN());
			stmt.setString(5, item.getAddress());
			stmt.setString(6, item.getTelephoneNumber());
			stmt.setString(7, item.getEmail());
			stmt.setString(8, item.getRole());

			return stmt.executeUpdate();
		}
	}

	public int delete(BankManager item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteManager(?)}");

			stmt.setInt(1, item.getID());

			return stmt.executeUpdate();
		}
	}

	public BankManager getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadManagerByID(?)}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.setInt(2, id);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			if (res.next()) {
				return generateFrom(res);
			}
			return null;
		}
	}

	public List<BankManager> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllManagers}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var list = new ArrayList<BankManager>();
			while (res.next()) {
				list.add(generateFrom(res));
			}

			return list;
		}
	}
	
	public BankManager generateFrom(ResultSet res) throws SQLException {
		return new BankManager(
				res.getInt("managerID"),
				res.getString("lastname"),
				res.getString("firstname"),
				res.getString("nip"),
				res.getString("address"),
				res.getString("telephone"),
				res.getString("email"),
				res.getString("role")
		);
	}

}
