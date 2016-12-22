package rSS;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class Feed {

	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	final String pubDate;
	final String source;
	private Boolean isOpened = false;
	private Boolean isMenuItem = false;

	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	public Feed(String title, String link, String description, String language, String copyright, String pubDate, String source) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
		this.source = source;
	}

	public List<FeedMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getPubDate() {
		return pubDate;
	}
	
	public String getSource() {
		return source;
	}
	
	public Boolean getIsOpened() {
		return this.isOpened;
	}
	
	public Boolean getIsMenuItem() {
		return this.isMenuItem;
	}
	
	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}
	
	public void setIsMenuItem(Boolean isMenuItem) {
		this.isMenuItem = isMenuItem;
	}

	@Override
	public String toString() {
		return "Feed [copyright=" + copyright + ", description=" + description + ", language=" + language + ", link="
				+ link + ", pubDate=" + pubDate + ", title=" + title + "]";
	}
	
	public boolean isOutdated(String pubDate) {
		return (this.pubDate.equals(pubDate) ? false : true);
	}

}
