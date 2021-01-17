package transcriptions.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import transcriptions.model.Section;
import transcriptions.model.Transcription;

public class ExportSection {
	public static void generateFile(Section section, Transcription transcription) throws IOException{
		String fileName = section.getTitle() + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		Common.appendLine(writer, section.getTitle());
		Common.appendLine(writer, "Section of " + transcription.getTitleEnglish());
		Common.appendLine(writer, transcription.getLink());
		writer.newLine();
		printBothTexts(writer, section.getTextKorean(), section.getTextEnglish());
	    writer.close();
	    System.out.println("File exported as " + fileName);
	}
	
	private static void printBothTexts(BufferedWriter writer, Map<String, String> textKorean, Map<String, String> textEnglish) throws IOException {
		for(String key: textKorean.keySet()) {
			Common.appendLine(writer, key);
			Common.appendLine(writer, textKorean.get(key));
			Common.appendLine(writer, textEnglish.get(key));
		}
	}
}
