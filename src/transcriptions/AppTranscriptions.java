package transcriptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import transcriptions.DAO.AdminMySQL;
import transcriptions.DAO.TranscriptionDAO;
import transcriptions.IO.ImportTranscription;
import transcriptions.controller.TranscriptionController;
import transcriptions.model.Transcription;

public class AppTranscriptions {
	public static void main(String[] args) {
		try {
			Connection connection = AdminMySQL.connect();
			System.out.println("Sucessfully connected to database");
			try {
				int option = 1;
				switch(option) {
				case 1:
					System.out.println("Insertar");
					TranscriptionController.insert(connection);
					break;
				case 2:
					System.out.println("Buscar por titulo");
					String title = "";
					TranscriptionController.findByTitle(connection, title);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
