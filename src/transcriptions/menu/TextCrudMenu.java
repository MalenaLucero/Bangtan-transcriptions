package transcriptions.menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import transcriptions.DAOPostgres.TextDAO;
import transcriptions.IO.ImportText;
import transcriptions.controller.TextController;
import transcriptions.model.Text;



public class TextCrudMenu {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		System.out.println("Insert");
		TextController.insert(connection);
	}
	
	public static void update(Connection connection, Scanner sc) throws IOException, SQLException, ParseException {
		System.out.println("Modify");
		System.out.println("ID:");
		int id = sc.nextInt();
		Text text = TextDAO.findById(connection, id);
		if(text == null) {
			System.out.println("No se encontro el texto");
		} else {
			Text importedText = ImportText.importFromFile();
			importedText.setId(text.getId());
			TextController.update(connection, importedText);
		}
	}
	
	public static void delete(Connection connection, Scanner sc) throws SQLException {
		System.out.println("Delete");
		System.out.println("ID:");
	}
}
