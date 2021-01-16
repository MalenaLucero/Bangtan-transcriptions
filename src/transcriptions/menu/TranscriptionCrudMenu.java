package transcriptions.menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import transcriptions.IO.ImportTranscription;
import transcriptions.controller.TranscriptionController;
import transcriptions.model.Transcription;

public class TranscriptionCrudMenu {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		System.out.println("Insert");
		TranscriptionController.insert(connection);
	}
	
	public static void update(Connection connection, Scanner sc) throws IOException, SQLException, ParseException {
		System.out.println("Modify");
		System.out.println("ID:");
		int idUpdate = sc.nextInt();
		Transcription transcription = ImportTranscription.importFromFile();
		transcription.setId(idUpdate);
		TranscriptionController.update(connection, transcription);
	}
	
	public static void delete(Connection connection, Scanner sc) throws SQLException {
		System.out.println("Delete");
		System.out.println("ID:");
		int id = sc.nextInt();
		TranscriptionController.delete(connection, id);
	}
	
	public static void findById(Connection connection, Scanner sc) throws SQLException {
		System.out.println("Find by ID");
		System.out.println("ID:");
		int id = sc.nextInt();
		TranscriptionController.findById(connection, id);
	}
}
