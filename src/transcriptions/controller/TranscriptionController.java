package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import transcriptions.DAOPostgres.TranscriptionDAO;
import transcriptions.IO.ImportTranscription;
import transcriptions.model.Transcription;
import transcriptions.util.PrintUtil;

public class TranscriptionController {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		Transcription transcription = ImportTranscription.importFromFile();
		if(TranscriptionDAO.findByTitle(connection, transcription.getTitleEnglish()) == null) {
			int res = TranscriptionDAO.insert(connection, transcription);
			PrintUtil.actionMessage(res);
		} else {
			System.out.println("The text already exists in the database");
		}
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		Transcription transcription = TranscriptionDAO.findById(connection, id);
		System.out.println(transcription);
	}
	
	public static void findByTitle(Connection connection, String title) throws SQLException {
		Transcription transcription = TranscriptionDAO.findByTitle(connection, title);
		System.out.println(transcription);
	}
	
	public static void update(Connection connection, Transcription transcription) throws SQLException, ParseException {
		int res = TranscriptionDAO.update(connection, transcription);
		System.out.println(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		int res = TranscriptionDAO.delete(connection, id);
		PrintUtil.actionMessage(res);
	}
}
