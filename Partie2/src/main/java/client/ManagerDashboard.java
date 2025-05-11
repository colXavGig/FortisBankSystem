package client;

import java.util.List;

import bus.model.notification.Task;
import bus.model.transaction.TransactionRecord;
import bus.model.user.BankManager;
import client.helper.CLI;
import utils.BankAccountCollection;
import utils.TransactionRecordCollection;

public class ManagerDashboard {

	private BankManager manager;

	public ManagerDashboard(BankManager manager) {
		this.manager = manager;
	}

	public void display() {
		CLI.println("Welcome to the Manager Dashboard!");
		mainMenu();
	}

	private void mainMenu() {
		boolean hasTaskTodo = false;
		while (true) {
			int option = 0;
			try {
				hasTaskTodo = manager.hasTaskTodo();
				if (hasTaskTodo) {
					CLI.println("There is tasks that needs to be completed");
				} else {
					CLI.println("All tasks are completed");
				}
			} catch (Exception e) {
				CLI.println("Could not check if there is task to be done");
			}
			CLI.println("%d. Work on tasks".formatted(++option));
			CLI.println("%d. Check transactions details".formatted(++option));
			CLI.println("%d. Check account balance".formatted(++option));
			CLI.println("%d. Exit".formatted(++option));

			int choice = Integer.parseInt(
					CLI.getString("Enter your choice: ")
					);
			CLI.clear();
			switch (choice) {
				case 1:
					workOnTasks();
					break;
				case 2:
					checkTransactionDetails();
					break;
				case 3:
					checkAllAccountBalance();
				case 4:
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					CLI.promptEnterToClear();
					break;
			}

		}
	}

	private void checkAllAccountBalance() {
		BankAccountCollection accountCollection = null;
		accountCollection  = BankAccountCollection.getInstance();
		CLI.println("Account ID | Balance");
		CLI.println("--------------------");
		for (var account : accountCollection.getData()) {
			CLI.println("%s : %f".formatted(account.getID(), account.getBalance()));
		}

		CLI.promptEnterToClear();
	}

	private void checkTransactionDetails() {
		CLI.println("Checking Transaction details...");
		List<TransactionRecord> transactionRecords = null;
		try {
			transactionRecords = TransactionRecordCollection.getInstance().getData();
		} catch (Exception e) {
			CLI.println("Could not load transactions");
			CLI.promptEnterToClear();
			return;
		}

		int offset = 0;
		final int pageLenght = 10;
		var choice = "p";

		while (choice.equalsIgnoreCase("p") || choice.equalsIgnoreCase("n")) {
			var curr = transactionRecords.subList(offset, offset + pageLenght);
			for (var transact : curr)  {
				CLI.println(transact.toString());
			}
			CLI.println("[N] : Next \t\t\t\t [P] : Previous");
			choice = CLI.getString("");
			if (choice.equals("p")) {
				offset -= (offset >= pageLenght-1) ? pageLenght : offset;
			} else if (choice.equalsIgnoreCase("n")) {
				offset += (offset <= transactionRecords.size() - pageLenght - 1) ?
					pageLenght :
					transactionRecords.size()-1 - pageLenght - offset;
			}
		}
		CLI.clear();
	}

	private void workOnTasks() {
		var choice= -1;
		while (choice != 2) {
			int option = 0;
			CLI.println("%d. Next task".formatted(++option));
			CLI.println("%d. Back".formatted(++option));
			choice = Integer.parseInt(CLI.getString(null));
			Task nextTask = null;
			try {
				if (!manager.hasTaskTodo()) {
					System.out.println("All task  are completed");
					CLI.promptEnterToClear();
					return;
				} else {
					nextTask = manager.getNextTask();
				}
			} catch (Exception e) {
				CLI.println("Could not load tasks");
			}
			switch(choice) {
				case 1:
					completeTask(nextTask);
					break;
				case 2:
					CLI.clear();
					return;
				default:
					CLI.println("Invalid choice.");
			}
		}
	}

	private void completeTask(Task nextTask) {
		CLI.println("Completing task...");
		CLI.println("Task:");
		CLI.println(nextTask.getMessage());
		String choice = "";
		do {
			choice = CLI.getString("Do you approve the change? (y/n) ");
		} while ( !choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));

		if (choice.equalsIgnoreCase("y")) {
			nextTask.approve();
		} else {
			nextTask.refuse();
		}
		CLI.promptEnterToClear();
	}

}
