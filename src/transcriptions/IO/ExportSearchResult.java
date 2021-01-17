package transcriptions.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import transcriptions.model.Transcription;

public class ExportSearchResult {
	public static String generateFile(String[] words) throws IOException{
		String fileName = generateFileName(words);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		appendLine(writer, "RESULTADO DE LA BUSQUEDA");
	    writer.close();
	    return fileName;
	}
	
	private static String generateFileName(String[] words) {
		String fileName = "";
		for (int i = 0; i < words.length; i++) {
			fileName = (i != words.length - 1) ? fileName + words[i] + "_" : fileName + words[i] + ".txt";
		}
		return fileName;
	}
	
	public static void generateWordDetail(Map<Transcription, List<String>> data, String fileName, String word) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.newLine();
		appendLine(writer, "--------------------------------------------");
		writer.newLine();
		appendLine(writer, "PALABRA: " + word);
		for(Transcription transcription: data.keySet()) {
			writer.newLine();
			appendLine(writer, "EPISODE: " + transcription.toString());
			appendLine(writer, transcription.getLink());
			for(String key: data.get(transcription)) {
				writer.newLine();
				appendLine(writer, key);
				appendLine(writer, transcription.getTextKorean().get(key));
				appendLine(writer, transcription.getTextEnglish().get(key));
			}
		}
	    writer.close();
	}
	
	private static void appendLine(BufferedWriter writer, String line) throws IOException {
		writer.append(line);
		writer.newLine();
	}
}
