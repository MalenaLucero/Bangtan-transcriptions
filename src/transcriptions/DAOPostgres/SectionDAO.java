package transcriptions.DAOPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import transcriptions.model.Section;

public class SectionDAO {
	public static int insert(Connection connection, Section section) throws SQLException, ParseException {
		String query = "INSERT INTO public.section "
				+ "(start, finish, title, id_transcription) "
				+ "values (?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		setStatement(statement, section);
		return statement.executeUpdate();
	}
	
	public static Section findById(Connection connection, int id) throws SQLException {
		String query = "SELECT * from public.section WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateSection(res) : null;
	}

	private static void setStatement(PreparedStatement statement, Section section) throws SQLException, ParseException {
		statement.setString(1, section.getStart());
		statement.setString(2, section.getFinish());
		statement.setString(3, section.getTitle());
		statement.setInt(4, section.getIdTranscription());
	}
	
	private static Section generateSection(ResultSet res) throws SQLException {
		return new Section(res.getInt("id"), res.getString("start"), res.getString("finish"),
				res.getString("title"), res.getInt("id_transcription"));
	}
}
