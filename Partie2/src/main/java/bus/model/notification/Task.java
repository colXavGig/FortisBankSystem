package bus.model.notification;

import java.io.Serializable;

import bus.model.Identifiable;
import utils.TaskCollection;

public abstract class Task 
implements Serializable, INotification, Identifiable<Integer> {

	private static final long serialVersionUID = -8132504314381310721L;
	private Boolean isDone;
	private Integer ID;

	public Task() {
		this.setIsDone(false);
	}


	protected void complete() {
		this.setIsDone(true);
		TaskCollection.getInstance()
			.update(this);
	}

	public abstract void approve();
	public void refuse() {
		complete();
	}


	public Boolean getIsDone() {
		return isDone;
	}


	private void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}
	
	public boolean hasID() {
		return ID != null;
	}
}
