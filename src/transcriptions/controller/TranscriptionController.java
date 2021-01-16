package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import transcriptions.DAOPostgres.TranscriptionDAO;
import transcriptions.IO.ExportTranscription;
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
		showTranscription(transcription);
	}
	
	public static void findByTitle(Connection connection, String title) throws SQLException {
		Transcription transcription = TranscriptionDAO.findByTitle(connection, title);
		showTranscription(transcription);
	}
	
	public static void update(Connection connection, Transcription transcription) throws SQLException, ParseException {
		int res = TranscriptionDAO.update(connection, transcription);
		PrintUtil.actionMessage(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		int res = TranscriptionDAO.delete(connection, id);
		PrintUtil.actionMessage(res);
	}
	
	public static void exportTranscription(Connection connection, int id, int option) throws SQLException, IOException {
		Transcription transcription = TranscriptionDAO.findById(connection, id);
		if(transcription != null) {
			ExportTranscription.generateFile(transcription, option);
		} else {
			System.out.println("Transcription not found");
		}
	}
	
	public static void findAll(Connection connection) throws SQLException {
		List<Transcription> list = TranscriptionDAO.findAll(connection);
		showTranscriptionList(list);
	}
	
	private static void showTranscriptionList(List<Transcription> list) {
		if(list.size() == 0) {
			System.out.println("No transcriptions found");
		} else {
			for(Transcription transcription: list) {
				System.out.println(transcription);
			}
		}
	}
	
	private static void showTranscription(Transcription transcription) {
		if(transcription == null) {
			System.out.println("Transcription not found");
		} else {
			System.out.println(transcription);
		}
	}
}
