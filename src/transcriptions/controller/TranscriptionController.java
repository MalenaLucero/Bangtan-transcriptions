package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import transcriptions.DAO.TranscriptionDAO;
import transcriptions.IO.ImportTranscription;
import transcriptions.model.Transcription;

public class TranscriptionController {
	public static void insert(Connection connection) throws IOException, SQLException {
		Map<String, String> mapData = ImportTranscription.importStringData("data.txt");
		if(TranscriptionDAO.findByTitle(connection, mapData.get("titleEnglish")) == null) {
			Map<String, String> textKorean = ImportTranscription.importText("text_korean.txt");
			Map<String, String> textEnglish = ImportTranscription.importText("text_english.txt");
			Transcription transcription = new Transcription(mapData.get("titleKorean"), mapData.get("titleEnglish"),
					textKorean, textEnglish, mapData.get("type"), mapData.get("link"), mapData.get("date"));
			int res = TranscriptionDAO.insert(connection, transcription);
			System.out.println(res);
		} else {
			System.out.println("The text already exists");
		}
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		Transcription transcription = TranscriptionDAO.findById(connection, id);
		System.out.println(transcription);
	}
	
	public static void findByTitle(Connection connection, String title) throws SQLException {
		Transcription transcription = TranscriptionDAO.findByTitle(connection, title);
		System.out.println(transcription);
	}
	
	public static void update(Connection connection, Transcription transcription) throws SQLException {
		int res = TranscriptionDAO.update(connection, transcription);
		System.out.println(res);
	}
	
	public static void generateTranscription() throws IOException {
		Transcription transcription = new Transcription();
		Map<String, String> textKorean = ImportTranscription.importText("text_korean.txt");
		Map<String, String> textEnglish = ImportTranscription.importText("text_english.txt");
		transcription.setTextKorean(textKorean);
		transcription.setTextEnglish(textEnglish);
	}
}
