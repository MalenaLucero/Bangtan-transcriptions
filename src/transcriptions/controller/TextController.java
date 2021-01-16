package transcriptions.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import transcriptions.DAOPostgres.TextDAO;
import transcriptions.IO.ImportText;
import transcriptions.model.Text;
import transcriptions.util.PrintUtil;

public class TextController {
	public static void insert(Connection connection) throws IOException, SQLException, ParseException {
		Text text = ImportText.importFromFile();
		int res = TextDAO.insert(connection, text);
		PrintUtil.actionMessage(res);
	}
	
	public static void findById(Connection connection, Scanner sc) throws SQLException {
		System.out.println("ID:");
		int id = sc.nextInt();
		Text text = TextDAO.findById(connection, id);
		System.out.println(text);
	}
	
	public static void update(Connection connection, Text text) throws SQLException, IOException, ParseException {
		int res = TextDAO.update(connection, text);
		System.out.println(res);
	}
}
