package transcriptions.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import transcriptions.model.Transcription;

public class ImportTranscription {
	final static String DATA = "transcription_data.txt";
	final static String TEXT_KOREAN = "transcription_text_korean.txt";
	final static String TEXT_ENGLISH = "transcription_text_english.txt";
	
	public static Transcription importFromFile() throws IOException {
		Map<String, String> mapData = Common.importStringData(DATA);
		Map<String, String> textKorean = deleteTextCommentary(importText(TEXT_KOREAN));
		Map<String, String> textEnglish = deleteTextCommentary(importText(TEXT_ENGLISH));
		return new Transcription(mapData.get("titleKorean"), mapData.get("titleEnglish"), textKorean,
				textEnglish, mapData.get("type"), mapData.get("link"), mapData.get("date"));
	}
	
	private static Map<String, String> importText(String fileName) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String line = bufferedReader.readLine();
		String key = null;
		String value = "";
		Map<String, String> textMap = new LinkedHashMap<String, String>();
		while (line != null) {
			if(line.contains("-->")) {
				key = line;
				value = "";
			} else {
				value = value.length() == 0 ? line : value + " " + line;
			}
			textMap.put(key, value);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return textMap;
	}
	
	private static Map<String, String> deleteTextCommentary(Map<String, String> textMap){
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		for(String key: textMap.keySet()) {
			String value = textMap.get(key);
			if(value.contains("[") && value.contains("]")) {
				while(value.length() > 0 && value.charAt(0) == '[') {
					value = value.substring(value.indexOf(']') + 1);
					if(value.length() > 0) value = value.substring(1);
				}
			}
			if(value.length() > 0) newMap.put(key, value);
		}
		return newMap;
	}
}
