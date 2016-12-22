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
			System.out.println("Feed: " + feed.getTitle() + "wird geprueft.");
			System.out.println(feed.getPubDate());
			System.out.println(checkedFeedList.getFeed(feed.getSource()).getPubDate());
			if (!feed.getPubDate().equals(checkedFeedList.getFeed(feed.getSource()).getPubDate())) {
				feed = checkedFeedList.getFeed(feed.getSource());
				System.out.println("Feed: " + feed.getTitle() + " wurde geupdatet");
			}
		});
		
	}

}
