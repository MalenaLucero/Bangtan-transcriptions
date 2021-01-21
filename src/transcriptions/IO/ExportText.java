package transcriptions.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import transcriptions.model.Text;

public class ExportText {
	public static void generateFile(Text text) throws IOException {
		String fileName = text.getTitleJapanese() + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		Common.appendLine(writer, text.getTitleJapanese());
		Common.appendLine(writer, text.getLink());
		writer.newLine();
		for(String line: text.getTextJapanese()) {
			Common.appendLine(writer, line);
			writer.newLine();
		}
		writer.close();
	    System.out.println("File exported as " + fileName);
	}
}
