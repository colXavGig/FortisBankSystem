package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import bus.Currency;
import bus.model.account.CurrencyAccount;
import data.DbConnection;

public class CurrencyAccountDAO {

	public CurrencyAccountDAO() {
		// TODO Auto-generated constructor stub
	}

	public int add(CurrencyAccount item, int userID) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateCurrencyAccount(?,?,?)}");
			
			stmt.setInt(1, userID);
			stmt.setString(2, item.getDevise().name());
			stmt.setDouble(3, item.getExchangeRate());
			
			return stmt.executeUpdate();
		}
	}

	public int update(CurrencyAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateCurrencyAccount(?,?)}");

			stmt.setInt(1, item.getID());
			stmt.setDouble(2, item.getBalance());
			
			return stmt.executeUpdate();
		}
	}

	public int delete(CurrencyAccount item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteCurrencyAccount(?)}");

			stmt.setInt(1, item.getID());
			
			return stmt.executeUpdate();
		}
	}

	public CurrencyAccount getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadCurrencyAccountByID(?)}");
			stmt.registerOutParameter(1, Types.REF_CURSOR);
			stmt.setInt(2, id);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			if (res.next()) {
				return generateFrom(res);
			}
			return null;
		}
	}

	public List<CurrencyAccount> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{? = call ReadAllCurrencyAccounts}");
			stmt.registerOutParameter(1, Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var list = new ArrayList<CurrencyAccount>();
			while (res.next()) {
				list.add( generateFrom(res) );
			}
			
			return list;
		}
	}


	private CurrencyAccount generateFrom(ResultSet res) throws SQLException {
		var acc = new CurrencyAccount(Currency.valueOf(res.getString("currency")));
		acc.setID(res.getInt("currency_accountID"));
		acc.setUserID(res.getInt("userID"));
		acc.setBalance(res.getDouble("balance"));
		acc.setCreationDate(LocalDateTime
				.ofInstant(
						res.getDate("creation_date").toInstant(), 
						ZoneId.systemDefault()
						)
				);
		acc.setExchangeRate(res.getDouble("exchange_rate"));
		
		
		return acc;
	}
}
