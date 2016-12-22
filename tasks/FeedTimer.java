package tasks;

import java.util.Timer;

import rSS.RSSFeedList;

public class FeedTimer {

	public FeedTimer(RSSFeedList rssFeedList) {
		Timer feedTimer = new Timer();
		feedTimer.schedule(new OnlineCheck(), 0, 600000);
		feedTimer.schedule(new FeedUpdateCheck(rssFeedList), 0,300000);
	}
}
