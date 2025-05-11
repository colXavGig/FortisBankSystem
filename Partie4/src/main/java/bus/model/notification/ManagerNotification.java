package bus.model.notification;

import java.io.Serializable;

public class ManagerNotification implements INotification, Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 6229550251974921316L;
	private Integer ID;
	public String nomGestionnaire;
	Task tache;

	/**
	 * Constructeur de la classe ManagerNotification
	 * @param nomGestionnaire le nom du gestionnaire
	 */

	public ManagerNotification(String nomGestionnaire) {
		this.nomGestionnaire=nomGestionnaire;
	}

	public String getMessage() {
		return " Bonjour " + nomGestionnaire + ", vous avez une vouvelle tache à faire.";
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public boolean hasID() {
		return ID != null;
	}
}
