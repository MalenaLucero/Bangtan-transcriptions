package transcriptions.model;

import java.util.List;

public class Text {
	private int id;
	private String titleKorean;
	private String titleEnglish;
	private String titleJapanese;
	private List<String> textKorean;
	private List<String> textEnglish;
	private List<String> textJapanese;
	private String type;
	private String link;
	private String date;
	
	public Text(String titleKorean, String titleEnglish, String titleJapanese, List<String> textKorean,
			List<String> textEnglish, List<String> textJapanese, String type, String link, String date) {
		this.titleKorean = titleKorean;
		this.titleEnglish = titleEnglish;
		this.titleJapanese = titleJapanese;
		this.textKorean = textKorean;
		this.textEnglish = textEnglish;
		this.textJapanese = textJapanese;
		this.type = type;
		this.link = link;
		this.date = date;
	}
	
	public Text(int id, String titleKorean, String titleEnglish, String titleJapanese, List<String> textKorean,
			List<String> textEnglish, List<String> textJapanese, String type, String link, String date) {
		this.id = id;
		this.titleKorean = titleKorean;
		this.titleEnglish = titleEnglish;
		this.titleJapanese = titleJapanese;
		this.textKorean = textKorean;
		this.textEnglish = textEnglish;
		this.textJapanese = textJapanese;
		this.type = type;
		this.link = link;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTitleJapanese() {
		return titleJapanese;
	}

	public void setTitleJapanese(String titleJapanese) {
		this.titleJapanese = titleJapanese;
	}

	public List<String> getTextKorean() {
		return textKorean;
	}

	public void setTextKorean(List<String> textKorean) {
		this.textKorean = textKorean;
	}

	public List<String> getTextEnglish() {
		return textEnglish;
	}

	public void setTextEnglish(List<String> textEnglish) {
		this.textEnglish = textEnglish;
	}

	public List<String> getTextJapanese() {
		return textJapanese;
	}

	public void setTextJapanese(List<String> textJapanese) {
		this.textJapanese = textJapanese;
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
		return titleEnglish;
	}
}
