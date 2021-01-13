package transcriptions.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import transcriptions.model.Transcription;

public class TranscriptionDAO {
	public static int insert(Connection connection, Transcription transcription) throws SQLException {
		String query = "INSERT INTO transcription "
				+ "(title_korean, title_english, text_korean, text_english, type, link, date) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, transcription.getTitleKorean());
		statement.setString(2, transcription.getTitleEnglish());
		statement.setString(3, generateStringFromMap(transcription.getTextKorean()));
		statement.setString(4, generateStringFromMap(transcription.getTextEnglish()));
		statement.setString(5, transcription.getType());
		statement.setString(6, transcription.getLink());
		statement.setString(7, transcription.getDate());
		return statement.executeUpdate();
	}
	
	public static Transcription findById(Connection connection, int id) throws SQLException {
		String query = "SELECT * FROM transcription WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateTranscription(res) : null;
	}
	
	public static Transcription findByTitle(Connection connection, String title) throws SQLException {
		String query = "SELECT * FROM transcription WHERE title_english = ? OR title_korean = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, title);
		statement.setString(2, title);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateTranscription(res) : null;
	}

	public static int update(Connection connection, Transcription transcription) throws SQLException {
		String editString = "UPDATE transcription "
				+ "SET title_korean = ?, title_english = ?, text_korean = ?, text_english = ?, "
				+ "type = ?, link = ?, date = ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(editString);
		statement.setString(1, transcription.getTitleKorean());
		statement.setString(2, transcription.getTitleEnglish());
		statement.setString(3, generateStringFromMap(transcription.getTextKorean()));
		statement.setString(4, generateStringFromMap(transcription.getTextEnglish()));
		statement.setString(5, transcription.getType());
		statement.setString(6, transcription.getLink());
		statement.setString(7, transcription.getDate());
		statement.setInt(8, transcription.getId());
		return statement.executeUpdate();
	}
	
	public static int delete(Connection connection, int id) throws SQLException {
		String query = "DELETE FROM transcription WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		return statement.executeUpdate();
	}
	
	private static Transcription generateTranscription(ResultSet res) throws SQLException {
		Map<String, String> textKorean = generateTextMap(res.getString("text_korean"));
		Map<String, String> textEnglish = generateTextMap(res.getString("text_english"));
		return new Transcription(res.getInt("id"), res.getString("title_korean"),
				res.getString("title_english"), textKorean, textEnglish,
				res.getString("type"), res.getString("link"), res.getString("date"));
	}
	
	private static Map<String, String> generateTextMap(String text) {
		String[] textList = text.split("\\r\\n");
		Map<String, String> textMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < textList.length; i++) {
			String key = null;
			String value = "";
			if(key == null && textList[i].contains("-->")) {
				key = textList[i];
				int index = i + 1;
				while(index < textList.length && !textList[index].contains("-->")) {
					value = value.length() > 0 ? value + " " + textList[index] : value + textList[index];
					index++;
				}
				textMap.put(key, value);
				key = null;
				value = "";
			}
		}
		return textMap;
	}
	
	private static String generateStringFromMap(Map<String, String> textMap) {
		String text = "";
		for(String key: textMap.keySet()) {
			text += key + "\r\n";
			text += textMap.get(key) + "\r\n";
		}
		return text;
	}
}
