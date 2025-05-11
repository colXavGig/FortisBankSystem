package data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DbConnection;
import dto.UserDTO;

public class UserDAO {
	public UserDAO() {}
	
	public UserDTO getByEmail(String email) throws SQLException {
		try (var conn = DbConnection.getConnection()) {
			var stmt = conn.prepareCall("{?=call ReadUserByEmail(?)}");
			stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
			stmt.setString(2, email);
			stmt.execute();
			var res = stmt.getObject(1, ResultSet.class);
			if (res.next()) {
				return generateFrom(res);
			}
			return null;
		}
	}

	private UserDTO generateFrom(ResultSet res) throws SQLException {
		return new UserDTO(
				res.getInt("ID"),
				res.getString("firstname"),
				res.getString("lastname"),
				res.getString("nip"),
				res.getString("address"),
				res.getString("telephone"),
				res.getString("email")
		);
	}

}
