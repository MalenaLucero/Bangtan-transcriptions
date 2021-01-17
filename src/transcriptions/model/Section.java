package transcriptions.model;

import java.util.Map;

public class Section {
	private int id;
	private String start;
	private String finish;
	private String title;
	private int idTranscription;
	private Map<String, String> textKorean;
	private Map<String, String> textEnglish;
	
	public Section(String start, String finish, String title, int idTranscription) {
		this.start = start;
		this.finish = finish;
		this.title = title;
		this.idTranscription = idTranscription;
	}
	
	public Section(int id, String start, String finish, String title, int idTranscription) {
		this.id = id;
		this.start = start;
		this.finish = finish;
		this.title = title;
		this.idTranscription = idTranscription;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIdTranscription() {
		return idTranscription;
	}
	public void setIdTranscription(int idTranscription) {
		this.idTranscription = idTranscription;
	}
	public Map<String, String> getTextKorean() {
		return textKorean;
	}
	public void setTextKorean(Map<String, String> textKorean) {
		this.textKorean = textKorean;
	}
	public Map<String, String> getTextEnglish() {
		return textEnglish;
	}
	public void setTextEnglish(Map<String, String> textEnglish) {
		this.textEnglish = textEnglish;
	}

	public String toString() {
		return String.format("%s: %s - %s", title, start, finish);
	}
}
