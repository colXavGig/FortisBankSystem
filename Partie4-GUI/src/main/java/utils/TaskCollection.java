package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.notification.BankAccountTask;
import bus.model.notification.Task;
import bus.model.notification.UserTask;
import data.dao.AccountTaskDAO;
import data.dao.UserTaskDAO;

public class TaskCollection {

	private static TaskCollection instance = null;
	private static final UserTaskDAO utd = new UserTaskDAO();
	private static final AccountTaskDAO atd = new AccountTaskDAO();
		private TaskCollection() {
		super();
	}

	public static TaskCollection getInstance() {
		if (instance == null) {
			instance = new TaskCollection();
		}
		return instance;
	}

	public List<Task> getTaskToDo() throws SQLException {
		var tasks = new ArrayList<Task>();
		
		tasks.addAll(utd.getAllPending());
		tasks.addAll(atd.getAllPending());
		
		return tasks;
		
	}

	public void add(Task item) throws SQLException {
		if (item instanceof UserTask) {
			utd.add((UserTask) item);
		} else if (item instanceof BankAccountTask) {
			atd.add((BankAccountTask) item);
		}
		
	}


	public void update(Task item) throws SQLException {
		if (item instanceof UserTask) {
			utd.update((UserTask) item);
		} else if (item instanceof BankAccountTask) {
			atd.update((BankAccountTask) item);
		}
	}

	public List<Task> getData() throws SQLException {
		var tasks = new ArrayList<Task>();
		
		tasks.addAll(utd.getAll());
		tasks.addAll(atd.getAll());
		
		return tasks;
	}

	public boolean isEmpty() throws SQLException {
		return getData().isEmpty();
	}

	public boolean contains(Task item) throws SQLException {
		return getData().contains(item);
	}

	public Task get(int id) throws SQLException {
		return getData().stream().filter(t -> t.getID() == id).findFirst().orElse(null);
	}


}
