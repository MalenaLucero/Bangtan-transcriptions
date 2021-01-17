package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import transcriptions.DAOPostgres.SectionDAO;
import transcriptions.DAOPostgres.TranscriptionDAO;
import transcriptions.IO.ExportSection;
import transcriptions.IO.ImportSection;
import transcriptions.model.Section;
import transcriptions.model.Transcription;
import transcriptions.util.PrintUtil;

public class SectionController {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		Section section = ImportSection.importFromFile();
		int res = SectionDAO.insert(connection, section);
		PrintUtil.actionMessage(res);
	}
	
	public static void findByID(Connection connection, int id) throws SQLException {
		Section section = SectionDAO.findById(connection, id);
		System.out.println(section);
	}
	
	public static void export(Connection connection, int id) throws SQLException, IOException {
		Section section = SectionDAO.findById(connection, id);
		Transcription transcription = TranscriptionDAO.findById(connection, section.getIdTranscription());
		section.setTextKorean(generateSectionText(section.getStart(), section.getFinish(), transcription.getTextKorean()));
		section.setTextEnglish(generateSectionText(section.getStart(), section.getFinish(), transcription.getTextEnglish()));
		ExportSection.generateFile(section, transcription);
	}

	private static Map<String, String> generateSectionText(String start, String finish, Map<String, String> fullText) {
		Map<String, String> sectionText = new LinkedHashMap<String, String>();
		boolean isInSection = false;
		for(String key: fullText.keySet()) {
			if(key.equals(start)) {
				isInSection = true;
			} else if(key.equals(finish)) {
				isInSection = false;
			}
			if(isInSection) {
				sectionText.put(key, fullText.get(key));
			}
		}
		return sectionText;
	}
}
