package bus.model.notification;

import bus.model.user.User;

public abstract class ClientTask extends Task {

	/**
	 *
	 */
	private static final long serialVersionUID = 8895906542789322627L;

	private User user;
	private TaskAction type;
	public ClientTask(User newUser, TaskAction type) {
		super();
		setUser(newUser);
		setType(type);
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

}


