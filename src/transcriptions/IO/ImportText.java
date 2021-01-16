package transcriptions.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import transcriptions.model.Text;

public class ImportText {
	final static String DATA = "text_data.txt";
	final static String TEXT_KOREAN = "text_korean.txt";
	final static String TEXT_ENGLISH = "text_english.txt";
	final static String TEXT_JAPANESE = "text_japanese.txt";
	
	public static Text importFromFile() throws IOException {
		Map<String, String> mapData = Common.importStringData(DATA);
		List<String> textKorean = importText(TEXT_KOREAN);
		List<String> textEnglish = importText(TEXT_ENGLISH);
		List<String> textJapanese = importText(TEXT_JAPANESE);
		if(textKorean.size() == textEnglish.size() && textEnglish.size() == textJapanese.size()) {
			System.out.println("Los textos coinciden");
			return new Text(mapData.get("titleKorean"), mapData.get("titleEnglish"), mapData.get("titleJapanese"),
					textKorean, textEnglish, textJapanese,
					mapData.get("type"), mapData.get("link"), mapData.get("date"));
		} else {
			System.out.println("Los textos no coinciden");
			return null;
		}
	}
	
	private static List<String> importText(String fileName) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String line = bufferedReader.readLine();
		List<String> list = new LinkedList<String>();
		while (line != null) {
			if(line.length() > 0) list.add(line);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return list;
	}
}
