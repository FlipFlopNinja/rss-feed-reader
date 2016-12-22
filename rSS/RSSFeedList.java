package rSS;

import java.util.HashMap;
import java.util.List;

import dataIO.URLList;

@SuppressWarnings("serial")
public class RSSFeedList extends HashMap<String, Feed> {

	// constructors
	public RSSFeedList() {}
	
	public RSSFeedList(List<String> urlList) {
		urlList.forEach((url) -> {
			RSSFeedParser parser = new RSSFeedParser(url.toString());
			Feed tempFeed = parser.readFeed();
			this.put(url, tempFeed);
		});
	}
	
	public RSSFeedList(String fileName) {
		URLList urlList = new URLList(fileName);
		urlList.getURLList().forEach((url) -> {
			this.addFeed(url);
		});
	}

	// getter
	public RSSFeedList getRssFeedList() {
		return this;
	}
	
	public Feed getFeed(String url) {
		return this.get(url);
	}

	// methods
	public void addFeed(String url) {
		if (!this.containsKey(url)) {
			RSSFeedParser parser = new RSSFeedParser(url);
			Feed tempFeed = parser.readFeed();
			this.put(url, tempFeed);
		}
	}

	public void addFeedList(List<String> urlList) {
		urlList.forEach((url) -> {
			if (!this.containsKey(url)) {
				RSSFeedParser parser = new RSSFeedParser(url);
				Feed tempFeed = parser.readFeed();
				this.put(url, tempFeed);
			}
		});
	}

}
