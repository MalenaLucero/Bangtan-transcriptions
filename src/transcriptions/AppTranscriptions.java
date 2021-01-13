package transcriptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import transcriptions.DAO.AdminMySQL;
import transcriptions.DAO.TranscriptionDAO;
import transcriptions.controller.TranscriptionController;
import transcriptions.model.Transcription;

public class AppTranscriptions {
	public static void main(String[] args) {
		try {
			Connection connection = AdminMySQL.connect();
			System.out.println("Sucessfully connected to database");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Programa finalizado");
	}

	private static void transcriptionSearch(Connection connection, Scanner sc) throws SQLException {
		System.out.println("1. Merge subs");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			Transcription transcription = TranscriptionDAO.findById(connection, 1);
			transcription.getTextKorean().forEach((key, value) -> {
				System.out.println(key);
				System.out.println(value);
				System.out.println(transcription.getTextEnglish().get(key));
			});
			break;
		}
		
	}

	private static void crud(Connection connection, Scanner sc) throws IOException, SQLException {
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
