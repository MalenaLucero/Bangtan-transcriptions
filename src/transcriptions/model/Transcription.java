package transcriptions.model;

import java.util.Map;

public class Transcription {
	private int id;
	private String titleKorean;
	private String titleEnglish;
	private Map<String, String> textKorean;
	private Map<String, String> textEnglish;
	private String type;
	private String link;
	private String date;
	
	public Transcription() {}
	
	public Transcription(String titleKorean, String titleEnglish, Map<String, String> textKorean,
			Map<String, String> textEnglish, String type, String link, String date) {
		this.titleKorean = titleKorean;
		this.titleEnglish = titleEnglish;
		this.textKorean = textKorean;
		this.textEnglish = textEnglish;
		this.type = type;
		this.link = link;
		this.date = date;
	}
	
	public Transcription(int id, String titleKorean, String titleEnglish, Map<String, String> textKorean,
			Map<String, String> textEnglish, String type, String link, String date) {
		this.id = id;
		this.titleKorean = titleKorean;
		this.titleEnglish = titleEnglish;
		this.textKorean = textKorean;
		this.textEnglish = textEnglish;
		this.type = type;
		this.link = link;
		this.date = date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public String getTitleKorean() {
		return titleKorean;
	}
	public void setTitleKorean(String titleKorean) {
		this.titleKorean = titleKorean;
	}
	public String getTitleEnglish() {
		return titleEnglish;
	}
	public void setTitleEnglish(String titleEnglish) {
		this.titleEnglish = titleEnglish;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString() {
		return String.format("Episode: %s - Type: %s", titleEnglish, type);
	}
}
