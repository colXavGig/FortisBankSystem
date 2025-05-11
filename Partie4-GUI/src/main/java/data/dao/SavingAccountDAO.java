package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import bus.model.account.SavingAccount;
import data.DbConnection;

public class SavingAccountDAO  {

	public SavingAccountDAO() {
		// TODO Auto-generated constructor stub
	}

	public int add(SavingAccount item, int userID) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateSavingAccount(?,?)}");
			
			stmt.setInt(1, userID);
			stmt.setDouble(2, item.getTauxInteret());
			
			return stmt.executeUpdate();
		}
	}

	public int update(SavingAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateSavingAccount(?,?)}");

			stmt.setInt(1, item.getID());
			stmt.setDouble(2, item.getBalance());
			
			return stmt.executeUpdate();
		}
	}

	public int delete(SavingAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteSavingAccount(?)}");

			stmt.setInt(1, item.getID());
			
			return stmt.executeUpdate();
		}
	}

	public SavingAccount getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadSavingAccountByID(?)}");
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

	public List<SavingAccount> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllSavingAccounts}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var list = new ArrayList<SavingAccount>();
			while (res.next()) {
				list.add( generateFrom(res) );
			}
			
			return list;
		}
	}

	private SavingAccount generateFrom(ResultSet res) throws SQLException {
		SavingAccount account = null;
			account = new SavingAccount(res.getDouble("interest_rate"));
			account.setID(res.getInt("saving_accountID"));
			account.setUserID(res.getInt("userID"));
			account.setBalance(res.getDouble("balance"));
			account.setCreationDate(LocalDateTime
					.ofInstant(
							Instant.ofEpochMilli(res.getDate("creation_date").getTime()), 
							ZoneId.systemDefault()
							)
					);
		return account;
	}
}
