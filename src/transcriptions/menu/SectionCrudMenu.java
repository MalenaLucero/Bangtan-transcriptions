package transcriptions.menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import transcriptions.controller.SectionController;

public class SectionCrudMenu {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		System.out.println("Insert section");
		SectionController.insert(connection);
	}
	
	public static void findById(Connection connection, Scanner sc) throws SQLException {
		System.out.println("Find by ID");
		System.out.println("ID:");
		int id = sc.nextInt();
		SectionController.findByID(connection, id);
	}
	
	public static void export(Connection connection, Scanner sc) throws SQLException, IOException {
		System.out.println("Export section");
		System.out.println("ID:");
		int id = sc.nextInt();
		SectionController.export(connection, id);
	}

}
