package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

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
		ExportSection.generateFile(section, transcription);
	}
}
