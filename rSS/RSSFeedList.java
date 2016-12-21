package rSS;

import java.util.HashMap;
import java.util.List;

import dataIO.URIList;

@SuppressWarnings("serial")
public class RSSFeedList extends HashMap<String, Feed> {

	// constructors
	public RSSFeedList() {}
	
	public RSSFeedList(List<String> uriList) {
		uriList.forEach((uri) -> {
			RSSFeedParser parser = new RSSFeedParser(uri.toString());
			Feed tempFeed = parser.readFeed();
			this.put(uri, tempFeed);
		});
	}
	
	public RSSFeedList(String fileName) {
		URIList uriList = new URIList(fileName);
		uriList.getURIList().forEach((uri) -> {
			this.addFeed(uri);
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

	public void addFeedList(List<String> uriList) {
		uriList.forEach((uri) -> {
			if (!this.containsKey(uri)) {
				RSSFeedParser parser = new RSSFeedParser(uri);
				Feed tempFeed = parser.readFeed();
				this.put(uri, tempFeed);
			}
		});
	}

}
