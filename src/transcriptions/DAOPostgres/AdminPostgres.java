package transcriptions.DAOPostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminPostgres {
	public static Connection connect() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://localhost/bts_text", "postgres", "postgres");
	}
}
