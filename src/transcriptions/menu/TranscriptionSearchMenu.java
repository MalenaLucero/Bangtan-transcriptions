package transcriptions.menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import transcriptions.IO.ExportSearchResult;
import transcriptions.controller.TranscriptionSearchController;
import transcriptions.model.Transcription;

public class TranscriptionSearchMenu {
	public static void searchIsolatedWords(Scanner sc, Connection connection) throws SQLException, IOException {
		System.out.println("Word or words (separated by space):");
		sc.nextLine();
		String words = sc.nextLine() + " ";
		String[] wordList = words.split(" ");
		searchAndExport(sc, connection, wordList);
	}
	
	public static void searchPhrase(Scanner sc, Connection connection) throws SQLException, IOException {
		System.out.println("Phrase:");
		sc.nextLine();
		String[] wordList = { sc.nextLine() };
		searchAndExport(sc, connection, wordList);
	}
	
	private static void searchAndExport(Scanner sc, Connection connection, String[] wordList) throws SQLException, IOException {
		String fileName = ExportSearchResult.generateFile(wordList);
		searchType(sc, connection, wordList, fileName);
		System.out.println("Resultado de busqueda exportado como " + fileName);
	}
	
	private static void searchType(Scanner sc, Connection connection, String[] wordList, String fileName) throws SQLException, IOException {
		System.out.println("Where do you want to search");
		System.out.println("1. All transcripts, 2. By type, 3. By ID");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			for (int i = 0; i < wordList.length; i++) {
				Map<Transcription, List<String>> data = TranscriptionSearchController.findByWordInKorean(connection, wordList[i]);
				ExportSearchResult.generateWordDetail(data, fileName, wordList[i]);
			}
			break;
		case 2:
			searchAndExportByType(sc, connection, wordList, fileName);
			break;
		}
	}
	
	private static void searchAndExportByType(Scanner sc, Connection connection, String[] wordList, String fileName) throws SQLException, IOException {
		System.out.println("Type:");
		String type = sc.next();
		for (int i = 0; i < wordList.length; i++) {
			Map<Transcription, List<String>> data = TranscriptionSearchController.findByWordAndTypeInKorean(connection, wordList[i], type);
			ExportSearchResult.generateWordDetail(data, fileName, wordList[i]);
		}
	}
}
