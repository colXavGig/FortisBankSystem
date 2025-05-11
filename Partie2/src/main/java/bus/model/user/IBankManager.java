package bus.model.user;

import bus.IRapportFinancier;
import bus.model.account.BankAccount;

public interface IBankManager {
	Integer getID();
	void setID(Integer id);

	String getName();
	void setName(String name);

	IBankClient createClient();
	void removeClient(IBankClient client);
	void openAccount(IBankClient client);
	void closeAccount(BankAccount account);

	IRapportFinancier genererRapportFinancier();


	void generateMensualStatement();
	void sendMensualStatement();

}
