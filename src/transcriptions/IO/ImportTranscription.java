package transcriptions.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import transcriptions.model.Transcription;

public class ImportTranscription {
	final static String DATA = "transcription_data.txt";
	final static String TEXT_KOREAN = "transcription_korean.txt";
	final static String TEXT_ENGLISH = "transcription_english.txt";
	
	public static Transcription importFromFile() throws IOException {
		Map<String, String> mapData = Common.importStringData(DATA);
		Map<String, String> textKorean = deleteTextCommentary(importText(TEXT_KOREAN));
		Map<String, String> textEnglish = deleteTextCommentary(importText(TEXT_ENGLISH));
		if(!areKeysNormalized(textKorean, textEnglish)) {
			System.out.println("Normalizing keys...");
			Set<String> commonKeys = findCommonKeys(textKorean, textEnglish);
			textKorean = normalizeWithCommonKeys(textKorean, commonKeys);
			textEnglish = normalizeWithCommonKeys(textEnglish, commonKeys);
		}
		return new Transcription(mapData.get("titleKorean"), mapData.get("titleEnglish"), textKorean,
				textEnglish, mapData.get("type"), mapData.get("link"), mapData.get("date"));
	}
	
	private static Set<String> findCommonKeys(Map<String, String> textKorean, Map<String, String> textEnglish) {
		Set<String> commonKeys = new HashSet<String>();
		commonKeys.addAll(textKorean.keySet());
		commonKeys.retainAll(textEnglish.keySet());
		return commonKeys;
	}

	private static Map<String, String> normalizeWithCommonKeys(Map<String, String> text, Set<String> commonKeys) {
		Map<String, String> normalizedText = new LinkedHashMap<String, String>();
		String previousKey = null;
		for(String key: text.keySet()) {
			if(commonKeys.contains(key)) {
				normalizedText.put(key, text.get(key));
				previousKey = key;
			} else {
				String value = normalizedText.get(previousKey) + " " + text.get(key);
				normalizedText.put(previousKey, value);
			}
		}
		return normalizedText;
	}
	
	private static boolean areKeysNormalized(Map<String, String> textKorean, Map<String, String> textEnglish) {
		boolean isNormalized = true;
		if(textKorean.keySet().size() == textEnglish.keySet().size()) {
			for(String key: textKorean.keySet()) {
				if(textEnglish.get(key) == null) {
					isNormalized = false;
				} 
			}
		} else {
			isNormalized = false;
		}
		return isNormalized;
	}

	private static Map<String, String> importText(String fileName) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String line = bufferedReader.readLine();
		String key = null;
		String value = "";
		Map<String, String> textMap = new LinkedHashMap<String, String>();
		while (line != null) {
			if(line.contains("-->")) {
				key = line.substring(0, line.indexOf(','));
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
