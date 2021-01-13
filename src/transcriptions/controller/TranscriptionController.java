package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import transcriptions.DAO.TranscriptionDAO;
import transcriptions.IO.ImportTranscription;
import transcriptions.model.Transcription;
import transcriptions.util.PrintUtil;

public class TranscriptionController {
	public static void insert(Connection connection) throws IOException, SQLException {
		Map<String, String> mapData = ImportTranscription.importStringData("data.txt");
		if(TranscriptionDAO.findByTitle(connection, mapData.get("titleEnglish")) == null) {
			Map<String, String> textKorean = ImportTranscription.importText("text_korean.txt");
			Map<String, String> textEnglish = ImportTranscription.importText("text_english.txt");
			Transcription transcription = new Transcription(mapData.get("titleKorean"), mapData.get("titleEnglish"),
					textKorean, textEnglish, mapData.get("type"), mapData.get("link"), mapData.get("date"));
			int res = TranscriptionDAO.insert(connection, transcription);
			PrintUtil.actionMessage(res);
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
	
	public static void delete(Connection connection, int id) throws SQLException {
		int res = TranscriptionDAO.delete(connection, id);
		PrintUtil.actionMessage(res);
	}
	
	public static Map<Transcription, List<String>> findByWordInKorean(Connection connection, String word) throws SQLException {
		System.out.println("Searching for the word " + word);
		List<Transcription> list = TranscriptionDAO.findByWord(connection, "Korean", word);
		Map<Transcription, List<String>> keyMap = new HashMap<Transcription, List<String>>();
		for(Transcription transcription: list) {
			List<String> keyList = new LinkedList<String>();
			for(String key: transcription.getTextKorean().keySet()) {
				if(transcription.getTextKorean().get(key).contains(word)) {
					keyList.add(key);
				}
			}
			keyMap.put(transcription, keyList);
		}
		return keyMap;
	}
	
	public static void findByWordInEnglish(Connection connection, String word) throws SQLException {
		List<Transcription> list = TranscriptionDAO.findByWord(connection, "English", word);
		System.out.println(list);
	}
}
