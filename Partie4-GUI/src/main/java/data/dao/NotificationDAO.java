package data.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bus.model.account.BankAccount;
import bus.model.notification.ClientNotification;
import bus.model.user.BankClient;
import data.DbConnection;

public class NotificationDAO {

	public NotificationDAO() {
		// TODO Auto-generated constructor stub
	}

	public int add(ClientNotification notif) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call CreateNotification(?,?,?)");
			
			stmt.setInt(1, notif.getAccount().getID());
			stmt.setInt(2, notif.getClient().getID());
			stmt.setString(3, notif.getMessage());
			
			return stmt.executeUpdate();
		}
	}

	public int updateAsRead(ClientNotification notif) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call SetNotificationToRead(?)");
			
			stmt.setInt(1, notif.getID());
			
			return stmt.executeUpdate();
		}
	}

	public int archive(ClientNotification notif) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{call ArchiveNotification(?)");
			
			stmt.setInt(1, notif.getID());
			
			return stmt.executeUpdate();
		}
	}

	public List<ClientNotification> getAllUnread(BankClient client) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadAllUnreadNotificationsForClient(?)}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.setInt(2, client.getID());
			stmt.execute();
			var res = stmt.getObject(1, java.sql.ResultSet.class);
			var notifs = new ArrayList<ClientNotification>();
			while (res.next()) {
				var id = res.getInt(1);
				var accountId = res.getInt(2);
				BankAccount acc = null;
				for (var a : client.getBankAccounts()) {
					if (a.getID() == accountId) {
						acc = a;
						break;
					}
				}
				
				//unused var status = res.getString(4);
				var tmp = new ClientNotification(client, acc, res.getString(5));
				tmp.setID(id);
				
				notifs.add(tmp);
			}
			
			return notifs;
		}
	}

}
