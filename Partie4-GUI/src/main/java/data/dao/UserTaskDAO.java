package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.notification.TaskAction;
import bus.model.notification.TaskStatus;
import bus.model.notification.UserTask;
import bus.model.user.UserType;
import data.DbConnection;

public class UserTaskDAO {

	public UserTaskDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public UserTask getById(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadUserTaskByID(?)}");
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
	
	public List<UserTask> getAll() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllUserTasks}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var list = new ArrayList<UserTask>();
			while (res.next()) {
				list.add( generateFrom(res) );
			}
			return list;
		}
	}
	
	public List<UserTask> getAllPending() throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllPendingUserTasks}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			var list = new ArrayList<UserTask>();
			while (res.next()) {
				list.add( generateFrom(res) );
			}
			return list;
		}
	}
	
	private UserTask generateFrom(ResultSet res) throws SQLException {
		var task = new UserTask() ;
		
		task.setID(res.getInt("taskID"));
		task.setUserID(res.getInt("userID"));
		task.setStatus(TaskStatus.valueOf(res.getString("status")));
		task.setType(TaskAction.valueOf(res.getString("action_type").toUpperCase()));
		task.setUserType((res.getString("user_type").equalsIgnoreCase("client")) ? UserType.Client : UserType.Manager);
		
		
		return task;
	}

	public int add(UserTask task) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateUserTask(?,?,?)}");
			
			stmt.setInt(1, task.getUserID());
			stmt.setString(2, task.getType().name().toLowerCase());
			stmt.setString(3, task.getUserType(). name().toLowerCase());
			
			return stmt.executeUpdate();
		}
	}
	
	public int update(UserTask task) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call UpdateUserTask(?,?)}");
			
			stmt.setInt(1, task.getID());
			stmt.setString(2, task.getStatus().name());
			
			return stmt.executeUpdate();
		}
	}
	
	public int delete(int id) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call DeleteUserTask(?)}");
			
			stmt.setInt(1, id);
			
			return stmt.executeUpdate();
		}
	}

}
