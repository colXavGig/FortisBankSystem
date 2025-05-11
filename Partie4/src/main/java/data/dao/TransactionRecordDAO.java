package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.account.BankAccount;
import bus.model.transaction.TransactionRecord;
import data.DbConnection;

public class TransactionRecordDAO {

	public TransactionRecordDAO() {
		// TODO Auto-generated constructor stub
	}

	public int add(TransactionRecord rec, int bankAccountID) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateTransactionRecord(?,?,?,?)}");

			stmt.setInt(1, bankAccountID);
			stmt.setString(2, rec.type());
			stmt.setDouble(3, rec.operation());
			stmt.setDouble(4, rec.newBalance());
			
			return stmt.executeUpdate();
		}
	}

	public List<TransactionRecord> getFor(BankAccount account) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadTransactionRecordFor(?)}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.setInt(2, account.getID());
			stmt.execute();
			var res = stmt.getObject(1, java.sql.ResultSet.class);
			var records = new ArrayList<TransactionRecord>();
			while (res.next()) {
			
				records.add(generateRecordFrom(res));
			}
			
			return records;
		}
	}

	public List<TransactionRecord> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllTransactionRecords}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, java.sql.ResultSet.class);
			var records = new ArrayList<TransactionRecord>();
			while (res.next()) {
			
				records.add(generateRecordFrom(res));
			}
			
			return records;
		}
	}

	private TransactionRecord generateRecordFrom(ResultSet res) throws SQLException {
		return new TransactionRecord(
				res.getInt("historicID"),
				res.getString("type"), 
				res.getDouble("balance"), 
				res.getDouble("operation"), 
				res.getDate("date").toLocalDate()
				);
	}

}
