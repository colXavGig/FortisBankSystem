package bus.service;

import java.io.Serializable;

import bus.BusinessException;
import bus.model.Identifiable;
import utils.FileManager;
import utils.UtilException;

public class IdentityInitiator implements Serializable {
	
	private static final long serialVersionUID = 3885984057533896773L;

	private static IdentityInitiator instance = null;
	
	private Integer lastID;
	private static FileManager<IdentityInitiator> fileManager = new FileManager<IdentityInitiator>(IdentityInitiator.class);;
	
	private IdentityInitiator() {
		lastID = 0;
		
	}
	
	
	private static IdentityInitiator getInstance() {
		try {
			instance = fileManager.deSerialize();
		} catch (Exception e) {
			instance = new IdentityInitiator();
		}
		return instance;
	}
	
	public static void save() throws BusinessException {
		try {
			fileManager.serialize(getInstance());
		} catch (UtilException e) {
			
			throw  new BusinessException("Error saving identity initiator: " + e.getMessage(), e);
			
		}
	}
	
	public static <T extends Identifiable<Integer>> void initID(T identifiable) {
		if (identifiable.hasID()) {
			throw new IllegalArgumentException("ID already set");
		}
		identifiable.setID(++getInstance().lastID);
	}
	

}
