package tasks;

import java.util.TimerTask;

import rSS.RSSFeedList;

public class FeedUpdateCheck extends TimerTask {

	RSSFeedList tempFeedList;
	RSSFeedList checkedFeedList;

	public FeedUpdateCheck(RSSFeedList rssFeedList) {
		tempFeedList = rssFeedList;
		checkedFeedList = new RSSFeedList();
	}

	@Override
	public void run() {
		tempFeedList.forEach((string, feed) -> {
			checkedFeedList.addFeed(feed.getSource());
			if (!feed.getPubDate().equals(checkedFeedList.getFeed(feed.getSource()).getPubDate())) {
				feed.getMenuItem().setUpdated(true);
				feed = checkedFeedList.getFeed(feed.getSource());
			}
		});

	}

}
