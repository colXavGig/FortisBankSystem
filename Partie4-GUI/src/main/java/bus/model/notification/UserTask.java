package bus.model.notification;

import bus.model.user.User;
import bus.model.user.UserType;

public class UserTask extends Task {

	/**
	 *
	 */
	private static final long serialVersionUID = 8895906542789322627L;

	private User user;
	private Integer userID;
	private TaskAction type;
	private UserType userType;
	private String message;
	
	public UserTask(User newUser, TaskAction type, String message) {
		super();
		setUser(newUser);
		setType(type);
		setMessage(message);
	}
	
	public UserTask() {
		// TODO Auto-generated constructor stub
	}

	public TaskAction getType() {
		return type;
	}
	public void setType(TaskAction type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getUserID() {
		if (userID != null) {
			return user.getID();
		}
		return userID;
	}

	public void setUserID(Integer userID) {
		if (userID != null) {
			this.userID = userID;
		} else {
			this.user.setID(userID);
		}
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}


