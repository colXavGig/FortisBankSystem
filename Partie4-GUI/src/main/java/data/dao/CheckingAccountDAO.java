package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import bus.model.account.CheckingAccount;
import data.DbConnection;

public class CheckingAccountDAO  {

	public CheckingAccountDAO() {}

	public int add(CheckingAccount item, int userID) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateCheckingAccount(?,?)}");

			stmt.setInt(1, userID);
			stmt.setDouble(2, item.getFraisTransactionSupplementaire());

			return stmt.executeUpdate();
		}
	}

	public int update(CheckingAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateCheckingAccount(?,?)}");

			stmt.setInt(1, item.getID());
			stmt.setDouble(2, item.getBalance());

			return stmt.executeUpdate();
		}
	}

	public int delete(CheckingAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteCheckingAccount(?)}");

			stmt.setInt(1, item.getID());

			return stmt.executeUpdate();
		}
	}

	public CheckingAccount getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadCheckingAccountByID(?)}");
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

	public List<CheckingAccount> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllCheckingAccounts()}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var accounts = new ArrayList<CheckingAccount>();
			while (res.next()) {
				accounts.add(generateFrom(res));
			}
			return accounts;
		}
	}

	private CheckingAccount generateFrom(ResultSet res) throws SQLException {
		CheckingAccount account = new CheckingAccount();
		account.setID(res.getInt("checking_accountID"));
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
