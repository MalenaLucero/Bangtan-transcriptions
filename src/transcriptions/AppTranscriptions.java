package transcriptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import transcriptions.DAOPostgres.AdminPostgres;
import transcriptions.controller.TranscriptionController;
import transcriptions.menu.TranscriptionSearchMenu;
import transcriptions.model.Transcription;

public class AppTranscriptions {
	public static void main(String[] args) {
		try {
			Connection connection = AdminPostgres.connect();
			System.out.println("Sucessfully connected to Postgres database");
			Scanner sc = new Scanner(System.in);
			try {
				System.out.println("1. CRUD, 2. Transcription search");
				int option = sc.nextInt();
				switch(option) {
				case 1:
					crud(connection, sc);
					break;
				case 2:
					transcriptionSearch(connection, sc);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Programa finalizado");
	}

	private static void transcriptionSearch(Connection connection, Scanner sc) throws SQLException, IOException {
		System.out.println("1. Search words in Korean, 2. Search a phrase in Korean");
		int optionSearch = sc.nextInt();
		switch(optionSearch) {
		case 1:
			TranscriptionSearchMenu.searchIsolatedWords(sc, connection);
			break;
		case 2:
			TranscriptionSearchMenu.searchPhrase(sc, connection);
			break;
		}
	}

	private static void crud(Connection connection, Scanner sc) throws IOException, SQLException, ParseException {
		System.out.println("1. Insert, 2. Modify, 3. Delete");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			System.out.println("Insert");
			TranscriptionController.insert(connection);
			break;
		case 2:
			System.out.println("Modify");
			Transcription transcription = new Transcription();
			TranscriptionController.update(connection, transcription);
			break;
		case 3:
			System.out.println("Delete");
			System.out.println("ID:");
			int id = sc.nextInt();
			TranscriptionController.delete(connection, id);
			break;
		}
	}
}
