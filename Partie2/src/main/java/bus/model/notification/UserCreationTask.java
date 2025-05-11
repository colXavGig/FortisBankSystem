package bus.model.notification;

import bus.model.user.User;
import utils.UserCollection;

public class UserCreationTask extends ClientTask {

	/**
	 *
	 */
	private static final long serialVersionUID = -7706877386079003238L;

	public UserCreationTask(User newUser) {
		super(newUser, TaskAction.CREATION);
	}

	@Override
	public void approve() {
		UserCollection.getInstance().add(getUser());
		complete();
	}

	@Override
	public String getMessage() {
		return "Bonjour, vous avez une nouvelle demande de création de compte à approuver."
				+ this.getUser().getInfos();
	}
}
