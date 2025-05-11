package bus.model.user;

import java.sql.SQLException;

import bus.BusinessException;
import bus.model.notification.Task;
import utils.TaskCollection;

public class BankManager extends User {

	private static final long serialVersionUID = 465151098840298245L;

	private String role;


	/**
	 * Constructor for BankManager class.
	 * @param id The ID of the bank manager.
	 * @param nom The last name of the bank manager.
	 * @param prenom The first name of the bank manager.
	 * @param nip The NIP (National Identification Number) of the bank manager.
	 * @param adresse The address of the bank manager.
	 * @param email The email address of the bank manager.
	 * @param telephone The telephone number of the bank manager.
	 * @param role The role of the bank manager.
	 */
	public BankManager(
		Integer id, String nom, String prenom, String nip, String adresse, String email, String telephone, String role) {
        super(id, nom, prenom, nip, adresse, email, telephone);
        this.role = role;
    }

	public boolean hasTaskTodo() throws BusinessException {
		try {
			return TaskCollection.getInstance().getTaskToDo()
					.size() > 0;
		} catch (SQLException e) {
			throw new BusinessException("Error while getting tasks", e);
		}
	}

	public Task getNextTask() throws BusinessException {
		try {
			return TaskCollection.getInstance().getTaskToDo()
					.get(0);
		} catch (SQLException e) {
			throw new BusinessException("Error while getting tasks", e);
		}
	}

	/**
	 *
	 * @return the role of the bank manager
	 */
	public String getRole() {
		return role;
	}
	/**
	 *
	 * @param role
	 */
	public void setRole(String role) {
		this.role=role;
	}

	@Override
	public String toString() {
		return "Gestionnaire [id=" + getID() + ", nom=" + getLastname() + ", prenom=" + getFirstname() + ", nip=" + getPIN()
				+ ", adresse=" + getAddress() + ", email=" + getEmail() + ", telephone=" + getTelephoneNumber() + ", role="
				+ role + "]";
	}

	@Override
	public String getInfos() {
		// TODO Auto-generated method stub
		return toString();
	}

}

