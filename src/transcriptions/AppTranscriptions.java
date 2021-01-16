package transcriptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import transcriptions.DAOPostgres.AdminPostgres;
import transcriptions.DAOPostgres.TextDAO;
import transcriptions.controller.TextController;
import transcriptions.menu.TextCrudMenu;
import transcriptions.menu.TranscriptionCrudMenu;
import transcriptions.menu.TranscriptionSearchMenu;
import transcriptions.model.Text;

public class AppTranscriptions {
	public static void main(String[] args) {
		try {
			Connection connection = AdminPostgres.connect();
			System.out.println("Sucessfully connected to Postgres database");
			Scanner sc = new Scanner(System.in);
			try {
				System.out.println("1. Transcription CRUD, 2. Transcription search,"
								+ " 3. Text CRUD, 4. Text search");
				int option = sc.nextInt();
				switch(option) {
				case 1:
					transcriptionCrud(connection, sc);
					break;
				case 2:
					transcriptionSearch(connection, sc);
					break;
				case 3:
					textCrud(connection, sc);
					break;
				case 4:
					textSearch(connection, sc);
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

	private static void textCrud(Connection connection, Scanner sc) throws IOException, SQLException, ParseException {
		System.out.println("1. Insert, 2. Find by id, 3. Modify, 4. Delete");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			TextCrudMenu.insert(connection);
			break;
		case 2:
			TextController.findById(connection, sc);
			break;
		case 3:
			TextCrudMenu.update(connection, sc);
			break;
		}
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

	private static void transcriptionCrud(Connection connection, Scanner sc) throws IOException, SQLException, ParseException {
		System.out.println("1. Insert, 2. Modify, 3. Delete, 4. Find by ID,");
		System.out.println("5. Export, 6. List all");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			TranscriptionCrudMenu.insert(connection);
			break;
		case 2:
			TranscriptionCrudMenu.update(connection, sc);
			break;
		case 3:
			TranscriptionCrudMenu.delete(connection, sc);
			break;
		case 4:
			TranscriptionCrudMenu.findById(connection, sc);
			break;
		case 5:
			TranscriptionCrudMenu.exportTranscription(connection, sc);
			break;
		case 6:
			TranscriptionCrudMenu.findAll(connection);
			break;
		}
	}
	
	private static void textSearch(Connection connection, Scanner sc) throws SQLException {
		System.out.println("word to search");
		String word = sc.next();
		List<Text> list = TextDAO.findByWord(connection, word);
		for (Text text: list) {
			System.out.println(text.getTitleEnglish());
			for (int i = 0; i < text.getTextKorean().size(); i++) {
				if(text.getTextKorean().get(i).contains(word)) {
					System.out.println(text.getTextKorean().get(i));
					System.out.println(text.getTextJapanese().get(i));
					System.out.println(text.getTextEnglish().get(i));
				}
			}
		}
	}
}
