package transcriptions.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import transcriptions.DAOPostgres.TranscriptionDAO;
import transcriptions.model.Transcription;

public class TranscriptionSearchController {
	public static Map<Transcription, List<String>> findByWordInKorean(Connection connection, String word) throws SQLException {
		System.out.println("Searching for " + word);
		List<Transcription> list = TranscriptionDAO.findByWord(connection, "Korean", word);
		return generateKeyMapFromList(list, word);
	}
	
	public static void findByWordInEnglish(Connection connection, String word) throws SQLException {
		List<Transcription> list = TranscriptionDAO.findByWord(connection, "English", word);
		System.out.println(list);
	}
	
	public static Map<Transcription, List<String>> findByWordAndTypeInKorean(Connection connection, String word, String type) throws SQLException {
		System.out.println("Searching for " + word);
		List<Transcription> list = TranscriptionDAO.findByWordAndType(connection, "Korean", word, type);
		return generateKeyMapFromList(list, word);
	}
	
	private static Map<Transcription, List<String>> generateKeyMapFromList(List<Transcription> list, String word) {
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
}
