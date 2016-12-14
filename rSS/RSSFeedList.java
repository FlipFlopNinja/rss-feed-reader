package rSS;

import java.util.HashMap;
import java.util.List;

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

	// getter
	public RSSFeedList getRssFeedList() {
		return this;
	}
	
	public Feed getFeed(String uri) {
		return this.get(uri);
	}

	// methods
	public void addFeed(String uri) {
		RSSFeedParser parser = new RSSFeedParser(uri);
		Feed tempFeed = parser.readFeed();
		this.put(uri, tempFeed);
	}

	public void addFeedList(List<String> uriList) {
		uriList.forEach((uri) -> {
			RSSFeedParser parser = new RSSFeedParser(uri);
			Feed tempFeed = parser.readFeed();
			this.put(uri, tempFeed);
		});
	}

}