package transcriptions.DAOPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import transcriptions.model.Text;

public class TextDAO {
	public static int insert(Connection connection, Text text) throws SQLException, ParseException {
		String query = "INSERT INTO public.text "
				+ "(title_korean, title_english, title_japanese, text_korean, text_english, text_japanese, type, link, date) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		setStatement(statement, text);
		return statement.executeUpdate();
	}
	
	public static Text findById(Connection connection, int id) throws SQLException {
		String query = "SELECT * FROM public.text WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet res = statement.executeQuery();
		return res.next() ? generateText(res) : null;
	}
	
	private static Text generateText(ResultSet res) throws SQLException {
		List<String> textKorean = Arrays.asList(res.getString("text_korean").split("\\r\\n"));
		List<String> textEnglish = Arrays.asList(res.getString("text_english").split("\\r\\n"));;
		List<String> textJapanese = Arrays.asList(res.getString("text_japanese").split("\\r\\n"));;
		return new Text(res.getInt("id"), res.getString("title_korean"),
				res.getString("title_english"), res.getString("title_japanese"),
				textKorean, textEnglish, textJapanese,
				res.getString("type"), res.getString("link"), res.getString("date"));
	}

	public static int update(Connection connection, Text text) throws SQLException, ParseException {
		String editString = "UPDATE public.text"
				+ " SET title_korean = ?, title_english = ?, title_japanese = ?,"
				+ " text_korean = ?, text_english = ?, text_japanese = ?,"
				+ " type = ?, link = ?, date = ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(editString);
		setStatement(statement, text);
		statement.setInt(10, text.getId());
		return statement.executeUpdate();
	}
	
	public static List<Text> findByWord(Connection connection, String word) throws SQLException {
		String query = "SELECT * FROM public.text WHERE text_korean LIKE ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, "%" + word + "%");
		ResultSet res = statement.executeQuery();
		return generateTextList(res);
	}
	
	private static List<Text> generateTextList(ResultSet res) throws SQLException {
		List<Text> list = new ArrayList<Text>();
		while(res.next()) {
			Text text = generateText(res);
			list.add(text);
		}
		return list;
	}

	private static void setStatement(PreparedStatement statement, Text text) throws SQLException, ParseException {
		statement.setString(1, text.getTitleKorean());
		statement.setString(2, text.getTitleEnglish());
		statement.setString(3, text.getTitleJapanese());
		statement.setString(4, fromListToString(text.getTextKorean()));
		statement.setString(5, fromListToString(text.getTextEnglish()));
		statement.setString(6, fromListToString(text.getTextJapanese()));
		statement.setString(7, text.getType());
		statement.setString(8, text.getLink());
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(text.getDate());
		statement.setDate(9, new java.sql.Date(date.getTime()));
	}
	
	private static String fromListToString(List<String> list) {
		String string = "";
		for(String line: list) {
			string += line + "\r\n";
		}
		return string;
	}
}
