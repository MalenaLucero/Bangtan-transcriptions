package transcriptions.IO;

import java.io.IOException;
import java.util.Map;

import transcriptions.model.Section;

public class ImportSection {
	private final static String DATA = "section.txt";
	
	public static Section importFromFile() throws IOException {
		Map<String, String> mapData = Common.importStringData(DATA);
		return new Section(mapData.get("start"), mapData.get("finish"),
				mapData.get("title"), Integer.parseInt(mapData.get("id_transcription")));
	}
}
