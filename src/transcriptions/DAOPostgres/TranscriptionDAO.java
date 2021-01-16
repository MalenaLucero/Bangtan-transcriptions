package transcriptions.DAOPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import transcriptions.model.Transcription;

public class TranscriptionDAO {
	public static int insert(Connection connection, Transcription transcription) throws SQLException, ParseException {
		String query = "INSERT INTO public.transcription "
				+ "(title_korean, title_english, text_korean, text_english, type, link, date) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, transcription.getTitleKorean());
		statement.setString(2, transcription.getTitleEnglish());
		statement.setString(3, generateStringFromMap(transcription.getTextKorean()));
		statement.setString(4, generateStringFromMap(transcription.getTextEnglish()));
		statement.setString(5, transcription.getType());
		statement.setString(6, transcription.getLink());
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transcription.getDate());
		statement.setDate(7, new java.sql.Date(date.getTime()));
		return statement.executeUpdate();
	}
	
	public static Transcription findById(Connection connection, int id) throws SQLException {
		String query = "SELECT * FROM public.transcription WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateTranscription(res) : null;
	}
	
	public static Transcription findByTitle(Connection connection, String title) throws SQLException {
		String query = "SELECT * FROM public.transcription WHERE title_english = ? OR title_korean = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, title);
		statement.setString(2, title);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateTranscription(res) : null;
	}

	public static int update(Connection connection, Transcription transcription) throws SQLException, ParseException {
		String editString = "UPDATE public.transcription "
				+ "SET title_korean = ?, title_english = ?, text_korean = ?, text_english = ?, "
				+ "type = ?, link = ?, date = ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(editString);
		statement.setString(1, transcription.getTitleKorean());
		statement.setString(2, transcription.getTitleEnglish());
		statement.setString(3, generateStringFromMap(transcription.getTextKorean()));
		statement.setString(4, generateStringFromMap(transcription.getTextEnglish()));
		statement.setString(5, transcription.getType());
		statement.setString(6, transcription.getLink());
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transcription.getDate());
		statement.setDate(7, new java.sql.Date(date.getTime()));
		statement.setInt(8, transcription.getId());
		return statement.executeUpdate();
	}
	
	public static int delete(Connection connection, int id) throws SQLException {
		String query = "DELETE FROM public.transcription WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		return statement.executeUpdate();
	}
	
	public static List<Transcription> findByWord(Connection connection, String language, String word) throws SQLException{
		String query = language == "Korean"
				? "SELECT * FROM public.transcription WHERE text_korean LIKE ?"
				: "SELECT * FROM public.transcription WHERE text_english LIKE ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, "%" + word + "%");
		ResultSet res = statement.executeQuery();
		return generateTranscriptionList(res);
	}
	
	public static List<Transcription> findByWordAndType(Connection connection, String language, String word, String type) throws SQLException{
		String query = language == "Korean"
				? "SELECT * FROM public.transcription WHERE text_korean LIKE ? AND type = ?"
				: "SELECT * FROM public.transcription WHERE text_english LIKE ? AND type = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, "%" + word + "%");
		statement.setString(2, type);
		ResultSet res = statement.executeQuery();
		return generateTranscriptionList(res);
	}
	
	private static List<Transcription> generateTranscriptionList(ResultSet res) throws SQLException{
		List<Transcription> list = new ArrayList<Transcription>();
		while(res.next()) {
			Transcription transcription = generateTranscription(res);
			list.add(transcription);
		}
		return list;
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
		boolean isKey = true;
		String key = null;
		String value = null;
		for (int i = 0; i < textList.length; i++) {
			if(isKey) {
				key = textList[i];
				isKey = false;
			} else {
				value = textList[i];
				textMap.put(key, value);
				isKey = true;
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
