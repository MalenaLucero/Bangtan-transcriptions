package transcriptions.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import transcriptions.model.Transcription;

public class ExportTranscription {
	public static void generateFile(Transcription transcription, int option) throws IOException{
		String fileName = transcription.getTitleEnglish() + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		printData(writer, transcription);
		switch(option) {
		case 1:
			printSingleText(writer, transcription.getTextKorean());
			break;
		case 2:
			printSingleText(writer, transcription.getTextEnglish());
			break;
		case 3:
			printBothTexts(writer, transcription.getTextKorean(), transcription.getTextEnglish());
			break;
		}
	    writer.close();
	    System.out.println("File exported as " + fileName);
	}

	private static void printSingleText(BufferedWriter writer, Map<String, String> text) throws IOException {
		for(String key: text.keySet()) {
			Common.appendLine(writer, key);
			Common.appendLine(writer, text.get(key));
		}
	}
	
	private static void printBothTexts(BufferedWriter writer, Map<String, String> textKorean, Map<String, String> textEnglish) throws IOException {
		for(String key: textKorean.keySet()) {
			Common.appendLine(writer, key);
			Common.appendLine(writer, textKorean.get(key));
			Common.appendLine(writer, textEnglish.get(key));
		}
	}

	private static void printData(BufferedWriter writer, Transcription transcription) throws IOException {
		Common.appendLine(writer, transcription.getTitleKorean());
		Common.appendLine(writer, transcription.getTitleEnglish());
		Common.appendLine(writer, transcription.getDate());
		Common.appendLine(writer, transcription.getLink());
		writer.newLine();
	}
}
