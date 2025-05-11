package data.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import bus.model.account.CreditAccount;
import data.DbConnection;

public class CreditAccountDAO  {

	public CreditAccountDAO() {
		// TODO Auto-generated constructor stub
	}


	public int add(CreditAccount account, int userID) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateCreditAccount(?,?,?)}");
			
			stmt.setInt(1, userID);
			stmt.setDouble(2, account.getCreditLimit());
			stmt.setDouble(3, account.getInterestRate());
			
			return stmt.executeUpdate();
		}
	}

	public int update(CreditAccount account) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateCreditAccount(?,?,?)}");
			
			stmt.setInt(1, account.getID());
			stmt.setDouble(2, account.getBalance());
			stmt.setDate(3, Date.valueOf(account.getCreationDate().toLocalDate()));
			stmt.setDouble(4, account.getCreditLimit());
			stmt.setDouble(5, account.getInterestRate());
			
			return stmt.executeUpdate();
		}
	}

	public int delete(CreditAccount account) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteCreditAccount(?)}");
			
			stmt.setInt(1, account.getID());
			
			return stmt.executeUpdate();
		}
	}

	public CreditAccount getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadCreditAccountByID(?)}");
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

	public List<CreditAccount> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllCreditAccounts}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			
			var accounts = new ArrayList<CreditAccount>();
			while (res.next()) {
				accounts.add( generateFrom(res) );
			}
			return accounts;
		}
	}

	
	private CreditAccount generateFrom(ResultSet res) throws SQLException {
		var acc =  new CreditAccount(
				res.getDouble("credit_limit"), 
				res.getDouble("interest_rate")
				);
		acc.setBalance(res.getDouble("balance"));
		acc.setCreationDate(LocalDateTime
				.ofInstant(
						res.getDate("creation_date").toInstant(), 
						ZoneId.systemDefault()
						)
				);
		acc.setID(res.getInt("credit_accountID"));
		acc.setUserID(res.getInt("userID"));
		return acc;
	}

}
