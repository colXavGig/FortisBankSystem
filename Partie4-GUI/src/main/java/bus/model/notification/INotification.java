package bus.model.notification;

import bus.model.Identifiable;

public interface INotification extends Identifiable<Integer> {
	String getMessage();
}
