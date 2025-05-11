package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.notification.BankAccountTask;
import bus.model.notification.TaskAction;
import bus.model.notification.TaskStatus;
import data.DbConnection;

public class AccountTaskDAO {

	public AccountTaskDAO() {
	}

	public int add(BankAccountTask item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateAccountTask(?,?)}");

			stmt.setInt(1, item.getAccount().getID());
			stmt.setString(2, item.getAction().name().toLowerCase());
			

			return stmt.executeUpdate();
		}
	}

	public int update(BankAccountTask item) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateAccountTask(?,?)}");

			stmt.setInt(1, item.getID());
			stmt.setString(2, item.getStatus().name());

			return stmt.executeUpdate();
		}
	}

	
	public BankAccountTask getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{? = call ReadAccountTaskByID(?)}");
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
	public List<BankAccountTask> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllAccountTasks}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var tasks = new ArrayList<BankAccountTask>();
			while (res.next()) {
				tasks.add( generateFrom(res) );
			}
			return tasks;
		}
	}

	public List<BankAccountTask> getAllPending() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllPendingAccountTasks}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			
			var tasks = new ArrayList<BankAccountTask>();
			while (res.next()) {
				tasks.add(generateFrom(res));
			}
			return tasks;
		}
	}

	private BankAccountTask generateFrom(ResultSet res) throws SQLException {
				var task = new BankAccountTask();
				task.setID(res.getInt("taskID"));
				task.setAccountID(res.getInt("bank_accountID"));
				task.setStatus(TaskStatus.valueOf(res.getString("status")));
				task.setAction(TaskAction.valueOf(res.getString("action_type").toUpperCase()));
				return task;
	}


}
