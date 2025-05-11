package utils;

import java.util.List;

import bus.model.notification.Task;

public class TaskCollection extends IdentifiableCollection<Task> {

	private static TaskCollection instance = null;
	private TaskCollection() {
		super(new CollectionFileManager<>(Task.class));
	}

	public static TaskCollection getInstance() {
		if (instance == null) {
			instance = new TaskCollection();
		}
		return instance;
	}

	public List<Task> getTaskToDo() {
		return getData().stream()
				.filter(task -> !task.getIsDone())
				.toList();
	}

}
