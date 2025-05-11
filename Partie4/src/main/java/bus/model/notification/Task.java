package bus.model.notification;

import java.io.Serializable;
import java.sql.SQLException;

import bus.model.Identifiable;
import utils.TaskCollection;

public abstract class Task 
implements Serializable, INotification, Identifiable<Integer> {

	private static final long serialVersionUID = -8132504314381310721L;
	private TaskStatus status;
	private Integer ID;

	public Task() {
		this.setIsDone(false);
	}


	protected void complete() throws SQLException {
		TaskCollection.getInstance()
			.update(this);
	}

	public void approve() {
		this.setStatus(TaskStatus.accepted);
	}
	
	public void refuse() {
		this.setStatus(TaskStatus.rejected);
	}


	public Boolean getIsDone() {
		return status != TaskStatus.pending 
			&& status != TaskStatus.in_progress;
	}


	private void setIsDone(Boolean isDone) {
		throw new UnsupportedOperationException("Not yet implemented");
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


	public TaskStatus getStatus() {
		return status;
	}


	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
